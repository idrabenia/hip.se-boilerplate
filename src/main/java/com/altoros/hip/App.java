package com.altoros.hip;

import org.apache.camel.spring.boot.CamelAutoConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jms.activemq.ActiveMQAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContext;
import se.hip.sdk.bootstrap.Hip;
import se.hip.sdk.listener.PropertyListener;

/**
 * Hello world!
 *
 */
@EnableAutoConfiguration(exclude = {CamelAutoConfiguration.class, ActiveMQAutoConfiguration.class})
@SpringBootApplication
public class App {

    private static Hip hip;

    public static void main(String[] args) throws Exception {
        final SpringApplicationBuilder builder  = new SpringApplicationBuilder();
        builder.listeners(new PropertyListener());
        builder.sources(Hip.class, App.class);

        final SpringApplication app = builder.build();

        ApplicationContext context = app.run();

        context.getBean(ExampleService.class).fetchCareContacts();
    }

}
