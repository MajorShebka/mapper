package home.dev.mapper.testEntity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class TestCar {

    private String name;
    private String model;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        TestCar testCar = (TestCar) object;
        return Objects.equals(name, testCar.name) && Objects.equals(model, testCar.model);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, model);
    }
}
