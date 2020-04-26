package org.harshdev.goosbook.auctionsniper.xmpp

import org.harshdev.goosbook.auctionsniper.AuctionEventListener
import org.harshdev.goosbook.auctionsniper.xmpp.AuctionMessageTranslator
import org.jivesoftware.smack.packet.Message
import spock.lang.Specification

import static org.harshdev.goosbook.auctionsniper.AuctionEventListener.PriceSource.FromOtherBidder
import static org.harshdev.goosbook.auctionsniper.AuctionEventListener.PriceSource.FromSniper


class AuctionMessageTranslatorSpec  extends Specification{

    private AuctionMessageTranslator translator;
    private AuctionEventListener listener;
    private static String SNIPER_ID = "sniper"

    def setup(){
        listener = Mock()
        translator = new AuctionMessageTranslator(SNIPER_ID, listener);
    }


    def "notify auction closed when closed message received"() {
        given:
        String closeEvent = "SOLVersion: 1.1; Event: CLOSE;";

        Message message = new Message();
        message.setBody(closeEvent);

        when:
        translator.processMessage(message);

        then:
        1 * listener.auctionClosed()
    }

    def "notify current price detail when price message received from other bidder" () {
        given:
        String priceEvent =  "Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;"

        Message message = new Message()
        message.setBody(priceEvent)

        when:
        translator.processMessage(message)

        then:
        1 * listener.currentPrice(192, 7, FromOtherBidder)


    }

    def "notify current price detail when price message received from sniper" () {
        given:
        String priceEvent =  "Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: ${SNIPER_ID};"

        Message message = new Message()
        message.setBody(priceEvent)

        when:
        translator.processMessage(message)

        then:
        1 * listener.currentPrice(192, 7, FromSniper)


    }




}
