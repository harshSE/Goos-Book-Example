package org.harshdev.goosbook

interface SniperListener extends EventListener {
    void sniperLost()
    void bidding()

    void winning()

    void sniperWon()
}
