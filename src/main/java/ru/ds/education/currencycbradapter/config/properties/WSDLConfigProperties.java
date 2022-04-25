package ru.ds.education.currencycbradapter.config.properties;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotEmpty;

@Component
@ConfigurationProperties(prefix = "web-service")
@Validated
@Getter
@Setter
public class WSDLConfigProperties {


    @NotEmpty
    private String endpoint;

}
