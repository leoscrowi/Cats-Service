package is.tech.owners.rabbitmq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OwnersRabbitMQConfig {


    public static final String EXCHANGE_NAME = "owners-exchange";

    public static final String GET_BY_ID_QUEUE = "owners-get-by-id-queue";
    public static final String GET_ALL_QUEUE = "owners-get-all-queue";
    public static final String CREATE_QUEUE = "owners-create-queue";
    public static final String DELETE_BY_ID_QUEUE = "owners-delete-by-id-queue";
    public static final String DELETE_ALL_QUEUE = "owners-delete-all-queue";

    @Bean
    public Jackson2JsonMessageConverter jsonConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jsonConverter());
        factory.setDefaultRequeueRejected(false);
        return factory;
    }

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue getOwnerByIdQueue() {
        return new Queue(GET_BY_ID_QUEUE);
    }

    @Bean
    public Binding getOwnerByIdBinding(DirectExchange exchange) {
        return BindingBuilder.bind(getOwnerByIdQueue()).to(exchange).with("get-by-id");
    }

    @Bean
    public Queue getAllOwnersQueue() {
        return new Queue(GET_ALL_QUEUE);
    }

    @Bean
    public Binding getAllCatsBinding(DirectExchange exchange) {
        return BindingBuilder.bind(getAllOwnersQueue()).to(exchange).with("get-all");
    }

    @Bean
    public Queue createOwnerQueue() {
        return new Queue(CREATE_QUEUE);
    }

    @Bean
    public Binding createCatBinding(DirectExchange exchange) {
        return BindingBuilder.bind(createOwnerQueue()).to(exchange).with("create");
    }

    @Bean
    public Queue deleteOwnerByIdQueue() {
        return new Queue(DELETE_BY_ID_QUEUE);
    }

    @Bean
    public Binding deleteOwnerByIdBinding(DirectExchange exchange) {
        return BindingBuilder.bind(deleteOwnerByIdQueue()).to(exchange).with("delete-by-id");
    }


    @Bean
    public Queue deleteAllOwnersQueue() {
        return new Queue(DELETE_ALL_QUEUE);
    }

    @Bean
    public Binding deleteAllOwnersBinding(DirectExchange exchange) {
        return BindingBuilder.bind(deleteAllOwnersQueue()).to(exchange).with("delete-all");
    }
}
