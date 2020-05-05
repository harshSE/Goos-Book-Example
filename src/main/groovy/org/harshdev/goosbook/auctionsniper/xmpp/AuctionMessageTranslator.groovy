package org.harshdev.goosbook.auctionsniper.xmpp

import org.harshdev.goosbook.auctionsniper.AuctionEventListener
import org.jivesoftware.smack.packet.Message

import java.util.concurrent.CopyOnWriteArrayList

class AuctionMessageTranslator {

    private final List<AuctionEventListener> listeners
    private final String sniperId
    private LoggingXMPPFailureReporter reporter

    AuctionMessageTranslator(String sniperId, LoggingXMPPFailureReporter reporter) {
        this.reporter = reporter
        this.sniperId = sniperId
        this.listeners = [] as CopyOnWriteArrayList
    }

    void processMessage(Message message) {
        AuctionEvent auctionEvent = null;
        try {
            auctionEvent = AuctionEvent.from(message)
            if(auctionEvent.isPriceEvent()) {
                notifyCurrentPrice(auctionEvent)
            } else if(auctionEvent.isCloseEvent()){
                notifyAuctionClosed()
            }
        } catch(Exception ex) {
            reporter.canNotTranslateMessage(sniperId, message.getBody(), ex)
            notifyAuctionFailed()
        }
    }

    private void notifyAuctionFailed() {
        listeners.each {
            it.auctionFailed()
        }
    }

    void addEventListener(AuctionEventListener listener) {
        listeners.add listener
    }

    private void notifyCurrentPrice(AuctionEvent auctionEvent) {
        listeners.each {
            it.currentPrice(auctionEvent.currentPrice(), auctionEvent.increment(), auctionEvent.isFrom(sniperId))
        }
    }

    private void notifyAuctionClosed(){
        listeners.each {
            it.auctionClosed()
        }
    }

    private static class AuctionEvent {

        private final Map<String, String> fields

        AuctionEvent(Map<String, String> fields) {
            this.fields = fields
        }

        int currentPrice() {
            get("CurrentPrice") as Integer
        }

        private String get(String field) {
            String val = fields.get(field)

            if(Objects.isNull(val)) {
                throw new MissingPropertyException("$field is missing")
            }

            val
        }

        int increment() {
            get("Increment") as Integer
        }

        String event() {
            get("Event")
        }

        String getBidder() {
            get("Bidder")
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

        AuctionEventListener.PriceSource isFrom(String sniperId) {
            if(sniperId.equalsIgnoreCase(getBidder())) {
                AuctionEventListener.PriceSource.FromSniper
            } else {
                AuctionEventListener.PriceSource.FromOtherBidder
            }
        }
    }
}
