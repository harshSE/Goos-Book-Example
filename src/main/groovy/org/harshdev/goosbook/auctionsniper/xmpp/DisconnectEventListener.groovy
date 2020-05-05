package org.harshdev.goosbook.auctionsniper.xmpp

import org.harshdev.goosbook.auctionsniper.AuctionEventListener
import org.jivesoftware.smack.chat2.IncomingChatMessageListener

class DisconnectEventListener implements AuctionEventListener{
    private final ChatDisconnectEventListener listener;
    private final IncomingChatMessageListener incomingChatMessageListener;

    DisconnectEventListener(ChatDisconnectEventListener listener, IncomingChatMessageListener incomingChatMessageListener) {
        this.listener = listener
        this.incomingChatMessageListener = incomingChatMessageListener
    }

    @Override
    void auctionClosed() {
        disconnect()
    }

    @Override
    void currentPrice(int price, int increment, PriceSource priceSource) {
        //Nothing to do
    }

    @Override
    void auctionFailed() {
        disconnect()
    }

    private disconnect() {
        listener.disconnect(incomingChatMessageListener)
    }
}
