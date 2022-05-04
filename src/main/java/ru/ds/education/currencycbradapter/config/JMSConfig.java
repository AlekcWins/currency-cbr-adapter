package ru.ds.education.currencycbradapter.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import ru.ds.education.currencycbradapter.config.properties.JMCConfigProperties;

import javax.jms.Queue;
import java.text.SimpleDateFormat;

@Configuration
@EnableJms
@Slf4j
@RequiredArgsConstructor
public class JMSConfig {

    private final JMCConfigProperties jmcConfigProperties;

    @Bean

    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        return objectMapper;
    }

    @Bean
    public Queue responseQueue() {
        return new ActiveMQQueue(jmcConfigProperties.getResponseQueue());
    }

}
