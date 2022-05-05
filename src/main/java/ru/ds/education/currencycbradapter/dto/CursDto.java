package ru.ds.education.currencycbradapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class CursDto implements Serializable {
    @JsonProperty("currency")
    private String currency;

    @JsonProperty("curs")
    private BigDecimal curs;
}
