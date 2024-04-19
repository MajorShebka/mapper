package home.dev.mapper.entity;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Converter<S, D> {

    protected DefaultMapper mapper;

    public D convert(S source, D destination) {
        Map<String, Field> destinationFields = createMap(destination.getClass().getDeclaredFields());
        Arrays.stream(source.getClass().getDeclaredFields()).forEach(field -> addValue(field, destinationFields.get(field.getName()), destination, source));
        return destination;
    }

    private Map<String, Field> createMap(Field[] fields) {
        Map<String, Field> fieldMap = new HashMap<>();
        Arrays.stream(fields).forEach(field -> fieldMap.put(field.getName(), field));
        return fieldMap;
    }

    private <D, S> void addValue(Field sourceField, Field destinationField, D destination, S source) {
        if (destinationField == null) {
            return;
        }

        if (sourceField.getType().equals(destinationField.getType())) {
            destinationField.setAccessible(true);
            sourceField.setAccessible(true);
            try {
                destinationField.set(destination, sourceField.get(source));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        } else {
            destinationField.setAccessible(true);
            sourceField.setAccessible(true);
            try {
                destinationField.set(destination, mapper.map(sourceField.get(source), destinationField.getType()));
            } catch (IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
