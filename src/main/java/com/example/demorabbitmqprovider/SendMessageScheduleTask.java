package com.example.demorabbitmqprovider;



import com.tm.common.model.User;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


@Component
@SuppressWarnings("all")
public class SendMessageScheduleTask {
    @Autowired
    RabbitTemplate rabbitTemplate;  //使用RabbitTemplate,这提供了接收/发送等等方法

    private int i;

   /* @Scheduled(cron = "0/1 * * * * ? ")
    public void directExecute() {
        for(int i=0;i<10;i++){

            sendDirectMessage();
        }

    }*/

    @Scheduled(cron = "0/1 * * * * ? ")
    public void topicExecute() {
        for(int i=0;i<10;i++){
            sendAllTopicMessage();
            //sendManTopicMessage();
            //sendWomanTopicMessage();
        }



    }

    @GetMapping("/TestlonelyDirectExchangeMessageAck")
    public String TestlonelyDirectExchangeMessageAck() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: lonelyDirectExchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("lonelyDirectExchange", "TestDirectRouting", map);
        return "ok";
    }

    @GetMapping("/TestMessageAck")
    public String TestMessageAck() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: non-existent-exchange test message ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("non-existent-exchange", "TestDirectRouting", map);
        return "ok";
    }


    @GetMapping("/sendFanoutMessage")
    public String sendFanoutMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: testFanoutMessage ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> map = new HashMap<>();
        map.put("messageId", messageId);
        map.put("messageData", messageData);
        map.put("createTime", createTime);
        rabbitTemplate.convertAndSend("fanoutExchange", null, map);
        return "ok";
    }

    @GetMapping("/sendManTopicMessage")
    public String sendManTopicMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        String messageData = "message: man message data";
        System.out.println("发送一个消息消息："+messageId);
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> manMap = new HashMap<>();
        manMap.put("messageId", messageId);
        manMap.put("messageData", messageData);
        manMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.man", manMap);
        return "ok";
    }

    @GetMapping("/sendWomanTopicMessage")
    public String sendWomanTopicMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        System.out.println("发送一个消息消息："+messageId);
        String messageData = "message: woman message data ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String, Object> womanMap = new HashMap<>();
        womanMap.put("messageId", messageId);
        womanMap.put("messageData", messageData);
        womanMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.woman", womanMap);
        return "ok";
    }

    @GetMapping("/sendAllTopicMessage")
    public String sendAllTopicMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        System.out.println("发送一个消息消息："+messageId);
        String messageData = "message:  all message data ";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
 /*     Map<String, Object> allMap = new HashMap<>();
        allMap.put("messageId", messageId);
        allMap.put("messageData", messageData);
        allMap.put("createTime", createTime);
        rabbitTemplate.convertAndSend("topicExchange", "topic.all", allMap);

*/
        User user = new User();
        user.setName("michael");
        user.setAge(37);
        rabbitTemplate.convertAndSend("topicExchange", "topic.all", user);
        return "ok";
    }

    public String sendDirectMessage() {
        String messageId = String.valueOf(UUID.randomUUID());
        System.out.println("发送一个消息消息："+messageId);
        String messageData = "test message, hello!";
        String createTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        Map<String,Object> map=new HashMap<>();
        map.put("messageId",messageId);
        map.put("messageData",messageData);
        map.put("createTime",createTime);
        //将消息携带绑定键值：TestDirectRouting 发送到交换机TestDirectExchange
        rabbitTemplate.convertAndSend("TestDirectExchange", "TestDirectRouting", map);
        return "ok";
    }
}
