package part3v2.items;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import part3v2.people.PassengerState;
import part3v2.people.ScuperfieldImpl;
import part3v2.route.Station;
import part3v2.route.StationImpl;

import static org.junit.jupiter.api.Assertions.*;

class BottleImplTest {

    @ParameterizedTest
    @EnumSource(value = BottleState.class, names = {"DEFAULT", "BENT_DOWN", "FELL_DOWN"})
    void testBendDown(BottleState state) {
        Bottle bottle = new BottleImpl(state, 1000);
        ScuperfieldImpl scuperfield = new ScuperfieldImpl(null, null, null, bottle);
        switch (state) {
            case DEFAULT -> {
                bottle.bendDown(scuperfield);
                assertEquals(BottleState.BENT_DOWN, bottle.getBottleState());
                assertEquals(PassengerState.SATISFIED, scuperfield.getPassengerState());
            }
            case BENT_DOWN -> {
                bottle.bendDown(scuperfield);
                assertEquals(BottleState.FELL_DOWN, bottle.getBottleState());
                assertEquals(PassengerState.SHOCKED, scuperfield.getPassengerState());
            }
            case FELL_DOWN -> {
                assertThrows(IllegalStateException.class, () -> {
                    bottle.bendDown(scuperfield);
                });
            }
        }
    }
}