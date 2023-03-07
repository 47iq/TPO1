package part3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConductorTest {

    private final Station rightStation = new SomeStation("TEST", 2);
    private final  Conductor neverForgettingConductor = new SomeConductor("NEVER_FORGETFUL", 0);

    private final TimeCounter timeCounter = new SomeTimeCounter(3);

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    Map<String, Passenger> passengerMap = new HashMap<String, Passenger>() {{
        put("ASLEEP", new SomePassenger("ASLEEP",PassengerCondition.ASLEEP,
                rightStation, 1, new ScuperfieldActions()));
        put("LIGHT_ASLEEP", new SomePassenger("LIGHT_ASLEEP",PassengerCondition.ASLEEP,
                rightStation, 1, new ScuperfieldActions()));
        put("MEDIUM_ASLEEP", new SomePassenger("MEDIUM_ASLEEP",PassengerCondition.ASLEEP,
                rightStation, 5, new ScuperfieldActions()));
        put("DEEPLY_ASLEEP", new SomePassenger("DEEPLY_ASLEEP",PassengerCondition.ASLEEP,
                rightStation, 10, new ScuperfieldActions()));
        put("AWAKE", new SomePassenger("AWAKE", PassengerCondition.REGULAR_AWAKE,
                rightStation, 1, new ScuperfieldActions()));
        put("NULL_STATION",new SomePassenger("NULL", PassengerCondition.REGULAR_AWAKE,
                null, 1, new ScuperfieldActions()));
        put("SHOCKED", new SomePassenger("SHOCKED", PassengerCondition.SHOCKED,
                rightStation, 1, new ScuperfieldActions()));
        put("GROUP", new SomeGroupOfPassengers("GROUP", PassengerCondition.REGULAR_AWAKE,
                rightStation, 1, new ScuperfieldActions()));
    }};

    Map<String, Conductor> conductorMap = new HashMap<String, Conductor>() {{
        put("BASIC", new SomeConductor("BASIC", 100));
        put("NEVER_FORGETFUL", new SomeConductor("NEVER_FORGETFUL", 0));
    }};


    @ParameterizedTest
    @CsvSource({
            "BASIC,AWAKE,BASIC checks if he forgot to tell anybody to leave the train.\\nOh no! BASIC forgot to tell Passenger AWAKE to leave.\\nBASIC decides to wait till the next station and not to say Passenger AWAKE that he skipped his station{{ because BASIC wants to avoid explanations.\\nBASIC tries to get more concentrated. He won't forget about any passenger from now on.",
            "BASIC,'',BASIC checks if he forgot to tell anybody to leave the train.\\nBASIC didn't forget anyone.",
            "NEVER_FORGETFUL,AWAKE,NEVER_FORGETFUL checks if he forgot to tell anybody to leave the train.\\nNEVER_FORGETFUL didn't forget anyone."
    })
    void testDoubleCheck(String conductorName, String passengerName, String out) {
        Conductor conductor = conductorMap.get(conductorName);
        Passenger passenger = passengerMap.get(passengerName);
        Train train = new SomeTrain(new SomeStation("TEST2", 2), conductor, timeCounter);
        if(passenger != null)
            train.addPassenger(passenger);
        train.stopAt(rightStation);
        System.setOut(new PrintStream(outputStreamCaptor));
        conductor.doubleCheck(train);
        String expected = outputStreamCaptor.toString().trim().replace("\n", "\\n");
        expected = expected.replace(",", "{{");
        Assertions.assertEquals(out, expected);
    }

    @Test
    void testDoubleCheckNull() {
        Exception exception = assertThrows(Exception.class, () -> {
            neverForgettingConductor.doubleCheck(null);
        });
    }

    @ParameterizedTest
    @CsvSource({
            "NEVER_FORGETFUL,AWAKE,NEVER_FORGETFUL says: \"Passenger AWAKE{{ you have to leave now!\"\\nCurrent station is Passenger AWAKE's destination station.\\nPassenger AWAKE leaves the train at TEST.",
            "NEVER_FORGETFUL,LIGHT_ASLEEP,NEVER_FORGETFUL says: \"Passenger LIGHT_ASLEEP{{ you have to leave now!\"\\nPassenger LIGHT_ASLEEP didn't hear NEVER_FORGETFUL cause Passenger LIGHT_ASLEEP is sleeping.\\nNEVER_FORGETFUL shakes Passenger LIGHT_ASLEEP.\\nNEVER_FORGETFUL wakes Passenger LIGHT_ASLEEP up.\\nPassenger LIGHT_ASLEEP is now awake.\\nCurrent station is Passenger LIGHT_ASLEEP's destination station.\\nPassenger LIGHT_ASLEEP leaves the train at TEST.",
            "NEVER_FORGETFUL,MEDIUM_ASLEEP,NEVER_FORGETFUL says: \"Passenger MEDIUM_ASLEEP{{ you have to leave now!\"\\nPassenger MEDIUM_ASLEEP didn't hear NEVER_FORGETFUL cause Passenger MEDIUM_ASLEEP is sleeping.\\nNEVER_FORGETFUL shakes Passenger MEDIUM_ASLEEP.\\nPassenger MEDIUM_ASLEEP is still sleeping. NEVER_FORGETFUL takes out tongs and starts knocking on his shelf.\\nPassenger MEDIUM_ASLEEP is now awake.\\nCurrent station is Passenger MEDIUM_ASLEEP's destination station.\\nPassenger MEDIUM_ASLEEP leaves the train at TEST.",
            "NEVER_FORGETFUL,DEEPLY_ASLEEP,NEVER_FORGETFUL says: \"Passenger DEEPLY_ASLEEP{{ you have to leave now!\"\\nPassenger DEEPLY_ASLEEP didn't hear NEVER_FORGETFUL cause Passenger DEEPLY_ASLEEP is sleeping.\\nNEVER_FORGETFUL shakes Passenger DEEPLY_ASLEEP.\\nPassenger DEEPLY_ASLEEP is still sleeping. NEVER_FORGETFUL realizes that he won't wake up and gets angry. NEVER_FORGETFUL shouts into Passenger DEEPLY_ASLEEP's ear.\\nPassenger DEEPLY_ASLEEP is now awake.\\nNEVER_FORGETFUL kicks Passenger DEEPLY_ASLEEP out of the train.\\nPassenger DEEPLY_ASLEEP is now shocked.\\nCurrent station is Passenger DEEPLY_ASLEEP's destination station.\\nPassenger DEEPLY_ASLEEP leaves the train at TEST.",
            "BASIC,AWAKE,''"
    })
    void testCheckPassengersOut(String conductorName, String passengerName, String out) {
        Conductor conductor = conductorMap.get(conductorName);
        Passenger passenger = passengerMap.get(passengerName);
        Train train = new SomeTrain(new SomeStation("TEST2", 2), conductor, timeCounter);
        train.stopAt(rightStation);
        if(passenger != null)
            train.addPassenger(passenger);
        System.setOut(new PrintStream(outputStreamCaptor));
        conductor.checkPassengersOut(train);
        String expected = outputStreamCaptor.toString().trim().replace("\n", "\\n");
        expected = expected.replace(",", "{{");
        Assertions.assertEquals(out, expected);
    }
}
