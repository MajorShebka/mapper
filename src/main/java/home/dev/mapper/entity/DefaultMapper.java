package home.dev.mapper.entity;

import java.lang.reflect.Field;
import java.util.*;
import java.lang.reflect.ParameterizedType;

public class DefaultMapper {

    private Map<String, Converter> converters = new HashMap<>();

    public void setConverters(Set<Converter> converters) {
        converters.forEach(this::initConverter);
    }

    private void initConverter(Converter converter) {
        try {
            Field field = converter.getClass().getSuperclass().getDeclaredField("mapper");
            field.setAccessible(true);
            field.set(converter, this);
        } catch (Exception e) {
            System.out.println("Something wrong, message: " + e.getMessage());
        }

        String source = ((ParameterizedType) converter.getClass().getGenericSuperclass()).getActualTypeArguments()[0].getTypeName();
        String destination = ((ParameterizedType) converter.getClass().getGenericSuperclass()).getActualTypeArguments()[1].getTypeName();
        converters.putIfAbsent(createKey(source, destination), converter);
    }

    private String createKey(String source, String destination) {
        return source + destination;
    }

    public <S, D> Collection<D> map(Collection<S> sources, Collection collection, Class<D> destination) {
        sources.forEach(s -> collection.add(map(s, destination)));
        return collection;
    }

    public <S,D> D map(S source, Class<D> destination) {
        if (source == null) {
            return null;
        }
        Converter converter = converters.get(createKey(source.getClass().getTypeName(), destination.getTypeName()));
        try {
            return (D) converter.convert(source, destination.newInstance());
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
