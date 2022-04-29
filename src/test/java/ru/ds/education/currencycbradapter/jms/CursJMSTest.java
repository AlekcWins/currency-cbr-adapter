package ru.ds.education.currencycbradapter.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQTextMessage;
import org.assertj.core.api.Assertions;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.jms.core.JmsTemplate;
import ru.ds.education.currencycbradapter.BaseActiveMQContainer;
import ru.ds.education.currencycbradapter.config.properties.JMCConfigProperties;
import ru.ds.education.currencycbradapter.dto.CursDataRequest;
import ru.ds.education.currencycbradapter.dto.CursDataResponse;
import ru.ds.education.currencycbradapter.dto.CursDto;
import ru.ds.education.currencycbradapter.service.CbrWebService;

import javax.jms.JMSException;
import javax.jms.TextMessage;
import javax.xml.parsers.ParserConfigurationException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@Slf4j
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class CursJMSTest extends BaseActiveMQContainer {

    private final JmsTemplate jmsTemplate;
    private final JMCConfigProperties jmcConfigProperties;

    private final ObjectMapper mapper;

    @MockBean
    private CbrWebService service;


    @Autowired
    CursJMSTest(JmsTemplate jmsTemplate, JMCConfigProperties jmcConfigProperties, ObjectMapper mapper) {
        this.jmsTemplate = jmsTemplate;
        this.jmcConfigProperties = jmcConfigProperties;
        this.mapper = mapper;
    }


    @Test
    void testJMSConnection() {
        Assertions.assertThatCode(() ->
                jmsTemplate.send("test", x -> {
                    ActiveMQTextMessage message = new ActiveMQTextMessage();
                    message.setText("test");
                    return message;
                })
        ).doesNotThrowAnyException();
    }


    @Test
    void firstMessageTestWitOutCorrelationId() throws JMSException, JsonProcessingException {
        LocalDate onDate = LocalDate.now();
        CursDataResponse testData = getTestData(onDate);
        setupMock(testData);
        CursDataRequest request = new CursDataRequest(onDate);
        CursDataResponse expectedResponse = service.fetchCursesData(request);
        TextMessage firstMessage = new ActiveMQTextMessage();

        firstMessage.setText(mapper.writeValueAsString(request));
        jmsTemplate.send(jmcConfigProperties.getRequestQueue(), x -> firstMessage);
        TextMessage message = (TextMessage) jmsTemplate.receive(jmcConfigProperties.getResponseQueue());
        assert message != null;
        Assert.assertNull(message.getJMSCorrelationID());
        CursDataResponse response = mapper.readValue(message.getText(), CursDataResponse.class);
        Assert.assertEquals(expectedResponse, response);


    }

    @Test
    void secondMessageTestWitOutCorrelationId() throws JMSException, JsonProcessingException {
        LocalDate onDateTwo = LocalDate.now().minusDays(5);
        String correlationId = "testCorrelation";
        CursDataResponse testDataTwo = getTestData(onDateTwo);
        CursDataRequest requestTwo = new CursDataRequest(onDateTwo);
        setupMock(testDataTwo);
        CursDataResponse expectedRequestTwo = service.fetchCursesData(requestTwo);
        TextMessage secondMessage = new ActiveMQTextMessage();
        secondMessage.setJMSCorrelationID(correlationId);

        secondMessage.setText(mapper.writeValueAsString(requestTwo));
        jmsTemplate.send(jmcConfigProperties.getRequestQueue(), x -> secondMessage);
        TextMessage secondMessageResponse = (TextMessage) jmsTemplate.receive(jmcConfigProperties.getResponseQueue());
        assert secondMessageResponse != null;
        Assert.assertEquals(secondMessageResponse.getJMSCorrelationID(), correlationId);
        CursDataResponse secondResponse = mapper.readValue(secondMessageResponse.getText(), CursDataResponse.class);
        Assert.assertEquals(expectedRequestTwo, secondResponse);


    }

    CursDataResponse getTestData(LocalDate onDate) {
        List<String> givenList = Arrays.asList("EUR", "USD");
        Random rand = new Random();
        String randomCurs = givenList.get(rand.nextInt(givenList.size()));
        String randomCursTwo = givenList.get(rand.nextInt(givenList.size()));
        CursDataResponse testResponse = new CursDataResponse();
        List<CursDto> curses = new ArrayList<>();
        curses.add(new CursDto(randomCurs, BigDecimal.valueOf(Math.random())));
        curses.add(new CursDto(randomCursTwo, BigDecimal.valueOf(Math.random())));
        testResponse.setOnDate(onDate);
        testResponse.setRates(curses);
        return testResponse;
    }

    void setupMock(CursDataResponse testData) {

        when(service.fetchCursesData(any()))
                .thenAnswer(invocation -> {
                    try {
                        CursDataRequest request = (CursDataRequest) invocation.getArguments()[0];
                        return testData;
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });
    }

}