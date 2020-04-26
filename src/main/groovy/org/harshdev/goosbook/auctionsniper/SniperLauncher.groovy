package org.harshdev.goosbook.auctionsniper


import org.harshdev.goosbook.auctionsniper.ui.UserEventListener
import org.harshdev.goosbook.auctionsniper.xmpp.AuctionHouse
import org.harshdev.goosbook.auctionsniper.xmpp.XMPPAuction

class SniperLauncher implements UserEventListener{

    private final AuctionHouse auctionHouse
    private final SniperCollector collector
    private final TaskExecutor executor

    SniperLauncher(AuctionHouse auctionHouse, SniperCollector collector, TaskExecutor executor) {
        this.executor = executor
        this.auctionHouse = auctionHouse
        this.collector = collector
    }

    @Override
    void joinAuction(String item) {
        executor.execute(() -> join(item))
    }

    private void join(String item) {
        XMPPAuction auction = auctionHouse.auctionFor(item);
        AuctionSniper sniper = new AuctionSniper(item, auction)
        auction.addEventListener(sniper)
        collector.addSniper(sniper)
        auction.join()
    }
}
