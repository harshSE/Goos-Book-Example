package org.harshdev.goosbook.auctionsniper;

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString;

@ToString
@EqualsAndHashCode
class Item {
    final String itemId;
    final int stopPrice;

    Item(String itemId, int stopPrice) {
        this.itemId = itemId;
        this.stopPrice = stopPrice;
    }

    boolean allowBid(int newPrice) {
        newPrice <= stopPrice
    }
}
