package com.auction.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.dto.AuctionItemCreateDto;
import com.auction.dto.AuctionItemDto;
import com.auction.dto.AuctionItemUpdateDto;
import com.auction.entity.AuctionItem;
import com.auction.entity.AuctionItem.AuctionStatus;
import com.auction.entity.User;
import com.auction.exception.UserNotFoundException;
import com.auction.repository.AuctionItemRepository;
import com.auction.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class AuctionItemService {

    private final AuctionItemRepository auctionItemRepository;
    private final UserRepository userRepository;

    public AuctionItemService(AuctionItemRepository auctionItemRepository, UserRepository userRepository) {
        this.auctionItemRepository = auctionItemRepository;
        this.userRepository = userRepository;
    }

    // 유효성 검증 메서드
    private void validateAuctionItemCreate(AuctionItemCreateDto dto) {
        // 필수값 체크
        if (dto.getTitle() == null || dto.getTitle().trim().isEmpty()) {
            throw new IllegalArgumentException("제목을 입력해주세요.");
        }

        if (dto.getStartPrice() == null || dto.getStartPrice() <= 0) {
            throw new IllegalArgumentException("시작가는 0보다 커야 합니다.");
        }

        if (dto.getStartTime() == null || dto.getEndTime() == null) {
            throw new IllegalArgumentException("경매 시작/종료 시간을 입력해주세요.");
        }

        // 시간 검증
        LocalDateTime now = LocalDateTime.now();

        if (dto.getEndTime().isBefore(dto.getStartTime())) {
            throw new IllegalArgumentException("종료 시간은 시작 시간보다 늦어야 합니다.");
        }

        if (dto.getStartTime().isBefore(now.minusMinutes(5))) {
            throw new IllegalArgumentException("시작 시간은 현재 시간 이후여야 합니다.");
        }

        // 경매 기간 검증
        long hours = ChronoUnit.HOURS.between(dto.getStartTime(), dto.getEndTime());
        if (hours < 1) {
            throw new IllegalArgumentException("경매 기간은 최소 1시간 이상이어야 합니다.");
        }

        if (hours > 720) { // 30일
            throw new IllegalArgumentException("경매 기간은 최대 30일까지 가능합니다.");
        }
    }

    // 경매 상품 등록
    @Transactional
    public AuctionItemDto createAuctionItem(Long sellerId, AuctionItemCreateDto dto) {
        // 1. 판매자 조회
        User seller = userRepository.findById(sellerId)
                .orElseThrow(() -> new UserNotFoundException("판매자를 찾을 수 없습니다."));

        // 2. 유효성 검증
        validateAuctionItemCreate(dto);

        // 3. Entity 생성
        AuctionItem auctionItem = new AuctionItem();
        auctionItem.setSeller(seller);
        auctionItem.setTitle(dto.getTitle());
        auctionItem.setDescription(dto.getDescription());
        auctionItem.setStartPrice(dto.getStartPrice());
        auctionItem.setCurrentPrice(dto.getStartPrice()); // 현재가 = 시작가
        auctionItem.setStartTime(dto.getStartTime());
        auctionItem.setEndTime(dto.getEndTime());

        // 4. 상태 설정
        LocalDateTime now = LocalDateTime.now();
        if (dto.getStartTime().isBefore(now) || dto.getStartTime().isEqual(now)) {
            auctionItem.setStatus(AuctionStatus.ACTIVE);
        } else {
            auctionItem.setStatus(AuctionStatus.PENDING);
        }

        // 5. 저장 및 반환
        return AuctionItemDto.fromEntity(auctionItemRepository.save(auctionItem));
    }

    // 경매 상품 조회
    public AuctionItemDto getAuctionItem(Long itemId) {
        return auctionItemRepository.findById(itemId)
                .map(AuctionItemDto::fromEntity)
                .orElseThrow(() -> new IllegalArgumentException("경매 상품을 찾을 수 없습니다."));
    }

    // 모든 경매 상품 조회
    public List<AuctionItemDto> getAllAuctionItems() {
        return convertToDto(auctionItemRepository.findAll());
    }

    // 진행중인 경매 조회
    public List<AuctionItemDto> getActiveAuctionItems() {
        return convertToDto(auctionItemRepository.findByStatus(AuctionStatus.ACTIVE));
    }

    // 내가 등록한 경매 조회
    public List<AuctionItemDto> getMyAuctionItems(Long userId) {
        return convertToDto(auctionItemRepository.findBySellerId(userId));
    }

    // 제목으로 검색
    public List<AuctionItemDto> searchAuctionItems(String keyword) {
        return convertToDto(auctionItemRepository.findByTitleContaining(keyword));
    }

    // 경매 상품 수정
    @Transactional
    public AuctionItemDto updateAuctionItem(Long id, AuctionItemUpdateDto dto) {
        AuctionItem auctionItem = auctionItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("경매 상품을 찾을 수 없습니다."));

        // 입찰이 있으면 수정 불가 (선택적 검증)
        if (!auctionItem.getBids().isEmpty()) {
            throw new IllegalStateException("입찰이 있는 경매는 수정할 수 없습니다.");
        }

        // 수정 가능한 필드만 업데이트
        if (dto.getTitle() != null && !dto.getTitle().trim().isEmpty()) {
            auctionItem.setTitle(dto.getTitle());
        }
        if (dto.getDescription() != null) {
            auctionItem.setDescription(dto.getDescription());
        }
        if (dto.getEndTime() != null && dto.getEndTime().isAfter(auctionItem.getStartTime())) {
            auctionItem.setEndTime(dto.getEndTime());
        }

        return AuctionItemDto.fromEntity(auctionItem);
    }

    // 경매 상품 삭제
    @Transactional
    public void deleteAuctionItem(Long id) {
        AuctionItem auctionItem = auctionItemRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("경매 상품을 찾을 수 없습니다."));

        // 입찰이 있으면 삭제 불가
        if (!auctionItem.getBids().isEmpty()) {
            throw new IllegalStateException("입찰이 있는 경매는 삭제할 수 없습니다.");
        }

        auctionItemRepository.delete(auctionItem);
    }

    // Entity List를 DTO List로 변환하는 헬퍼 메서드
    private List<AuctionItemDto> convertToDto(List<AuctionItem> items) {
        return items.stream()
                .map(AuctionItemDto::fromEntity)
                .toList();
    }

}
