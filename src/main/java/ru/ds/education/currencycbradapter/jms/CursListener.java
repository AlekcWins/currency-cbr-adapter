package ru.ds.education.currencycbradapter.jms;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Service;
import ru.ds.education.currencycbradapter.dto.CursDataRequest;
import ru.ds.education.currencycbradapter.service.CbrWebService;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Service
@Slf4j
@AllArgsConstructor
public class CursListener {
    private final CbrWebService webService;
    private final CursSender sender;
    private final ObjectMapper mapper;

    @JmsListener(destination = "#{@JMCConfigProperties.requestQueue}")
    public void listen(final TextMessage message) throws JMSException {
        try {
            CursDataRequest request = mapper.readValue(message.getText(), CursDataRequest.class);
            log.info("Parsed: {}", request);
            sender.send(webService.fetchCursesData(request), message.getJMSCorrelationID());
        } catch (JsonProcessingException e) {
            log.error("Unparsable message received " + message.getText());
        }
    }
}
