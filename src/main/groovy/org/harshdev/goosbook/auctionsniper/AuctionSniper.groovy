package org.harshdev.goosbook.auctionsniper

import java.util.concurrent.CopyOnWriteArrayList

class AuctionSniper implements AuctionEventListener{

    private final List<SniperListener> sniperListeners
    private Item item;
    private final Auction auction
    private SniperSnapShot snapShot;

    AuctionSniper(Item item, Auction auction) {
        this.item = item;
        this.auction = auction
        this.sniperListeners = [] as CopyOnWriteArrayList
        this.snapShot = SniperSnapShot.joining(item.getItemId())
    }

    @Override
    void auctionClosed() {
        snapShot = snapShot.close()
        notifyChange()
    }

    @Override
    void auctionFailed() {
        snapShot = snapShot.failed()
        notifyChange()
    }

    void addSniperListener(SniperListener listener ) {
        sniperListeners << listener
    }

    SniperSnapShot getSnapShot() {
        return snapShot
    }




    @Override
    void currentPrice(int price, int increment, PriceSource priceSource) {
        boolean  isWinning = priceSource == PriceSource.FromSniper

        if(isWinning) {
            snapShot = snapShot.winning(price)
            notifyChange()
        } else {
            int bid = price + increment
            if(shouldBidFurther(bid)) {
                snapShot = snapShot.bidding(price, bid)
                notifyChange()
                auction.bid(price + increment)
            } else {
                snapShot = snapShot.losing(price)
                notifyChange()
            }
        }
    }

    private notifyChange() {
        sniperListeners.each {
          it.sniperStateChanged(snapShot)
        }
    }

    private boolean shouldBidFurther(int newPrice) {
        return item.allowBid(newPrice)
    }
}
