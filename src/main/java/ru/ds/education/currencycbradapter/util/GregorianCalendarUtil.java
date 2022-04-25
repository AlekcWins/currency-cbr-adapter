package ru.ds.education.currencycbradapter.util;

import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.GregorianCalendar;

@UtilityClass
@Slf4j
public class GregorianCalendarUtil {
    private static DatatypeFactory datatypeFactory;

    static{
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            datatypeFactory = null;
            log.error("Datatype configuration error");
        }
    }

    public static XMLGregorianCalendar getXMLGregorianCalendar(LocalDate date) {
        if (datatypeFactory == null) {
            log.error("Trying to init XMLGregorianCalendar through null factory");
            throw new UnsupportedOperationException();
        }
        return datatypeFactory.newXMLGregorianCalendar(GregorianCalendar.from(date.atStartOfDay(ZoneId.systemDefault())));
    }
}
