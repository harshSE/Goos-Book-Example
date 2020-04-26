package org.harshdev.goosbook.auctionsniper

interface SniperListener extends EventListener{

    void sniperStateChanged(SniperSnapShot snapShot)
}
