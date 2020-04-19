package org.harshdev.goosbook

class AuctionSniper implements AuctionEventListener{

    private final SniperListener sniperListener
    private final Auction auction

    AuctionSniper(SniperListener sniperListener, Auction auction) {
        this.auction = auction
        this.sniperListener = sniperListener
    }

    @Override
    void auctionClosed() {
        sniperListener.sniperLost()
    }

    @Override
    void currentPrice(int bidPrice, int increment) {
        sniperListener.bidding()
        auction.bid(bidPrice + increment)
    }
}
