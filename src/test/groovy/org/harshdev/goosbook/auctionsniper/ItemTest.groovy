package org.harshdev.goosbook.auctionsniper

import spock.lang.Specification

class ItemTest extends Specification {

    def "not allowed when price is higher than stop price"() {

        given:
        Item item = new Item("test-item", 100)

        when:
        boolean result = item.allowBid(101)

        then:
        result == false
    }

    def "not allowed when price is lower than stop price"() {

        given:
        Item item = new Item("test-item", 100)

        when:
        boolean result = item.allowBid(99)

        then:
        result == true
    }

    def "not allowed when price is equal than stop price"() {

        given:
        Item item = new Item("test-item", 100)

        when:
        boolean result = item.allowBid(100)

        then:
        result == true
    }

}
