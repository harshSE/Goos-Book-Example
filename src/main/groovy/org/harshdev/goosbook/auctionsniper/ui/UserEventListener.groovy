package org.harshdev.goosbook.auctionsniper.ui

import org.harshdev.goosbook.auctionsniper.Item

trait UserEventListener {
    abstract void joinAuction(Item item)
}