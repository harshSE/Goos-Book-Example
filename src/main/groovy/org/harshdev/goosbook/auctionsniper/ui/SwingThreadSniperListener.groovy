package org.harshdev.goosbook.auctionsniper.ui

import org.harshdev.goosbook.auctionsniper.SniperListener
import org.harshdev.goosbook.auctionsniper.SniperSnapShot

import javax.swing.*

class SwingThreadSniperListener implements SniperListener {

    private SniperTableModel model

    SwingThreadSniperListener(SniperTableModel model) {
        this.model = model
    }

    @Override
    void sniperStateChanged(SniperSnapShot snapShot) {
        SwingUtilities.invokeAndWait(() -> model.sniperStateChanged(snapShot))
    }
}