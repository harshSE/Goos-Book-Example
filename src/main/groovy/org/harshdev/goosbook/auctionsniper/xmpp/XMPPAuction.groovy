package org.harshdev.goosbook.auctionsniper.xmpp

import org.harshdev.goosbook.auctionsniper.Announcer
import org.harshdev.goosbook.auctionsniper.Auction
import org.harshdev.goosbook.auctionsniper.AuctionEventListener
import org.jivesoftware.smack.chat2.Chat

class XMPPAuction implements Auction {

    private static final String JOIN_COMMAND = "SOLVersion: 1.1; Command: JOIN;"
    private static final def BID_FOR_PRICE_OF = { int price -> "Command: BID; Price: ${price};" }
    private Chat chat;
    private Announcer<AuctionEventListener> announcer


    XMPPAuction(Chat chat, Announcer<AuctionEventListener> announcer) {
        this.announcer = announcer
        this.chat = chat;
    }

    @Override
    void bid(int price) {
        chat.send(BID_FOR_PRICE_OF(price))
    }

    @Override
    void join() {
        chat.send(JOIN_COMMAND)
    }


    void addEventListener(AuctionEventListener auctionSniper) {
        announcer.addListener(auctionSniper)
    }
}