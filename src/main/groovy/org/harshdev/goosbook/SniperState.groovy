package org.harshdev.goosbook

enum SniperState {


    JOINING("JOINING"){
        @Override
        SniperState whenAuctionClosed() {
            LOST
        }
    },
    BIDDING("BIDDING") {
        @Override
        SniperState whenAuctionClosed() {
            LOST
        }
    },
    LOST("LOST"),
    WON("WIN"),

    WINNING("WINNING"){
        @Override
        SniperState whenAuctionClosed() {
            WON
        }
    },
    CLOSED("CLOSED")
    ;

    public final String name;

    SniperState(String name) {
        this.name = name
    }

    SniperState whenAuctionClosed() {
        throw new IllegalStateException("Auction already closed")
    }




}