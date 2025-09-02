package is.tech.cats.rabbitmq;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CatsRabbitMQConfig {

    public static final String EXCHANGE_NAME = "cats-exchange";
    public static final String GET_BY_ID_QUEUE = "cats-get-by-id-queue";
    public static final String GET_ALL_QUEUE = "cats-get-all-queue";
    public static final String CREATE_QUEUE = "cats-create-queue";
    public static final String DELETE_BY_ID_QUEUE = "cats-delete-by-id-queue";
    public static final String DELETE_ALL_QUEUE = "cats-delete-all-queue";
    public static final String GET_BY_OWNER_ID_QUEUE = "cats-get-by-owner-id-queue";

    public static final String GET_ALL_FRIENDSHIPS_QUEUE = "friends-get-all-queue";
    public static final String DELETE_FRIENDS_QUEUE = "friends-delete-queue";
    public static final String CREATE_FRIENDS_QUEUE = "friends-create-queue";

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
    public Queue getCatByIdQueue() {
        return new Queue(GET_BY_ID_QUEUE);
    }

    @Bean
    public Binding getCatByIdBinding(DirectExchange exchange) {
        return BindingBuilder.bind(getCatByIdQueue()).to(exchange).with("get-by-id");
    }

    @Bean
    public Queue getAllCatsQueue() {
        return new Queue(GET_ALL_QUEUE);
    }

    @Bean
    public Binding getAllCatsBinding(DirectExchange exchange) {
        return BindingBuilder.bind(getAllCatsQueue()).to(exchange).with("get-all");
    }

    @Bean
    public Queue createCatQueue() {
        return new Queue(CREATE_QUEUE);
    }

    @Bean
    public Binding createCatBinding(DirectExchange exchange) {
        return BindingBuilder.bind(createCatQueue()).to(exchange).with("create");
    }

    @Bean
    public Queue deleteCatByIdQueue() {
        return new Queue(DELETE_BY_ID_QUEUE);
    }

    @Bean
    public Binding deleteCatByIdBinding(DirectExchange exchange) {
        return BindingBuilder.bind(deleteCatByIdQueue()).to(exchange).with("delete-by-id");
    }


    @Bean
    public Queue deleteAllCatsQueue() {
        return new Queue(DELETE_ALL_QUEUE);
    }

    @Bean
    public Binding deleteAllCatsBinding(DirectExchange exchange) {
        return BindingBuilder.bind(deleteAllCatsQueue()).to(exchange).with("delete-all");
    }

    @Bean
    public Queue getCatsByOwnerIdQueue() {
        return new Queue(GET_BY_OWNER_ID_QUEUE);
    }

    @Bean
    public Binding getCatsByOwnerIdBinding(DirectExchange exchange) {
        return BindingBuilder.bind(getCatsByOwnerIdQueue()).to(exchange).with("get-by-owner-id");
    }

    @Bean
    public Queue getAllFriendshipsQueue() {
        return new Queue(GET_ALL_FRIENDSHIPS_QUEUE);
    }

    @Bean
    public Binding getAllFriendshipsBinding(DirectExchange exchange) {
        return BindingBuilder.bind(getAllFriendshipsQueue()).to(exchange).with("get-all-friendships");
    }

    @Bean
    public Queue createFriendshipsQueue() {
        return new Queue(CREATE_FRIENDS_QUEUE);
    }

    @Bean
    public Binding createFriendshipsBinding(DirectExchange exchange) {
        return BindingBuilder.bind(createFriendshipsQueue()).to(exchange).with("create-friendship");
    }

    @Bean
    public Queue deleteFriendshipsQueue() {
        return new Queue(DELETE_FRIENDS_QUEUE);
    }

    @Bean
    public Binding deleteFriendshipsBinding(DirectExchange exchange) {
        return BindingBuilder.bind(deleteFriendshipsQueue()).to(exchange).with("delete-friendship");
    }
}
