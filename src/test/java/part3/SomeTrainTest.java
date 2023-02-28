package part3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

// "condition" field is impossible to test without reflection.

class SomeTrainTest {
    private SomeTrain train;
    private Station fromStation = new SomeStation("TEST_STATION", 2);

    private Station toStation = new SomeStation("TEST_STATION_TO", 1);

    private Passenger asleepPassenger = new SomePassenger("ASLEEP", PassengerCondition.ASLEEP,
            this.fromStation, 1, new ScuperfieldActions());

    private Passenger awakePassenger = new SomePassenger("AWAKE", PassengerCondition.REGULAR_AWAKE,
            fromStation, 1, new ScuperfieldActions());

    private Passenger shockedPassenger = new SomePassenger("SHOCKED", PassengerCondition.SHOCKED,
            fromStation, 1, new ScuperfieldActions());

    private Passenger wrongDestPassenger = new SomePassenger("WRONG", PassengerCondition.SATISFIED,
            toStation, 1, new ScuperfieldActions());

    private SomeConductor conductor = new SomeConductor("CONDUCTOR", 0);

    private TimeCounter timeCounter = new SomeTimeCounter(3);

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    @Order(1)
    void testAddingPassengers() {
        train = new SomeTrain(fromStation, conductor, timeCounter);
        train.addPassenger(asleepPassenger);
        train.addPassenger(awakePassenger);
        train.addPassenger(shockedPassenger);
        ArrayList<Passenger> expectedPassengers = new ArrayList<>();
        expectedPassengers.add(asleepPassenger);
        expectedPassengers.add(awakePassenger);
        expectedPassengers.add(shockedPassenger);
        assertEquals(expectedPassengers, train.getPassengers());
    }

    // Impossible to test
    @Test
    void testStartTrain() {
        //train.start();
    }

    @Test
    void testLastStart() {
        train = new SomeTrain(fromStation, conductor, timeCounter);
        train.addPassenger(asleepPassenger);
        train.addPassenger(awakePassenger);
        train.addPassenger(shockedPassenger);
        train.lastStart();
        String output = String.format("""
                Train is staying at %s. The train's conductor's name is %s.
                Train starts moving and goes away from %s.
                The train disappears in the distance...""",
                        train.getStation().getName(),
                        conductor.getName(),
                        train.getStation().getName());

        assertEquals(output, outputStreamCaptor.toString().trim());
    }

    @Test
    void testNoCheckStart() {
        train = new SomeTrain(fromStation, conductor, timeCounter);
        train.addPassenger(asleepPassenger);
        train.addPassenger(awakePassenger);
        train.addPassenger(shockedPassenger);
        train.noCheckStart();
        String output = String.format("""
                Train is staying at %s. The train's conductor's name is %s.
                Train starts moving and goes away from %s.""",
                train.getStation().getName(),
                conductor.getName(),
                train.getStation().getName());

        assertEquals(output, outputStreamCaptor.toString().trim());
    }

    @Test
    void testRemovePassengerCorrectDestination() {
        train = new SomeTrain(fromStation, conductor, timeCounter);
        train.addPassenger(asleepPassenger);
        train.removePassenger(asleepPassenger);

        String output = String.format("""
                       Train is staying at %s. The train's conductor's name is %s.
                       Current station is Passenger %s's destination station.
                       Passenger %s leaves the train at %s.""",
                train.getStation().getName(),
                conductor.getName(),
                asleepPassenger.getName(),
                asleepPassenger.getName(),
                train.getStation().getName());
        assertEquals(output, outputStreamCaptor.toString().trim());
        assertEquals(0, train.getPassengers().size());
    }

    @Test
    void testRemovePassengerWrongDestination() {
        train = new SomeTrain(fromStation, conductor, timeCounter);
        train.addPassenger(wrongDestPassenger);
        train.removePassenger(wrongDestPassenger);

        String output = String.format("""
                       Train is staying at %s. The train's conductor's name is %s.
                       Current station isn't Passenger %s's destination station
                       Passenger %s leaves the train at %s.
                       Passenger %s says: "Hey! That's not my station!\"""",
                train.getStation().getName(),
                conductor.getName(),
                wrongDestPassenger.getName(),
                wrongDestPassenger.getName(),
                train.getStation().getName(),
                wrongDestPassenger.getName());
        assertEquals(output, outputStreamCaptor.toString().trim());
        assertEquals(0, train.getPassengers().size());
    }

}