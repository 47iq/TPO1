package part3;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SomeRouteTest {

    private final Conductor basicConductor = new SomeConductor("BASIC", 0);

    private final Station rightStation = new SomeStation("TEST", 2);

    private final Station leftStation = new SomeStation("TESTT", 2);

    private final Passenger scuperfield = new SomePassenger("Scuperfield", PassengerCondition.REGULAR_AWAKE,
            rightStation, 5, new ScuperfieldActions());

    SomeGroupOfPassengers neznaykaAndKozlik = new SomeGroupOfPassengers("Neznayka and Kozlik", PassengerCondition.REGULAR_AWAKE,
            rightStation, 5, new NeznaykaAndKozlikActions());

    private final TimeCounter timeCounter = new SomeTimeCounter(3);

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setup() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testRunRouteNoPassengersOneStation() throws UnableToFall {
        Train train = new SomeTrain(rightStation, basicConductor, timeCounter);
        List<Station> stationList = new ArrayList<>();
        stationList.add(rightStation);
        Route route = new SomeRoute(train, stationList);
        route.runFullRoute();
        String output = String.format("""
                Train is staying at %s. The train's conductor's name is %s.
                Train starts moving and goes away from %s.
                The train disappears in the distance...""",
                train.getStation().getName(),
                basicConductor.getName(),
                train.getStation().getName());
        assertEquals(output, outputStreamCaptor.toString().trim());
    }

    @Test
    void testRunRouteNoPassengersMultipleStations() throws UnableToFall {
        Train train = new SomeTrain(rightStation, basicConductor, timeCounter);
        List<Station> stationList = new ArrayList<>();
        stationList.add(rightStation);
        stationList.add(leftStation);
        Route route = new SomeRoute(train, stationList);
        route.runFullRoute();
        String output = String.format("""
                Train is staying at %s. The train's conductor's name is %s.
                Train starts moving and goes away from %s.
                The train disappears in the distance...
                Train starts moving and goes away from %s.
                The train disappears in the distance...""",
                train.getStation().getName(),
                basicConductor.getName(),
                train.getStation().getName(),
                train.getStation().getName());
        assertEquals(output, outputStreamCaptor.toString().trim());
    }

    @Test
    void testRunRouteScuperfieldMultipleStations() throws UnableToFall {
        Train train = new SomeTrain(rightStation, basicConductor, timeCounter);
        train.addPassenger(scuperfield);
        List<Station> stationList = new ArrayList<>();
        stationList.add(rightStation);
        stationList.add(leftStation);
        Route route = new SomeRoute(train, stationList);
        route.runFullRoute();
        String output = """
                        Train is staying at TEST. The train's conductor's name is BASIC.
                        Train starts moving and goes away from TEST.
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
                        Passenger Scuperfield is now having a %s bump on the head.
                        Passenger Scuperfield falls asleep because the time is 3 am.
                        Passenger Scuperfield is now asleep.
                        Passenger Scuperfield is sleeping. Passenger Scuperfield says: "Zzz.."
                        Train stops at TEST at 5 am.
                        BASIC says: "Passenger Scuperfield, you have to leave now!"
                        Passenger Scuperfield didn't hear BASIC cause Passenger Scuperfield is sleeping.
                        BASIC shakes Passenger Scuperfield.
                        Passenger Scuperfield is still sleeping. BASIC takes out tongs and starts knocking on his shelf.
                        Passenger Scuperfield is now awake.
                        Current station is Passenger Scuperfield's destination station.
                        Passenger Scuperfield leaves the train at TEST.
                        Train starts moving and goes away from TEST.
                        The train disappears in the distance...
                        Train starts moving and goes away from TESTT.
                        The train disappears in the distance...""";
        assertTrue(outputStreamCaptor.toString().trim().equals(output.formatted("small")) ||
                outputStreamCaptor.toString().trim().equals(output.formatted("big")));
    }

    @Test
    void testRunRouteNezAndKozlMultipleStations() throws UnableToFall {
        Train train = new SomeTrain(rightStation, basicConductor, timeCounter);
        train.addPassenger(neznaykaAndKozlik);
        List<Station> stationList = new ArrayList<>();
        stationList.add(rightStation);
        stationList.add(leftStation);
        Route route = new SomeRoute(train, stationList);
        route.runFullRoute();
        String output = """
                Train is staying at TEST. The train's conductor's name is BASIC.
                Train starts moving and goes away from TEST.
                Passengers Neznayka and Kozlik are watching Horror movies
                Passengers Neznayka and Kozlik fall asleep because the time is 3 am.
                Passengers Neznayka and Kozlik are now asleep.
                Passengers Neznayka and Kozlik are sleeping. Passengers Neznayka and Kozlik say: "Zzz.."
                Train stops at TEST at 5 am.
                BASIC says: "Passengers Neznayka and Kozlik, you have to leave now!"
                Passengers Neznayka and Kozlik didn't hear BASIC cause Passengers Neznayka and Kozlik are sleeping.
                BASIC shakes Passengers Neznayka and Kozlik.
                Passengers Neznayka and Kozlik are still sleeping. BASIC takes out tongs and starts knocking on their shelves.
                Passengers Neznayka and Kozlik are now awake.
                Current station is Passengers Neznayka and Kozlik's destination station.
                Passengers Neznayka and Kozlik leave the train at TEST.
                Train starts moving and goes away from TEST.
                The train disappears in the distance...
                Train starts moving and goes away from TESTT.
                The train disappears in the distance...""";
        assertEquals(output, outputStreamCaptor.toString().trim());
    }


}