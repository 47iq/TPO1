package part3v2.route;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import part3v2.people.ConductorImpl;
import part3v2.people.NeznaykaAndKozlikImpl;
import part3v2.people.Passenger;
import part3v2.people.PassengerState;

import javax.management.InstanceAlreadyExistsException;
import java.util.HashSet;
import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class TrainImplTest {

    Train train;

    Station right = new StationImpl("start");

    @BeforeEach
    void setUp() {
        train = new TrainImpl(new StationImpl("start"), new HashSet<>(), new ConductorImpl());
    }

    @Test
    void testDupPassengers() {
        assertThrows(InstanceAlreadyExistsException.class, () -> {
            train.addPassenger(new NeznaykaAndKozlikImpl(right));
            train.addPassenger(new NeznaykaAndKozlikImpl(right));
        });
    }

    @Test
    void testStopWhileStaying() {
        assertThrows(IllegalStateException.class, () -> {
            train.arriveToStation(right);
            train.arriveToStation(right);
        });
    }

    @Test
    void testStart() throws InstanceAlreadyExistsException {
        train.addPassenger(new NeznaykaAndKozlikImpl(right));
        train.startFromStation();
        assertEquals(train.getCondition(), TrainCondition.GOING);
        assertEquals(train.getCurrentStation(), right);
    }

    @Test
    void testDisappear() throws InstanceAlreadyExistsException {
        train.disappear();
        assertEquals(train.getCondition(), TrainCondition.GOING);
    }

    @ParameterizedTest
    @ValueSource(strings = {"TEST", "TEST2"})
    void testStop(String name) throws InstanceAlreadyExistsException {
        train.addPassenger(new NeznaykaAndKozlikImpl(right));
        train.startFromStation();
        Station station = new StationImpl(name);
        train.arriveToStation(station);
        assertEquals(train.getCondition(), TrainCondition.STAYING);
        assertEquals(train.getCurrentStation(), station);
    }

    @ParameterizedTest
    @ValueSource(strings = {"LIGHTLY_ASLEEP", "DEEPLY_ASLEEP", "AWAKE", "SHOCKED"})
    void testAddRemovePassengers(String name) throws InstanceAlreadyExistsException {
        Passenger passenger = new NeznaykaAndKozlikImpl(right);
        passenger.setPassengerState(PassengerState.valueOf(name));
        train.addPassenger(passenger);
        train.removePassenger(passenger);
        assertTrue(train.getPassengers().isEmpty());
    }

    @Test
    void testStartWhileGoing() throws InstanceAlreadyExistsException {
        train.addPassenger(new NeznaykaAndKozlikImpl(right));
        train.startFromStation();
        assertThrows(IllegalStateException.class, () -> {
            train.startFromStation();
        });
    }

    @Test
    void testAddPassengersWhileGoing() {
        assertThrows(IllegalStateException.class, () -> {
            train.startFromStation();
            train.addPassenger(new NeznaykaAndKozlikImpl(right));
        });
    }

    @Test
    void testRemovePassengersWhileGoing() throws InstanceAlreadyExistsException {
        train.addPassenger(new NeznaykaAndKozlikImpl(right));
        assertThrows(IllegalStateException.class, () -> {
            train.startFromStation();
            train.removePassenger(new NeznaykaAndKozlikImpl(right));
        });
    }

    @Test
    void testStartWithoutPassengers() {
        assertThrows(IllegalStateException.class, () -> {
            train.startFromStation();
        });
    }

    @Test
    void testNoSuchPassenger() {
        assertThrows(NoSuchElementException.class, () -> {
            train.removePassenger(new NeznaykaAndKozlikImpl(right));
        });
    }

}