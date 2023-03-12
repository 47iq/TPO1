package part3v2.route;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.EnumSource;
import org.junit.jupiter.params.provider.ValueSource;
import part3.*;
import part3v2.items.Bottle;
import part3v2.items.BottleImpl;
import part3v2.items.BottleState;
import part3v2.items.Item;
import part3v2.people.Conductor;
import part3v2.people.ConductorImpl;
import part3v2.people.PassengerState;
import part3v2.people.ScuperfieldImpl;

import org.junit.jupiter.api.Assertions;


import java.util.HashMap;
import java.util.Map;

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

    private Station start;

    private Bottle bottle;

    @BeforeEach
    void setUp() {
        conductor = new ConductorImpl(true);
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
