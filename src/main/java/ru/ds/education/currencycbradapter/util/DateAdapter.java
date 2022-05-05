package ru.ds.education.currencycbradapter.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateAdapter extends XmlAdapter<String, LocalDate> {
    private static final String CUSTOM_FORMAT_STRING = "yyyy-MM-dd";

    @Override
    public String marshal(LocalDate v) {
        return  DateTimeFormatter.ofPattern(CUSTOM_FORMAT_STRING).format(v);
    }

    @Override
    public LocalDate unmarshal(String v) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(CUSTOM_FORMAT_STRING);
        return LocalDate.parse(v, formatter);
    }

}
