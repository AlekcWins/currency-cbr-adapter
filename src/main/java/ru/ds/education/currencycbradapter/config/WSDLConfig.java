package ru.ds.education.currencycbradapter.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ds.education.currencycbradapter.config.properties.WSDLConfigProperties;
import ru.ds.education.currencycbradapter.generated.DailyInfo;

import javax.xml.ws.BindingProvider;

@Configuration
public class WSDLConfig {

    private final WSDLConfigProperties wsdlConfigProperties;

    @Autowired
    public WSDLConfig(WSDLConfigProperties wsdlConfigProperties) {
        this.wsdlConfigProperties = wsdlConfigProperties;
    }

    @Bean
    public DailyInfo dailyInfoService() {
        DailyInfo dailyInfo = new DailyInfo();
        BindingProvider bp = (BindingProvider) dailyInfo.getDailyInfoSoap();
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlConfigProperties.getEndpoint());
        return dailyInfo;
    }


}
