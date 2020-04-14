package org.harshdev.goosbook

import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.packet.Message
import org.junit.jupiter.api.Assertions

import java.util.concurrent.ArrayBlockingQueue
import java.util.concurrent.TimeUnit

import static org.junit.jupiter.api.Assertions.assertNotNull

class SingleMessageListener {

    private ArrayBlockingQueue<Message> messages = new ArrayBlockingQueue<>(1);

    void processMessage(Message message, Chat chat) {
        messages << message
    }

    void receiveAMessage() throws InterruptedException {
        assertNotNull(messages.poll(5, TimeUnit.SECONDS), "Message")
    }



}
