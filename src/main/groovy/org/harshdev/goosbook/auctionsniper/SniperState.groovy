package org.harshdev.goosbook.auctionsniper

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
    LOSING("LOSING") {
        @Override
        SniperState whenAuctionClosed() {
            LOST
        }
    },
    WON("WIN"),

    WINNING("WINNING"){
        @Override
        SniperState whenAuctionClosed() {
            WON
        }
    },
    CLOSED("CLOSED"),
    FAILED("FAILED") {
        @Override
        SniperState whenAuctionClosed() {
            FAILED
        }
    }
    ;

    public final String name;

    SniperState(String name) {
        this.name = name
    }

    SniperState whenAuctionClosed() {
        throw new IllegalStateException("Auction already closed")
    }




}