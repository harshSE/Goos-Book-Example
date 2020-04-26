package org.harshdev.goosbook.auctionsniper

import spock.lang.Specification

class SniperPortfolioSpec extends Specification {

    private SniperPortfolio portfolio = new SniperPortfolio()



    def "notify lister about auction added"() {
        given:
            AuctionSniper auctionSniper = Mock()
            PortfolioListener listener = Mock()
            portfolio.addPortfolioListener(listener)
        when:
            portfolio.addSniper(auctionSniper)

        then:
        1* listener.sniperAdded(auctionSniper)
    }
}
