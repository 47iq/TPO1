package part3v2.people;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.MethodSource;
import part3v2.items.Bottle;
import part3v2.items.BottleImpl;
import part3v2.items.BottleState;
import part3v2.items.Item;
import part3v2.route.Station;
import part3v2.route.StationImpl;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class ScuperfieldImplTest {

    private ScuperfieldImpl scuperfield;

    private Bottle bottle;

    private Item lostCane = new Item() {
        @Override
        public double getCost() {
            return 10000;
        }

        @Override
        public String toString() {
            return "Cane";
        }
    };

    private Station start = new StationImpl("Unknown station");

    private Station wrong = new StationImpl("Wrong");

    @BeforeEach
    void setUp() {
        bottle = new BottleImpl(BottleState.DEFAULT, 1000);
        scuperfield = new ScuperfieldImpl(bottle, lostCane, start, bottle);
    }

    @Test
    void testCompleteActions() {
        scuperfield.completeActions();
        assertEquals(PassengerState.SHOCKED, scuperfield.getPassengerState());
        assertEquals(BottleState.FELL_DOWN, bottle.getBottleState());
    }

    @Test
    void testDupCompleteActions() {
        assertThrows(IllegalStateException.class, () -> {
            scuperfield.completeActions();
            scuperfield.completeActions();
        });
    }

    @ParameterizedTest
    @EnumSource(value = PassengerState.class, names = {"LIGHTLY_ASLEEP", "DEEPLY_ASLEEP"})
    void testCompleteActionsInSleep(PassengerState state) {
        scuperfield.setPassengerState(state);
        assertThrows(IllegalStateException.class, () -> {
            scuperfield.completeActions();
        });
    }

    @Test
    void testReactOnLeavingTrain() {
        scuperfield.reactOnLeavingTrain(start);
        assertEquals(PassengerState.SATISFIED, scuperfield.getPassengerState());
    }

    @Test
    void testReactOnLeavingTrainWrongStation() {
        scuperfield.reactOnLeavingTrain(wrong);
        assertEquals(PassengerState.ANGRY, scuperfield.getPassengerState());
    }

    @ParameterizedTest
    @EnumSource(value = PassengerState.class, names = {"LIGHTLY_ASLEEP", "DEEPLY_ASLEEP"})
    void testReactOnLeavingTrainInSleep(PassengerState state) {
        scuperfield.setPassengerState(state);
        assertThrows(IllegalStateException.class, () -> {
            scuperfield.reactOnLeavingTrain(start);
        });
    }

    public static Stream<Arguments> testCalculateLossError() {
        return Stream.of(
                Arguments.of(0, -1),
                Arguments.of(1, -1),
                Arguments.of(-10, 0),
                Arguments.of(1000, 0),
                Arguments.of(0, 1000),
                Arguments.of(0, 0)
                );
    }

    public static Stream<Arguments> testCalculateLoss() {
        return Stream.of(
                Arguments.of(1000, 10),
                Arguments.of(1, 1),
                Arguments.of(Double.MAX_VALUE, 1/Double.MAX_VALUE),
                Arguments.of(10000, 10),
                Arguments.of(1000, 1),
                Arguments.of( 1/Double.MAX_VALUE, Double.MAX_VALUE)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testCalculateLossError(double cost1, double cost2) {
        ScuperfieldImpl scuperfield1 = new ScuperfieldImpl((Item) () -> cost2, (Item) () -> cost1, start, bottle);
        assertThrows(IllegalArgumentException.class, scuperfield1::calculateLoss);
    }

    @ParameterizedTest
    @EnumSource(value = PassengerState.class, names = {"LIGHTLY_ASLEEP", "DEEPLY_ASLEEP"})
    void testFallAsleep(PassengerState state) {
        scuperfield.fallAsleep(state.equals(PassengerState.DEEPLY_ASLEEP));
        assertEquals(scuperfield.getPassengerState(), state);
    }

    @ParameterizedTest
    @MethodSource
    void testCalculateLoss(double cost1, double cost2) {
        ScuperfieldImpl scuperfield1 = new ScuperfieldImpl((Item) () -> cost2, (Item) () -> cost1, start, bottle);
        double loss = scuperfield1.calculateLoss();
        assertEquals(loss, cost1 / cost2);
    }
}