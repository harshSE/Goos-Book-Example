package org.harshdev.goosbook.auctionsniper.xmpp

import org.harshdev.goosbook.auctionsniper.AuctionEventListener
import org.jivesoftware.smack.chat2.IncomingChatMessageListener
import spock.lang.Ignore
import spock.lang.Specification

class DisconnectEventListenerSpec extends Specification {
    private ChatDisconnectEventListener chatDisconnectEventListener;
    private IncomingChatMessageListener listener;
    private DisconnectEventListener disconnectEventListener;

    def setup(){
        chatDisconnectEventListener = Mock()
        listener = Mock()
        disconnectEventListener = new DisconnectEventListener(chatDisconnectEventListener, listener)
    }

    def "disconnect from chat on auction closed"() {
        when:
        disconnectEventListener.auctionClosed()

        then:
        1 * chatDisconnectEventListener.disconnect(listener)
    }

    @Ignore
    def "no interaction during price change"() {
        when:
        disconnectEventListener.currentPrice(0, 0, AuctionEventListener.PriceSource.FromOtherBidder)

        then:
        1 * chatDisconnectEventListener.disconnect(listener)
    }

    def "disconnect from chat on auction fail"() {
        when:
        disconnectEventListener.auctionFailed()

        then:
        1 * chatDisconnectEventListener.disconnect(listener)
    }
}
