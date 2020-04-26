package org.harshdev.goosbook.auctionsniper;

interface AuctionEventListener extends EventListener{
    enum PriceSource {
        FromSniper,
        FromOtherBidder
    }
    void auctionClosed()
    void currentPrice(int price, int increment, PriceSource priceSource);
}
