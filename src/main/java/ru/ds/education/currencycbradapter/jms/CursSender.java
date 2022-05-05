package ru.ds.education.currencycbradapter.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;
import ru.ds.education.currencycbradapter.dto.CursDataResponse;

import javax.jms.Queue;
import javax.jms.TextMessage;

@Service
@Slf4j
@AllArgsConstructor
public class CursSender {

    private final JmsTemplate jmsTemplate;
    private final ObjectMapper mapper;
    private final Queue responseQueue;


    public void send(final CursDataResponse response, final String correlationId) {
        jmsTemplate.send(responseQueue, s -> {
            TextMessage message = new ActiveMQTextMessage();
            try {
                message.setText(mapper.writeValueAsString(response));
            } catch (JsonProcessingException e) {
                log.error("parse  object error " + e.getMessage());
            }
            message.setJMSCorrelationID(correlationId);
            return message;
        });
    }
}
