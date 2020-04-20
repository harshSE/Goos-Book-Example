package org.harshdev.goosbook.auctionsniper.ui

import org.harshdev.goosbook.SniperSnapShot

enum Column {
    ITEM("Item") {
        @Override
        Object valueIn(SniperSnapShot snapShot) {
            snapShot.getItem()
        }
    },
    LAST_PRICE("Last Price") {
        @Override
        Object valueIn(SniperSnapShot snapShot) {
            snapShot.getLastPrice()
        }
    },
    LAST_BID("Last Bid") {
        @Override
        Object valueIn(SniperSnapShot snapShot) {
            snapShot.getLastBid()
        }
    },
    SNIPER_STATE("State") {
        @Override
        Object valueIn(SniperSnapShot snapShot) {
            SniperTableModel.getText(snapShot.getState())
        }
    };

    final name;
    Column(String name) {
        this.name = name;
    }

    static Column at(int offset) {
        values()[offset]
    }

    abstract Object valueIn(SniperSnapShot snapShot);

}