package minskim2.JHP_World.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    @Value("${spring.rabbitmq.pub-queue}")
    private String pubQueue;
    @Value("${spring.rabbitmq.sub-queue}")
    private String subQueue;
    @Value("${spring.rabbitmq.pub-exchange}")
    private String pubExchange;
    @Value("${spring.rabbitmq.sub-exchange}")
    private String subExchange;
    @Value("${spring.rabbitmq.pub-routing-key}")
    private String pubRoutingKey;
    @Value("${spring.rabbitmq.sub-routing-key}")
    private String subRoutingKey;


    /**
     * 지정된 Queue 이름으로 Queue Bean 생성
     */
    @Bean
    public Queue pubQueue() {
        return new Queue(pubQueue, true);
    }

    @Bean
    public Queue subQueue() {
        return new Queue(subQueue, true);
    }

    /**
     * 지정된 Exchange 이름으로 Direct Exchange Bean 을 생성
     */
    @Bean
    public DirectExchange pubExchange() {
        return new DirectExchange(pubExchange);
    }
    @Bean
    public DirectExchange subExchange() {
        return new DirectExchange(subExchange);
    }

    /**
     * 주어진 Queue 와 Exchange 을 Binding 하고 Routing Key 을 이용하여 Binding Bean 생성
     * Exchange 에 Queue 을 등록한다고 이해하면 됨
     **/
    @Bean
    public Binding bindingPub(Queue pubQueue, DirectExchange pubExchange) {
        return BindingBuilder.bind(pubQueue).to(pubExchange).with(pubRoutingKey);
    }
    @Bean
    public Binding bindingSub(Queue subQueue, DirectExchange subExchange) {
        return BindingBuilder.bind(subQueue).to(subExchange).with(subRoutingKey);
    }


    /**
     * RabbitTemplate
     * ConnectionFactory 로 연결 후 실제 작업을 위한 Template
     */
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

    /**
     * MessageConverter
     * RabbitTemplate 에서 사용할 MessageConverter 를 Bean 으로 등록
     */
    @Bean
    public Jackson2JsonMessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }
}
