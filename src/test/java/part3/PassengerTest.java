package part3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PassengerTest {


    Station rightStation = new SomeStation("TEST_STATION", 2);

    Map<String, Passenger> passengerMap = new HashMap<String, Passenger>() {{
        put("ASLEEP", new SomePassenger("ASLEEP",PassengerCondition.ASLEEP,
                rightStation, 1, new ScuperfieldActions()));
        put("AWAKE", new SomePassenger("AWAKE", PassengerCondition.REGULAR_AWAKE,
                rightStation, 1, new ScuperfieldActions()));
        put("NULL_STATION",new SomePassenger("NULL", PassengerCondition.REGULAR_AWAKE,
                null, 1, new ScuperfieldActions()));
        put("SHOCKED", new SomePassenger("SHOCKED", PassengerCondition.SHOCKED,
                rightStation, 1, new ScuperfieldActions()));
        put("GROUP", new SomeGroupOfPassengers("GROUP", PassengerCondition.REGULAR_AWAKE,
                rightStation, 1, new ScuperfieldActions()));
    }};

    TimeCounter timeCounter = new SomeTimeCounter(3);
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @ParameterizedTest
    @CsvSource({
            "ASLEEP, Passenger %s is sleeping. Passenger %s says: \"Zzz..\"",
            "GROUP, Passengers %s are sleeping. Passengers %s say: \"Zzz..\"",
    })
    void testPassengerSleep(String pass, String out) {
        Passenger passenger = passengerMap.get(pass);
        passenger.sleep();
        String output = String.format(out,
                passenger.getName(), passenger.getName());
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }


    @ParameterizedTest
    @CsvSource({
            "AWAKE, %s falls asleep because the time is %s\\n%s is now asleep.",
            "GROUP, %s fall asleep because the time is %s\\n%s are now asleep.",
            "ASLEEP,''",
    })
    void testPassengerFallAsleep(String pass, String out) {
        Passenger passenger = passengerMap.get(pass);
        passenger.fallAsleepTime(this.timeCounter);
        String output = String.format(out,
                passenger.toString(), this.timeCounter.toString(), passenger.toString());
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim().replace("\n", "\\n"));
    }

    @Test
    void testSleepingPassengerFallAsleepNull() {
        Passenger passenger = passengerMap.get("ASLEEP");
        Exception exception = assertThrows(Exception.class, () -> {
            passenger.fallAsleepTime(null);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "SHOCKED, %s leaves the train at %s.\\n%s wants to say something but is too shocked to open his mouth.",
            "AWAKE, %s leaves the train at %s.\\n%s says: \"Hey! That's not my station!\"",
            "GROUP, %s leave the train at %s.\\n%s say: \"Hey! That's not our station!\"",
            "NULL_STATION, %s leaves the train at %s.\\n%s says: \"Hey! That's not my station!\""
    })
    void testShockedLeavingTrain(String pass, String out) {
        Passenger passenger = passengerMap.get(pass);
        Station outStation = new SomeStation("TEST2", 2);
        passenger.commentOnLeavingTrain(outStation);
        String output = String.format(out,
                passenger.toString(), outStation.toString(),passenger.toString());
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim().replace("\n", "\\n"));
    }

    @ParameterizedTest
    @CsvSource({
            "ASLEEP, ''",
            "AWAKE, %s leaves the train at %s.",
    })
    void testLeavingTrainRightStation(String pass, String out) {
        Passenger passenger = passengerMap.get(pass);
        passenger.commentOnLeavingTrain(rightStation);
        String output = String.format(out,
                passenger.toString(), this.rightStation.toString());
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testNullLeavingTrain() {
        Passenger passenger = passengerMap.get("ASLEEP");
        Exception exception = assertThrows(Exception.class, () -> {
            passenger.commentOnLeavingTrain(null);
        });
    }
}
