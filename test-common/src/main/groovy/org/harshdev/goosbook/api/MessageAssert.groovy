package org.harshdev.goosbook.api

import org.assertj.core.api.AbstractAssert
import org.jivesoftware.smack.packet.Message;

class MessageAssert extends AbstractAssert<MessageAssert, Message>{

    private Map<String, String> keyToValues;

    MessageAssert(Message message) {
        super(message, MessageAssert.class)

    }

    static MessageAssert assertThat(Message message) {
        new MessageAssert(message);
    }

    MessageAssert price(int expected) {
        final String actualPrice = price()
        if(Objects.isNull(actualPrice)) {
            failWithMessage("Expected price:${expected}, actually price is not available in message")
        }

        int val = actualPrice as Integer
        if( val != expected) {
            failWithMessage("Expected price:${expected}, actual price:${actualPrice}")
        }

        return this;
    }

    MessageAssert from(String expected) {
        isNotNull()
        final String actualAddress = actual.getFrom().toString()

        if( actualAddress != expected) {
            failWithMessage("Expected address:${expected}, actual address:${actualAddress}")
        }

        return this;
    }

    MessageAssert contains(String expected) {
        isNotNull()
        if(!actual.getBody().containsIgnoreCase(expected)) {
            failWithMessage("""
                Expected :${expected}
                Actual :${actual.getBody()}
            """)
        }
        return this;
    }

    MessageAssert command(String expected) {

        isNotNull()

        String actualCommand = command()
        if(Objects.isNull(actualCommand)) {
            failWithMessage("Expected command:${expected}, actually command is not available in message")
        }

        if(actualCommand != expected) {
            failWithMessage("Expected command:${expected}, actual command:${actualCommand}")
        }

        return this

    }



    String price() {

        if(Objects.isNull(keyToValues)) {
            keyToValues = [:]
            actual.getBody().split(";").each {
                def values = it.split(":")
                keyToValues[values[0].trim()] =  values[1].trim()
            }
        }
        keyToValues["Price"]
    }

    String command() {

        if(Objects.isNull(keyToValues)) {
            keyToValues = [:]
            actual.getBody().split(";").each {
                def values = it.split(":")
                if(values.length == 2) {
                    keyToValues[values[0].trim()] =  values[1].trim()
                }
            }
        }
        keyToValues["Command"]
    }

}
