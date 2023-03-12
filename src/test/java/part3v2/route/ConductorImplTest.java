package part3v2.route;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;
import part3.*;
import part3v2.items.Bottle;
import part3v2.items.BottleImpl;
import part3v2.items.BottleState;
import part3v2.items.Item;
import part3v2.people.*;

import org.junit.jupiter.api.Assertions;
import part3v2.people.Conductor;
import part3v2.people.Passenger;


import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConductorImplTest {

    private Conductor conductor;

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

    private static Station startStation = new StationImpl("Unknown station");

    private static Station wrong = new StationImpl("Wrong");

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    private Station start;

    private Bottle bottle;

    public static Stream<Arguments> testCheckPassengersOutPrint() {
        return Stream.of(
                Arguments.of(new NeznaykaAndKozlikImpl(startStation), wrong, String.format("""
                        Conductor forgot to tell Neznayka and Kozlik to leave the train at %s. He decided to wait until %s to avoid explanations.
                        Conductor kindly asks Neznayka and Kozlik to leave the train.
                        """, startStation, wrong)),
                Arguments.of(new NeznaykaAndKozlikImpl(startStation), startStation, "Conductor kindly asks Neznayka and Kozlik to leave the train.\n"),
                Arguments.of(new NeznaykaAndKozlikImpl(startStation, PassengerState.LIGHTLY_ASLEEP), startStation, """
                        Conductor kindly asks Neznayka and Kozlik to leave the train, but Neznayka and Kozlik is asleep. Conductor takes out his tongs and starts knocking on the shelves.
                        Neznayka and Kozlik is now awake.
                        """),
                Arguments.of(new NeznaykaAndKozlikImpl(startStation, PassengerState.DEEPLY_ASLEEP), startStation, """
                        Conductor kindly asks Neznayka and Kozlik to leave the train, but Neznayka and Kozlik is asleep. Conductor takes out his tongs and starts knocking on the shelves.
                        Neznayka and Kozlik is still asleep. Conductor kicks Neznayka and Kozlik and his baggage out of the train.
                        Neznayka and Kozlik is now shocked.
                        """)
        );
    }

    @ParameterizedTest
    @MethodSource
    void testCheckPassengersOutPrint(Passenger passenger, Station station, String output) {
        System.setOut(new PrintStream(outputStreamCaptor));
        conductor.checkPassengersOut(passenger, station);
        assertEquals(output, outputStreamCaptor.toString());
    }

    @BeforeEach
    void setUp() {
        conductor = new ConductorImpl();
        bottle = new BottleImpl(BottleState.DEFAULT, 1000);
        start = new StationImpl("Unknown station");
    }

    @ParameterizedTest
    @EnumSource(value = PassengerState.class, names = {"AWAKE", "SHOCKED", "SATISFIED", "LIGHTLY_ASLEEP", "DEEPLY_ASLEEP"})
    public void testCheckOutPassengers(PassengerState state) {
        ScuperfieldImpl passenger = new ScuperfieldImpl(bottle, lostCane, start, bottle);
        passenger.setPassengerState(state);
        conductor.checkPassengersOut(passenger, start);

        switch (state) {
            case AWAKE, SHOCKED, SATISFIED -> {
                Assertions.assertEquals(state, passenger.getPassengerState());
            }
            case LIGHTLY_ASLEEP -> {
                Assertions.assertEquals(PassengerState.AWAKE, passenger.getPassengerState());
            }
            case DEEPLY_ASLEEP -> {
                Assertions.assertEquals(PassengerState.SHOCKED, passenger.getPassengerState());
            }
        }
    }

}
