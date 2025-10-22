package com.auction.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.auction.dto.BidCreateDto;
import com.auction.dto.BidDto;
import com.auction.dto.CustomUserDetails;
import com.auction.service.BidService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auction/bids")
public class BidController {

    private final BidService bidService;

    public BidController(BidService bidService) {
        this.bidService = bidService;
    }

    // 입찰하기
    @PostMapping
    public ResponseEntity<BidDto> createBid(
            @AuthenticationPrincipal CustomUserDetails currentUser,
            @RequestBody BidCreateDto dto) {
        BidDto createdBid = bidService.placeBid(currentUser.getId(), dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdBid);
    }

    // 경매별 입찰 내역 조회
    @GetMapping
    public ResponseEntity<List<BidDto>> getBidsByAuctionItem(
            @RequestParam Long auctionId) {
        List<BidDto> bids = bidService.getBidsByAuctionItem(auctionId);
        return ResponseEntity.ok(bids);
    }

    // 최고 입찰 조회
    @GetMapping("/top")
    public ResponseEntity<BidDto> getTopBid(@RequestParam Long auctionId) {
        BidDto topBid = bidService.getTopBid(auctionId);
        return ResponseEntity.ok(topBid);
    }

    // 내 입찰 내역 조회
    @GetMapping("/my")
    public ResponseEntity<List<BidDto>> getMyBids(
            @AuthenticationPrincipal CustomUserDetails currentUser) {
        List<BidDto> myBids = bidService.getMyBids(currentUser.getId());
        return ResponseEntity.ok(myBids);
    }

    // 입찰 개수 조회
    @GetMapping("/count")
    public ResponseEntity<Long> getBidCount(@RequestParam Long auctionId) {
        Long bidCount = bidService.getBidCount(auctionId);
        return ResponseEntity.ok(bidCount);
    }

}
