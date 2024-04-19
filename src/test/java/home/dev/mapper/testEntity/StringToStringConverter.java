package home.dev.mapper.testEntity;

import home.dev.mapper.entity.Converter;

public class StringToStringConverter extends Converter<String, String> {
    @Override
    public String convert(String source, String destination) {
        return source;
    }
}
