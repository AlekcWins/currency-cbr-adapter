package ru.ds.education.currencycbradapter.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@ToString
public class CursDataRequest implements Serializable {
    @JsonProperty("onDate")
    private LocalDate onDate;

}
