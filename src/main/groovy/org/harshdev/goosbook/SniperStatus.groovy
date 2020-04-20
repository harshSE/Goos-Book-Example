package org.harshdev.goosbook

enum SniperStatus {

    STATUS_JOINING("JOINING"),
    STATUS_CLOSED("CLOSED"),
    STATUS_LOST("LOST"),
    STATUS_WIN("WIN"),
    STATUS_BIDDING("BIDDING"),
    STATUS_WINNING("WINNING"),
    ;

    public final String name;

    SniperStatus(String name) {
        this.name = name
    }




}