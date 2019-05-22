package com.consumer;

import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
@Service
public class TopicConsumer1 implements MessageListener {
    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = (TextMessage)message;
        try {
            System.out.println("消费的TopicConsumer1获取消息:"+textMessage.getText());
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
