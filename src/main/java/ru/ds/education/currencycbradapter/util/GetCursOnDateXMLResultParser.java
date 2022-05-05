package ru.ds.education.currencycbradapter.util;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import ru.ds.education.currencycbradapter.dto.CursDto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class GetCursOnDateXMLResultParser {

    private GetCursOnDateXMLResultParser() {
    }

    public static List<CursDto> parse(Element xmlResult) {
        NodeList cursNodes = xmlResult.getElementsByTagName("ValuteCursOnDate");
        List<CursDto> parsedResult = new ArrayList<>();
        NodeList childNodes;
        for (int i = 0; i < cursNodes.getLength(); i++) {
            childNodes = cursNodes.item(i).getChildNodes();
            parsedResult.add(new CursDto(childNodes.item(4).getTextContent(), new BigDecimal((childNodes.item(2).getTextContent()))));
        }
        return parsedResult;
    }
}
