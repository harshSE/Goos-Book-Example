package org.harshdev.goosbook

import org.jivesoftware.smack.packet.Message;

class AuctionMessageTranslator {

    private final AuctionEventListener listener

    AuctionMessageTranslator(AuctionEventListener listener) {
        this.listener = listener
    }

    void processMessage(Message message) {
        def map = toMap(message.getBody())

        if("Price".equalsIgnoreCase(map.get("Event"))) {
            listener.currentPrice(map.get("CurrentPrice") as Integer, map.get("Increment") as Integer)
        } else {
            listener.auctionClosed()
        }

    }

    private Map<String, String> toMap(String message) {
        Map<String, String> map = [:]
        message.split(";").each {
            def values = it.split(':')
            map[values[0].trim()] = values[1].trim()
        }
        map
    }
}
