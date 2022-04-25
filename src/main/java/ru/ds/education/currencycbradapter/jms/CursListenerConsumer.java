package ru.ds.education.currencycbradapter.jms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;
import ru.ds.education.currencycbradapter.dto.CursDataRequest;
import ru.ds.education.currencycbradapter.dto.CursDataResponse;
import ru.ds.education.currencycbradapter.service.CbrWebService;

@Component
public class CursListenerConsumer {

    private final CbrWebService webService;

    @Autowired
    public CursListenerConsumer(CbrWebService webService) {
        this.webService = webService;
    }

    @JmsListener(destination = "#{@JMCConfigProperties.requestQueue}")
    @SendTo("#{@JMCConfigProperties.responseQueue}")
    public CursDataResponse send(CursDataRequest request) {
        return webService.fetchCursesData(request);
    }

}
