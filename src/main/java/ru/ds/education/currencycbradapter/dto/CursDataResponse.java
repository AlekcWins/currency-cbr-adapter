package ru.ds.education.currencycbradapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CursDataResponse implements Serializable {
    @JsonProperty("onDate")
    private LocalDate onDate;

    @JsonProperty("rates")
    private List<CursDto> rates;
}
