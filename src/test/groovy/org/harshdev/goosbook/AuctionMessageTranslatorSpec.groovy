package org.harshdev.goosbook

import org.jivesoftware.smack.packet.Message
import spock.lang.Specification


class AuctionMessageTranslatorSpec  extends Specification{

    private AuctionMessageTranslator translator;
    private AuctionEventListener listener;

    def setup(){
        listener = Mock()
        translator = new AuctionMessageTranslator(listener);
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

    def "notify current price detail when price message received" () {
        given:
            String priceEvent =  "Event: PRICE; CurrentPrice: 192; Increment: 7; Bidder: Someone else;"

            Message message = new Message()
            message.setBody(priceEvent)

        when:
            translator.processMessage(message)

        then:
         1 * listener.currentPrice(192, 7)


    }




}
