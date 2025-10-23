package com.auction.repository;

import com.auction.entity.AuctionItem;
import com.auction.entity.Bid;
import com.auction.entity.Bid.BidStatus;
import com.auction.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BidRepository extends JpaRepository<Bid, Long> {

    // 특정 경매 상품의 모든 입찰 조회 (최신순)
    List<Bid> findByAuctionItemOrderByBidTimeDesc(AuctionItem auctionItem);

    // 특정 경매 상품의 최고 입찰 조회
    @Query("SELECT b FROM Bid b WHERE b.auctionItem = :auctionItem ORDER BY b.bidAmount DESC LIMIT 1")
    Optional<Bid> findTopBidByAuctionItem(@Param("auctionItem") AuctionItem auctionItem);

    // 특정 사용자의 모든 입찰 조회
    List<Bid> findByBidderOrderByBidTimeDesc(User bidder);

    // 특정 사용자가 특정 경매에 입찰한 내역
    List<Bid> findByBidderAndAuctionItemOrderByBidTimeDesc(User bidder, AuctionItem auctionItem);

    // 특정 경매의 입찰 개수
    long countByAuctionItem(AuctionItem auctionItem);

    // 특정 상태의 입찰 조회
    List<Bid> findByStatus(BidStatus status);

    // 사용자 ID로 입찰 내역 조회
    @Query("SELECT b FROM Bid b WHERE b.bidder.id = :bidderId ORDER BY b.bidTime DESC")
    List<Bid> findByBidderId(@Param("bidderId") Long bidderId);

    // 경매 상품 ID로 입찰 내역 조회
    @Query("SELECT b FROM Bid b WHERE b.auctionItem.id = :auctionItemId ORDER BY b.bidAmount DESC")
    List<Bid> findByAuctionItemId(@Param("auctionItemId") Long auctionItemId);

    // 경매 상품 ID로 입찰 내역 조회 (금액 내림차순) - 스케줄러용
    List<Bid> findByAuctionItemIdOrderByBidAmountDesc(Long auctionItemId);
}
