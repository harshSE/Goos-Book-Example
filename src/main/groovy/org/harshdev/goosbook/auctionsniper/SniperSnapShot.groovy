package org.harshdev.goosbook.auctionsniper

import groovy.transform.EqualsAndHashCode

@EqualsAndHashCode
class SniperSnapShot {
    final int lastPrice
    final int lastBid
    final SniperState state
    final String item

    SniperSnapShot(String item, int lastPrice, int lastBid, SniperState state) {
        this.item = item
        this.lastPrice = lastPrice
        this.lastBid = lastBid
        this.state = state
    }

    static SniperSnapShot joining(String item) {
        new SniperSnapShot(item,0, 0, SniperState.JOINING)
    }

    SniperSnapShot close() {
        new SniperSnapShot(item,lastPrice, lastBid, state.whenAuctionClosed())
    }

    SniperSnapShot winning(int price) {
        new SniperSnapShot(item,price, price, SniperState.WINNING)
    }

    SniperSnapShot bidding(int price, int bid) {
        new SniperSnapShot(item,price, bid, SniperState.BIDDING)
    }

    SniperSnapShot losing(int price) {
        new SniperSnapShot(item,price, lastBid, SniperState.LOSING)
    }

    SniperSnapShot failed() {
        new SniperSnapShot(item, lastPrice, lastBid, SniperState.FAILED)
    }
}
