package ru.ds.education.currencycbradapter.service;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.w3c.dom.Element;
import ru.ds.education.currencycbradapter.dto.CursDataRequest;
import ru.ds.education.currencycbradapter.dto.CursDataResponse;
import ru.ds.education.currencycbradapter.dto.CursDto;
import ru.ds.education.currencycbradapter.generated.DailyInfo;
import ru.ds.education.currencycbradapter.generated.DailyInfoSoap;
import ru.ds.education.currencycbradapter.generated.GetCursOnDateXMLResponse;

import java.time.LocalDate;
import java.util.List;

import static ru.ds.education.currencycbradapter.util.GetCursOnDateXMLResultParser.parse;

@Service
@AllArgsConstructor
public class CbrWebService {

    private final DailyInfo service;

    public CursDataResponse fetchCursesData(CursDataRequest request) {
        DailyInfoSoap port = service.getDailyInfoSoap();
        LocalDate onDate = request.getOnDate();
        GetCursOnDateXMLResponse.GetCursOnDateXMLResult result =
                port.getCursOnDateXML(onDate);
        List<CursDto> rates = parse((Element) result.getContent().get(0));
        return new CursDataResponse(onDate, rates);
    }
}
