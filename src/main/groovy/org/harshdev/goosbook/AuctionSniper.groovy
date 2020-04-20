package org.harshdev.goosbook

class AuctionSniper implements AuctionEventListener{

    private final SniperListener sniperListener
    private final Auction auction
    private SniperSnapShot snapShot;

    AuctionSniper(String item,SniperListener sniperListener, Auction auction) {
        this.auction = auction
        this.sniperListener = sniperListener
        this.snapShot = SniperSnapShot.joining(item)
    }

    @Override
    void auctionClosed() {
        snapShot = snapShot.close()
        notifyChange()

    }

    @Override
    void currentPrice(int price, int increment, PriceSource priceSource) {
        boolean  isWinning = priceSource == PriceSource.FromSniper

        if(isWinning) {
            snapShot = snapShot.winning(price)
            notifyChange()
        } else {
            int bid = price + increment
            snapShot = snapShot.bidding(price, bid)
            notifyChange()
            auction.bid(price + increment)
        }
    }

    private notifyChange() {
        sniperListener.sniperStateChanged(snapShot)
    }

}
