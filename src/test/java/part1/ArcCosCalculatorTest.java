package part1;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class ArcCosCalculatorTest {

    double eps = 0.1;
    ArcCosCalculator arcCosCalculator;

    @BeforeEach
    public void setUp() {
        this.arcCosCalculator = new ArcCosCalculator();
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1.1, -10.0, Double.MIN_VALUE})
    public void negativeOverBoundCheck(double value) {
        Assertions.assertEquals(Math.acos(value), arcCosCalculator.calculate(value), eps);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-1, -1 - 0.00001, -1 + 0.0001})
    public void negativeBoundCheck(double value) {
        Assertions.assertEquals(Math.acos(value), arcCosCalculator.calculate(value), eps);
    }

    @ParameterizedTest
    @ValueSource(doubles = {-0.1, -0.5, -0.7})
    public void negativeValidCheck(double value) {
        Assertions.assertEquals(Math.acos(value), arcCosCalculator.calculate(value), eps);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0, 0 - 0.0001, 0 + 0.0001})
    public void zeroCheck(double value) {
        Assertions.assertEquals(Math.acos(value), arcCosCalculator.calculate(value), eps);
    }

    @ParameterizedTest
    @ValueSource(doubles = {0.1, 0.5, 0.7})
    public void positiveValidCheck(double value) {
        Assertions.assertEquals(Math.acos(value), arcCosCalculator.calculate(value), eps);
    }

    @ParameterizedTest
    @ValueSource(doubles = {1.1, 1 + 0.0001, 1 - 0.0001})
    public void positiveBoundCheck(double value) {
        Assertions.assertEquals(Math.acos(value), arcCosCalculator.calculate(value), eps);
    }

    @ParameterizedTest
    @ValueSource(doubles = {10, Double.MAX_VALUE})
    public void positiveOverBoundCheck(double value) {
        Assertions.assertEquals(Math.acos(value), arcCosCalculator.calculate(value), eps);
    }

}
