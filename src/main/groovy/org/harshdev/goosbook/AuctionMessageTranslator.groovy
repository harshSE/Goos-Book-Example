package org.harshdev.goosbook

import org.jivesoftware.smack.packet.Message;

class AuctionMessageTranslator {

    private final AuctionEventListener listener

    AuctionMessageTranslator(AuctionEventListener listener) {
        this.listener = listener
    }

    void processMessage(Message message) {
        AuctionEvent auctionEvent = AuctionEvent.from(message)

        if(auctionEvent.isPriceEvent()) {
            listener.currentPrice(auctionEvent.currentPrice(), auctionEvent.increment())
        } else if(auctionEvent.isCloseEvent()){
            listener.auctionClosed()
        }
    }

    private static class AuctionEvent {

        private final Map<String, String> fields

        AuctionEvent(Map<String, String> fields) {
            this.fields = fields
        }

        int currentPrice() {
            fields.get("CurrentPrice") as Integer
        }

        int increment() {
            fields.get("Increment") as Integer
        }

        String event() {
            fields.get("Event")
        }

        boolean isPriceEvent() {
            "Price".equalsIgnoreCase(event())
        }

        static AuctionEvent from(Message message) {
            Map<String, String> map = [:]
            message.getBody().split(";").each {
                String [] values = it.split(':')
                map[values[0].trim()] = values[1].trim()
            }
            new AuctionEvent(map)
        }

        boolean isCloseEvent() {
            "CLOSE".equalsIgnoreCase(event())
        }
    }
}
