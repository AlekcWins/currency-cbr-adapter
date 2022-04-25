package ru.ds.education.currencycbradapter.config;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.support.converter.MessageConversionException;
import org.springframework.jms.support.converter.MessageConverter;
import ru.ds.education.currencycbradapter.dto.CursDataRequest;
import ru.ds.education.currencycbradapter.dto.CursDataResponse;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;
import java.text.SimpleDateFormat;

@Configuration
@EnableJms
@Slf4j
public class JMSConfig {


    @Bean
    public DefaultJmsListenerContainerFactory containerFactory() {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setSessionTransacted(true);
        factory.setMaxMessagesPerTask(1);
        factory.setConcurrency("1-5");
        return factory;
    }

    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd"));
        return objectMapper;
    }

    @Bean
    public MessageConverter messageConverter(ObjectMapper mapper) {
        return new MessageConverter() {
            @Override
            public Message toMessage(Object object, Session session) throws JMSException, MessageConversionException {
                TextMessage message = new ActiveMQTextMessage();
                String json = null;
                try {
                    if (object instanceof CursDataResponse) {

                        json = mapper.writeValueAsString((CursDataResponse) object);
                    }
                } catch (JsonProcessingException ignored) {
                    log.error(ignored.getMessage());
                }
                message.setText(json);
                return message;
            }

            @Override
            public Object fromMessage(Message message) throws JMSException, MessageConversionException {
                CursDataRequest request = null;
                try {
                    request = mapper.readValue(((TextMessage) message).getText(), CursDataRequest.class);
                } catch (JsonProcessingException ignored) {
                    log.error(ignored.getMessage());
                }
                return request;
            }
        };
    }


}
