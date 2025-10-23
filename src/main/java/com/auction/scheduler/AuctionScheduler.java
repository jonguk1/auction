package com.auction.scheduler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.auction.service.AuctionItemService;

/**
 * 경매 마감 스케줄러
 * 주기적으로 만료된 경매를 자동으로 마감하고 낙찰 처리합니다.
 */
@Component
public class AuctionScheduler {

    private static final Logger logger = LoggerFactory.getLogger(AuctionScheduler.class);

    private final AuctionItemService auctionItemService;

    public AuctionScheduler(AuctionItemService auctionItemService) {
        this.auctionItemService = auctionItemService;
    }

    /**
     * 만료된 경매를 마감하고 낙찰 처리
     * 매분마다 실행됩니다.
     */
    @Scheduled(cron = "0 * * * * *")
    public void closeExpiredAuctions() {
        logger.info("경매 마감 스케줄러 실행 시작");

        try {
            auctionItemService.closeExpiredAuctions();
            logger.info("경매 마감 스케줄러 실행 완료");
        } catch (Exception e) {
            logger.error("경매 마감 스케줄러 실행 중 오류 발생", e);
        }
    }
}
