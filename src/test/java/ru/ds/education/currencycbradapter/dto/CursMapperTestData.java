package ru.ds.education.currencycbradapter.dto;

import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.ConfigurableMapper;

public class CursMapperTestData extends ConfigurableMapper {
    @Override
    protected void configure(MapperFactory factory) {
        factory.classMap(CursDto.class, CursTestDataDto.class)
                .byDefault()
                .register();
    }
}
