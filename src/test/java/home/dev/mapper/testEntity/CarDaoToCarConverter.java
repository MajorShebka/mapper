package home.dev.mapper.testEntity;

import home.dev.mapper.entity.Converter;

public class CarDaoToCarConverter extends Converter<TestCarDAO, TestCar> {

    @Override
    public TestCar convert(TestCarDAO source, TestCar destination) {
        destination.setName(mapper.map(source.getName(), String.class));
        destination.setModel(source.getModel());
        return destination;
    }
}
