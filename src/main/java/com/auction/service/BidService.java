package com.auction.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auction.dto.BidCreateDto;
import com.auction.dto.BidDto;
import com.auction.entity.AuctionItem;
import com.auction.entity.Bid;
import com.auction.entity.User;
import com.auction.exception.UserNotFoundException;
import com.auction.repository.AuctionItemRepository;
import com.auction.repository.BidRepository;
import com.auction.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class BidService {

    private final BidRepository bidRepository;
    private final AuctionItemRepository auctionItemRepository;
    private final UserRepository userRepository;

    public BidService(BidRepository bidRepository, AuctionItemRepository auctionItemRepository,
            UserRepository userRepository) {
        this.bidRepository = bidRepository;
        this.auctionItemRepository = auctionItemRepository;
        this.userRepository = userRepository;
    }

    // 입찰하기
    @Transactional
    public BidDto placeBid(Long bidderId, BidCreateDto dto) {
        // 입찰자 조회
        User bidder = userRepository.findById(bidderId)
                .orElseThrow(() -> new UserNotFoundException("사용자를 찾을 수 없습니다."));
        // 경매 상품 조회
        AuctionItem auctionItem = auctionItemRepository.findById(dto.getAuctionItemId())
                .orElseThrow(() -> new IllegalArgumentException("경매 상품을 찾을 수 없습니다."));
        // 유효성 검증
        validateBid(bidder, auctionItem, dto.getBidAmount());

        // 이전 최고 입찰 처리
        bidRepository.findTopBidByAuctionItem(auctionItem)
                .ifPresent(topBid -> {
                    topBid.markAsOutbid();
                });

        // 새 입찰 생성
        Bid bid = new Bid(bidder, auctionItem, dto.getBidAmount());
        bid.markAsWinning();

        // 경매 상품 현재가 업데이트
        auctionItem.updateCurrentPrice(dto.getBidAmount());

        // 저장 및 반환
        return BidDto.fromEntity(bidRepository.save(bid));
    }

    // 경매별 입찰 내역 조회
    @Transactional(readOnly = true)
    public List<BidDto> getBidsByAuctionItem(Long auctionItemId) {
        return convertToDto(bidRepository.findByAuctionItemId(auctionItemId));
    }

    // 최고 입찰 조회
    @Transactional(readOnly = true)
    public BidDto getTopBid(Long auctionItemId) {
        AuctionItem auctionItem = auctionItemRepository.findById(auctionItemId)
                .orElseThrow(() -> new IllegalArgumentException("경매 상품을 찾을 수 없습니다."));
        Bid topBid = bidRepository.findTopBidByAuctionItem(auctionItem)
                .orElseThrow(() -> new IllegalArgumentException("입찰 내역이 없습니다."));
        return BidDto.fromEntity(topBid);
    }

    // 내 입찰 내역
    @Transactional(readOnly = true)
    public List<BidDto> getMyBids(Long userId) {
        return convertToDto(bidRepository.findByBidderId(userId));
    }

    // 입찰 개수 조회
    @Transactional(readOnly = true)
    public Long getBidCount(Long auctionItemId) {
        AuctionItem auctionItem = auctionItemRepository.findById(auctionItemId)
                .orElseThrow(() -> new IllegalArgumentException("경매 상품을 찾을 수 없습니다."));
        return bidRepository.countByAuctionItem(auctionItem);
    }

    // 유효성 검증 메서드
    private void validateBid(User bidder, AuctionItem item, Long bidAmount) {
        // 진행중인 경매 확인
        if (!item.isActive()) {
            throw new IllegalStateException("진행중인 경매가 아닙니다.");
        }

        // 본인 상품 확인 (본인 상품에는 입찰 불가)
        if (item.getSeller().getId().equals(bidder.getId())) {
            throw new IllegalStateException("본인이 등록한 상품에는 입찰할 수 없습니다.");
        }

        // 입찰 금액 확인 (현재가보다 높아야 함)
        if (bidAmount <= item.getCurrentPrice()) {
            throw new IllegalArgumentException(
                    "입찰 금액은 현재가(" + item.getCurrentPrice() + "원)보다 높아야 합니다.");
        }
    }

    // 9. Entity -> DTO 변환 헬퍼
    private List<BidDto> convertToDto(List<Bid> bids) {
        return bids.stream()
                .map(BidDto::fromEntity)
                .toList();
    }

}
