package home.dev.mapper.tests;

import home.dev.mapper.entity.Converter;
import home.dev.mapper.entity.DefaultMapper;
import home.dev.mapper.testEntity.*;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RequiredArgsConstructor
public class DefaultMapperTest {

    private DefaultMapper mapper;

    @Test
    public void testCarDaoToCarConverter() {
        initMapper();
        TestCarDAO carDAO = new TestCarDAO();
        carDAO.setModel("model");
        carDAO.setName("name");
        TestCar car = mapper.map(carDAO, TestCar.class);
        Assertions.assertEquals("model", car.getModel());
        Assertions.assertEquals("name", car.getName());
    }

    @Test
    public void testCarDaoToCarConverterList() {
        initMapper();
        TestCarDAO carDAO = new TestCarDAO();
        carDAO.setModel("model");
        carDAO.setName("name");
        TestCarDAO carDAO2 = new TestCarDAO();
        carDAO2.setModel("model2");
        carDAO2.setName("name2");
        List<TestCar> cars = (List<TestCar>) mapper.map(new ArrayList<>(List.of(carDAO, carDAO2)), new ArrayList(), TestCar.class);
        Assertions.assertEquals("model", cars.get(0).getModel());
        Assertions.assertEquals("name", cars.get(0).getName());
        Assertions.assertEquals("model2", cars.get(1).getModel());
        Assertions.assertEquals("name2", cars.get(1).getName());
    }

    @Test
    public void testCarDaoToCarConverterSet() {
        initMapper();
        TestCarDAO carDAO = new TestCarDAO();
        carDAO.setModel("model");
        carDAO.setName("name");
        TestCarDAO carDAO2 = new TestCarDAO();
        carDAO2.setModel("model2");
        carDAO2.setName("name2");
        Set<TestCar> cars = (Set<TestCar>) mapper.map(Set.of(carDAO, carDAO2), new HashSet<>(), TestCar.class);
        TestCar car = new TestCar();
        car.setModel("model");
        car.setName("name");
        TestCar car2 = new TestCar();
        car2.setModel("model2");
        car2.setName("name2");
        Set<TestCar> testCars = new HashSet<>();
        testCars.add(car);
        testCars.add(car2);
        Assertions.assertTrue(cars.containsAll(testCars));
    }

    @Test
    public void testConverter() {
        Converter<TestCar, TestCarDAO> test = new Converter<>();
        TestCar testCar = new TestCar("name", "model");
        TestCarDAO testCarDao = new TestCarDAO();
        test.convert(testCar, testCarDao);
        Assertions.assertEquals("name", testCarDao.getName());
        Assertions.assertEquals("model", testCarDao.getModel());
        initMapper();
        TestCarDAO carDAO = new TestCarDAO();
        carDAO.setModel("model");
        carDAO.setName("name");
        MapEntity mapEntity = new MapEntity();
        mapEntity.setTestCar(carDAO);
        MapEntity2 e = mapper.map(mapEntity, MapEntity2.class);
        Assertions.assertEquals("model", e.getTestCar().getModel());
        Assertions.assertEquals("name", e.getTestCar().getName());
    }

    private void initMapper() {
        mapper = new DefaultMapper();
        mapper.setConverters(Set.of(new CarDaoToCarConverter(), new StringToStringConverter(), new MapperConverter()));
    }
}
