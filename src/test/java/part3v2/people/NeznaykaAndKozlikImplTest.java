package part3v2.people;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import part3v2.items.BottleImpl;
import part3v2.items.BottleState;
import part3v2.route.Station;
import part3v2.route.StationImpl;

import static org.junit.jupiter.api.Assertions.*;

public class NeznaykaAndKozlikImplTest {

    private NeznaykaAndKozlikImpl neznaykaAndKozlik;

    private Station start = new StationImpl("Unknown station");

    private Station wrong = new StationImpl("Wrong");

    @BeforeEach
    void setUp() {
        neznaykaAndKozlik = new NeznaykaAndKozlikImpl(start);
    }

    @ParameterizedTest
    @EnumSource(value = PassengerState.class, names = {"LIGHTLY_ASLEEP", "DEEPLY_ASLEEP"})
    void testCompleteActionsInSleep(PassengerState state) {
        neznaykaAndKozlik.setPassengerState(state);
        assertThrows(IllegalStateException.class, () -> {
            neznaykaAndKozlik.completeActions();
        });
    }

    @Test
    void testCompleteActions() {
        neznaykaAndKozlik.completeActions();
        assertTrue(neznaykaAndKozlik.areActionsCompleted());
    }

    @Test
    void testDupCompleteActions() {
        assertThrows(IllegalStateException.class, () -> {
            neznaykaAndKozlik.completeActions();
            neznaykaAndKozlik.completeActions();
        });
    }

    @Test
    void testReactOnLeavingTrain() {
        neznaykaAndKozlik.reactOnLeavingTrain(start);
        assertEquals(PassengerState.SATISFIED, neznaykaAndKozlik.getPassengerState());
    }

    @Test
    void testReactOnLeavingTrainWrongStation() {
        neznaykaAndKozlik.reactOnLeavingTrain(wrong);
        assertEquals(PassengerState.ANGRY, neznaykaAndKozlik.getPassengerState());
    }

    @ParameterizedTest
    @EnumSource(value = PassengerState.class, names = {"LIGHTLY_ASLEEP", "DEEPLY_ASLEEP"})
    void testReactOnLeavingTrainInSleep(PassengerState state) {
        neznaykaAndKozlik.setPassengerState(state);
        assertThrows(IllegalStateException.class, () -> {
            neznaykaAndKozlik.reactOnLeavingTrain(start);
        });
    }
}
