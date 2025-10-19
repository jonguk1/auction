package com.auction.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auction.dto.AuctionItemCreateDto;
import com.auction.dto.AuctionItemDto;
import com.auction.dto.AuctionItemUpdateDto;
import com.auction.service.AuctionItemService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auction/items")
public class AuctionItemController {

    public final AuctionItemService auctionItemService;

    public AuctionItemController(AuctionItemService auctionItemService) {
        this.auctionItemService = auctionItemService;
    }

    // 경매 상품 등록
    @PostMapping("/create")
    public ResponseEntity<AuctionItemDto> createAuctionItem(
            @RequestParam Long sellerId,
            @RequestBody AuctionItemCreateDto dto) {
        AuctionItemDto created = auctionItemService.createAuctionItem(sellerId, dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // 경매 상품 조회
    @GetMapping("/{itemId}")
    public ResponseEntity<AuctionItemDto> getAuctionItem(@RequestParam Long itemId) {
        AuctionItemDto item = auctionItemService.getAuctionItem(itemId);
        return ResponseEntity.ok(item);
    }

    // 모든 경매 상품 조회
    @GetMapping
    public ResponseEntity<List<AuctionItemDto>> getAllAuctionItems() {
        List<AuctionItemDto> items = auctionItemService.getAllAuctionItems();
        return ResponseEntity.ok(items);
    }

    // 진행중인 경매 조회
    @GetMapping("/active")
    public ResponseEntity<List<AuctionItemDto>> getActiveAuctionItems() {
        List<AuctionItemDto> items = auctionItemService.getActiveAuctionItems();
        return ResponseEntity.ok(items);
    }

    // 내가 등록한 경매 조회
    @GetMapping("/my")
    public ResponseEntity<List<AuctionItemDto>> getMyAuctionItems(
            @RequestParam Long userId // 나중에 @AuthenticationPrincipal로 변경
    ) {
        List<AuctionItemDto> items = auctionItemService.getMyAuctionItems(userId);
        return ResponseEntity.ok(items);
    }

    // 제목으로 검색
    @GetMapping("/search")
    public ResponseEntity<List<AuctionItemDto>> searchAuctionItems(@RequestParam String title) {
        List<AuctionItemDto> items = auctionItemService.searchAuctionItems(title);
        return ResponseEntity.ok(items);
    }

    // 경매 상품 수정
    @PostMapping("/update/{itemId}")
    public ResponseEntity<AuctionItemDto> updateAuctionItem(
            @RequestParam Long itemId,
            @RequestBody AuctionItemUpdateDto dto) {
        AuctionItemDto updated = auctionItemService.updateAuctionItem(itemId, dto);
        return ResponseEntity.ok(updated);
    }

    // 경매 상품 삭제
    @DeleteMapping("/delete/{itemId}")
    public ResponseEntity<Void> deleteAuctionItem(@RequestParam Long itemId) {
        auctionItemService.deleteAuctionItem(itemId);
        return ResponseEntity.noContent().build();
    }

}
