package org.harshdev.goosbook.auctionsniper;

trait AuctionEventListener implements EventListener{
    enum PriceSource {
        FromSniper,
        FromOtherBidder
    }
    abstract void auctionClosed()
    void auctionFailed() {

    }
    abstract void currentPrice(int price, int increment, PriceSource priceSource);
}
