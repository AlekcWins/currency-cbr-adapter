package ru.ds.education.currencycbradapter.service;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import ru.ds.education.currencycbradapter.dto.*;
import ru.ds.education.currencycbradapter.util.CursJsonReader;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
class CbrWebServiceTest {
    private final CbrWebService webService;
    private final CursMapperTestData mapper;

    @Autowired
    CbrWebServiceTest(CbrWebService webService) {
        this.webService = webService;
        mapper = new CursMapperTestData();
    }

    @Test
    void fetchCursesData() {
        List<CursTestDataDto> testData = CursJsonReader.readFileFromResource();
        LocalDate onDate = LocalDate.of(2021, 3, 8);
        CursDataResponse expectedResponse = new CursDataResponse();
        expectedResponse.setOnDate(onDate);
        expectedResponse.setRates(
                testData
                        .stream()
                        .map(x -> mapper.map(x, CursDto.class))
                        .collect(Collectors.toList())
        );

        CursDataRequest request = new CursDataRequest(onDate);
        CursDataResponse cursDataResponse = webService.fetchCursesData(request);

        Assert.assertEquals(cursDataResponse.getOnDate(), expectedResponse.getOnDate());
        Assert.assertEquals(cursDataResponse.getRates().size(), expectedResponse.getRates().size());
        Assert.assertEquals(expectedResponse, cursDataResponse);
    }
}


