package org.harshdev.goosbook.auctionsniper


import org.harshdev.goosbook.auctionsniper.xmpp.AuctionHouse
import org.harshdev.goosbook.auctionsniper.xmpp.XMPPAuction
import spock.lang.Specification

class SniperLauncherSpec extends Specification {

    private AuctionHouse auctionHouse
    private SniperLauncher launcher
    private XMPPAuction auction
    private SniperCollector collector

    def setup() {
        auctionHouse = Mock()
        auction = Mock()
        collector = Mock()
        launcher = new SniperLauncher(auctionHouse, collector, (Runnable runnable) -> runnable.run())
    }



    def "add new sniper to collector and then join"() {

        given:
        def item = "test-item"
        auctionHouse.auctionFor(item) >> auction


        when:
        launcher.joinAuction(new Item(item, 1000))

        then:
        1* collector.addSniper(_)

        then:
        1* auction.join()
    }
}
