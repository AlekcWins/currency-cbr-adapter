package ru.ds.education.currencycbradapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class CursDto implements Serializable {
    @JsonProperty("currency")
    private String currency;

    @JsonProperty("curs")
    private BigDecimal curs;
}
