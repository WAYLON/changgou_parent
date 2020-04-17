package com.changgou.canal.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {
    //定义队列名称
    public static final String AD_UPDATE_QUEUE = "ad_update_queue";

    //定义队列名称
    private static final String SEARCH_ADD_QUEUE = "search_add_queue";

    //交换机名称
    private static final String GOODS_UP_EXCHANGE = "goods_up_exchange";

    /**
     * 声明队列
     *
     * @return
     */
    @Bean(AD_UPDATE_QUEUE)
    public Queue queue() {
        return new Queue(AD_UPDATE_QUEUE);
    }

    /**
     * 声明队列
     *
     * @return
     */
    @Bean(SEARCH_ADD_QUEUE)
    public Queue AD_UPDATE_QUEUE() {
        return new Queue(SEARCH_ADD_QUEUE);
    }

    /**
     * 声明交换机
     *
     * @return
     */
    @Bean(GOODS_UP_EXCHANGE)
    public Exchange GOODS_UP_EXCHANGE() {
        return ExchangeBuilder.fanoutExchange(GOODS_UP_EXCHANGE).durable(true).build();
    }

    /**
     * 队列绑定交换机
     *
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding AD_UPDATE_QUEUE_BINDING(@Qualifier(AD_UPDATE_QUEUE) Queue queue, @Qualifier(GOODS_UP_EXCHANGE) Exchange exchange) {
        return BindingBuilder.bind(queue).to(exchange).with("").noargs();

    }

}