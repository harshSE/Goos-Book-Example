package org.harshdev.goosbook.auctionsniper

import java.util.concurrent.CopyOnWriteArrayList

class SniperPortfolio implements SniperCollector{

    private final List<PortfolioListener> portfolioListeners

    SniperPortfolio() {
        portfolioListeners = [] as CopyOnWriteArrayList
    }

    void addPortfolioListener(PortfolioListener portfolioListener) {
        portfolioListeners << portfolioListener
    }

    @Override
    void addSniper(AuctionSniper auctionSniper) {
        portfolioListeners.each {
            it.sniperAdded(auctionSniper)
        }
    }
}
