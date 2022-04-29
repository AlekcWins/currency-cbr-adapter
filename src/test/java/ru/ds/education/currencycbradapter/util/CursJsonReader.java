package ru.ds.education.currencycbradapter.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import ru.ds.education.currencycbradapter.dto.CursTestDataDto;

import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;

public class CursJsonReader {

    public static List<CursTestDataDto> readFileFromResource() {
        ObjectMapper objectMapper = new ObjectMapper();
        Resource resource = new ClassPathResource("testdata/wsdl/CBR_Curs_08_03_2021.json", Thread.currentThread().getContextClassLoader());
        try {
            String fileData = new String(
                    Files.readAllBytes(resource.getFile().toPath()));
            return Arrays.asList(objectMapper.readValue(fileData, CursTestDataDto[].class));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
