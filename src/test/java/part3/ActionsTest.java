package part3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.*;

public class ActionsTest {
    Actions scuperfieldActions = new ScuperfieldActions();

    Actions neznaykaAndKozlikActions = new NeznaykaAndKozlikActions();

    Station someStation = new SomeStation("TEST_STATION", 2);

    Passenger randomPassenger = new SomePassenger("RANDOM", PassengerCondition.REGULAR_AWAKE,
            someStation, 1, new ScuperfieldActions());

    Passenger scuperfield = new SomePassenger("Scuperfield", PassengerCondition.REGULAR_AWAKE,
            someStation, 1, new ScuperfieldActions());

    SomeGroupOfPassengers neznaykaAndKozlik = new SomeGroupOfPassengers("Neznayka and Kozlik", PassengerCondition.REGULAR_AWAKE,
            someStation, 1, new NeznaykaAndKozlikActions());


    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testRandomPassengerScuperfieldActions() {
        Exception exception = assertThrows(IllegalActionsTarget.class, () -> {
            scuperfieldActions.completeActions(randomPassenger);
        });
    }

    @Test
    void testRandomPassengerNeznaykaAndKozlikActions() {
        Exception exception = assertThrows(IllegalActionsTarget.class, () -> {
            neznaykaAndKozlikActions.completeActions(randomPassenger);
        });
    }

    @Test
    void testScuperfieldActions() throws UnableToFall {
        scuperfieldActions.completeActions(scuperfield);
        String output = """
                //Previously Passenger Scuperfield lost his Cane which costs 10000.0.
                Bottle tilts.
                Soda spills from Bottle.
                Bottle tilts.
                Soda from Bottle spills on Passenger Scuperfield.
                Passenger Scuperfield drinks sweet, great smelling, pleasantly pinching mouth Soda from Bottle.
                Passenger Scuperfield thinks: "I would have to pay 10 for this Bottle"
                Passenger Scuperfield thinks: "The sum of my loss has lowered. It's only 9990 now!"
                Passenger Scuperfield is now satisfied.
                Passenger Scuperfield thinks: "I have to take only 1000 train trips to get my money back!"
                Passenger Scuperfield is now happy.
                Bottle tilts.
                Bottle falls on Passenger Scuperfield's head.
                Bottle hits Passenger Scuperfield's forehead.
                Passenger Scuperfield is now having a""";
        String output1 = output + " small bump on the head.\n";
        String output2 = output + " big bump on the head.\n";
        Assertions.assertTrue(output1.equals(outputStreamCaptor.toString()) || output2.equals(outputStreamCaptor.toString()));
    }

    @Test
    void testNezaykaAndKozlikActions() throws UnableToFall {
        neznaykaAndKozlikActions.completeActions(neznaykaAndKozlik);
        String output = String.format("Passengers %s are watching Horror movies\n", neznaykaAndKozlik.getName());
        assertEquals(output, outputStreamCaptor.toString());
    }
}
