package com.wzwl.parking.mq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName CarDataSender
 * @Description 车辆数据消息生产者
 * @Author yangwu
 * @Date 2020/11/14 14:53
 * @Version 1.0
 */
@Component
public class CarDataSender {

    @Resource
    private RabbitTemplate rabbitTemplate;

    public void sendTest(String msg) throws Exception{
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setConfirmCallback(confirmCallback);
        CorrelationData correlationData = new CorrelationData();
        rabbitTemplate.convertAndSend("car-exchange",
                "car.report",
                msg,
                correlationData);;
    }

    /**
     * 回调函数
     */
    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.err.println("correlationData: " + correlationData);
            String messageId = correlationData.getId();
            if(ack){
                //如果confirm返回成功 则进行更新
                System.out.println("消息成功发送到队列===============messageId="+messageId);
            } else {
                //失败则进行具体的后续操作:重试 或者补偿等手段
                System.err.println("异常处理...");
            }
        }
    };

    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {

        }
    };

}
