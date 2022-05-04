package ru.ds.education.currencycbradapter.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.ds.education.currencycbradapter.config.properties.WSDLConfigProperties;
import ru.ds.education.currencycbradapter.generated.DailyInfo;

import javax.xml.ws.BindingProvider;

@Configuration
@RequiredArgsConstructor
public class WSDLConfig {

    private final WSDLConfigProperties wsdlConfigProperties;

    @Bean
    public DailyInfo dailyInfoService() {
        DailyInfo dailyInfo = new DailyInfo();
        BindingProvider bp = (BindingProvider) dailyInfo.getDailyInfoSoap();
        bp.getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, wsdlConfigProperties.getEndpoint());
        return dailyInfo;
    }


}
