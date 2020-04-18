package org.harshdev.goosbook

enum SniperStatus {

    STATUS_JOINING("JOINING"),
    STATUS_CLOSED("CLOSED"),
    STATUS_LOST("LOST"),
    STATUS_BIDDING("BIDDING"),
    ;

    public final String name;

    SniperStatus(String name) {
        this.name = name
    }




}