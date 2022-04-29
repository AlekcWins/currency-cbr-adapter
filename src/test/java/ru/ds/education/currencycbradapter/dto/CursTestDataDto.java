package ru.ds.education.currencycbradapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CursTestDataDto {

    @JsonProperty("CharCode")
    private String currency;

    @JsonProperty("Value")
    private BigDecimal curs;
}


