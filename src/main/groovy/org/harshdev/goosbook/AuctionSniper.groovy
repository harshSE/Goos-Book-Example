package org.harshdev.goosbook

class AuctionSniper implements AuctionEventListener{

    private final SniperListener sniperListener
    private final Auction auction
    private boolean  isWinning;

    AuctionSniper(SniperListener sniperListener, Auction auction) {
        this.auction = auction
        this.sniperListener = sniperListener
    }

    @Override
    void auctionClosed() {
        if(isWinning) {
            sniperListener.sniperWon()
        } else {
            sniperListener.sniperLost()
        }

    }

    @Override
    void currentPrice(int price, int increment, PriceSource priceSource) {
        isWinning = priceSource == PriceSource.FromSniper

        if(isWinning) {
            sniperListener.winning()
        } else {
            sniperListener.bidding()
            auction.bid(price + increment)
        }
    }
}
