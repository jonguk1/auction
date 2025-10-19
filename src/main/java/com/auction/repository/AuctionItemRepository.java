package com.auction.repository;

import com.auction.entity.AuctionItem;
import com.auction.entity.AuctionItem.AuctionStatus;
import com.auction.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface AuctionItemRepository extends JpaRepository<AuctionItem, Long> {

    // 판매자로 경매 상품 찾기
    List<AuctionItem> findBySeller(User seller);

    // 상태별 경매 상품 찾기
    List<AuctionItem> findByStatus(AuctionStatus status);

    // 진행중인 경매 상품 찾기
    @Query("SELECT a FROM AuctionItem a WHERE a.status = 'ACTIVE' AND a.startTime <= :now AND a.endTime > :now")
    List<AuctionItem> findActiveAuctions(@Param("now") LocalDateTime now);

    // 종료 시간이 지난 진행중인 경매 찾기 (자동 종료용)
    @Query("SELECT a FROM AuctionItem a WHERE a.status = 'ACTIVE' AND a.endTime <= :now")
    List<AuctionItem> findExpiredAuctions(@Param("now") LocalDateTime now);

    // 제목으로 검색
    List<AuctionItem> findByTitleContaining(String keyword);

    // 판매자 ID로 경매 상품 찾기
    @Query("SELECT a FROM AuctionItem a WHERE a.seller.id = :sellerId")
    List<AuctionItem> findBySellerId(@Param("sellerId") Long sellerId);
}
