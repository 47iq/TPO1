package part3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ConductorTest {

    private final Conductor basicConductor = new SomeConductor("BASIC", 100);

    private final Station rightStation = new SomeStation("TEST", 2);

    private final Passenger awakePassenger = new SomePassenger("AWAKE", PassengerCondition.REGULAR_AWAKE,
            rightStation, 1, new ScuperfieldActions());

    private final Passenger lightAsleepPassenger = new SomePassenger("ASLEEP", PassengerCondition.ASLEEP,
            this.rightStation, 1, new ScuperfieldActions());

    private final Passenger mediumAsleepPassenger = new SomePassenger("ASLEEP", PassengerCondition.ASLEEP,
            this.rightStation, 5, new ScuperfieldActions());

    private final Passenger deepAsleepPassenger = new SomePassenger("ASLEEP", PassengerCondition.ASLEEP,
            this.rightStation, 10, new ScuperfieldActions());

    private final  Conductor neverForgettingConductor = new SomeConductor("NEVER_FORGETFUL", 0);

    private final TimeCounter timeCounter = new SomeTimeCounter(3);

    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();


    @Test
    void testDoubleCheckBasic() {
        Train train = new SomeTrain(new SomeStation("TEST1", 2), basicConductor, timeCounter);
        train.addPassenger(awakePassenger);
        train.stopAt(rightStation);
        System.setOut(new PrintStream(outputStreamCaptor));
        basicConductor.doubleCheck(train);
        String output = String.format("""
                        %s checks if he forgot to tell anybody to leave the train.
                        Oh no! %s forgot to tell %s to leave.
                        %s decides to wait till the next station and not to say %s that he skipped his station, because %s wants to avoid explanations.
                        %s tries to get more concentrated. He won't forget about any passenger from now on.""",
                basicConductor, basicConductor, awakePassenger, basicConductor, awakePassenger,basicConductor,basicConductor);
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testDoubleCheckNoPassengers() {
        Train train = new SomeTrain(new SomeStation("TEST1", 2), basicConductor, timeCounter);
        train.stopAt(rightStation);
        System.setOut(new PrintStream(outputStreamCaptor));
        basicConductor.doubleCheck(train);
        String output = String.format("""
                        %s checks if he forgot to tell anybody to leave the train.
                        %s didn't forget anyone.""",
                basicConductor, basicConductor);
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testDoubleCheckNeverForgetting() {
        Train train = new SomeTrain(new SomeStation("TEST1", 2), neverForgettingConductor, timeCounter);
        train.addPassenger(awakePassenger);
        train.stopAt(rightStation);
        System.setOut(new PrintStream(outputStreamCaptor));
        neverForgettingConductor.doubleCheck(train);
        String output = String.format("""
                        %s checks if he forgot to tell anybody to leave the train.
                        %s didn't forget anyone.""",
                neverForgettingConductor, neverForgettingConductor);
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testDoubleCheckNull() {
        Exception exception = assertThrows(Exception.class, () -> {
            neverForgettingConductor.doubleCheck(null);
        });
    }

    @Test
    void testCheckPassengersOutNeverForgetting() {
        Train train = new SomeTrain(new SomeStation("TEST1", 2), neverForgettingConductor, timeCounter);
        train.stopAt(rightStation);
        train.addPassenger(awakePassenger);
        System.setOut(new PrintStream(outputStreamCaptor));
        neverForgettingConductor.checkPassengersOut(train);
        String output = String.format("""
                       %s says: "%s, you have to leave now!"
                       Current station is %s's destination station.
                       %s leaves the train at %s.""",
                neverForgettingConductor, awakePassenger, awakePassenger, awakePassenger, rightStation);
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testCheckLightlyAsleepPassengersOutNeverForgetting() {
        Train train = new SomeTrain(new SomeStation("TEST1", 2), neverForgettingConductor, timeCounter);
        train.stopAt(rightStation);
        train.addPassenger(lightAsleepPassenger);
        System.setOut(new PrintStream(outputStreamCaptor));
        neverForgettingConductor.checkPassengersOut(train);
        String output = String.format("""
                        %s says: "%s, you have to leave now!"
                        %s didn't hear %s cause %s is sleeping.
                        %s shakes %s.
                        %s wakes %s up.
                        %s is now awake.
                        Current station is %s's destination station.
                        %s leaves the train at %s.""",
                neverForgettingConductor, lightAsleepPassenger, lightAsleepPassenger, neverForgettingConductor, lightAsleepPassenger, neverForgettingConductor,
                lightAsleepPassenger, neverForgettingConductor,
                lightAsleepPassenger, lightAsleepPassenger, lightAsleepPassenger, lightAsleepPassenger, rightStation);
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testCheckMediumAsleepPassengersOutNeverForgetting() {
        Train train = new SomeTrain(new SomeStation("TEST1", 2), neverForgettingConductor, timeCounter);
        train.stopAt(rightStation);
        train.addPassenger(mediumAsleepPassenger);
        System.setOut(new PrintStream(outputStreamCaptor));
        neverForgettingConductor.checkPassengersOut(train);
        String output = String.format("""
                        %s says: "%s, you have to leave now!"
                        %s didn't hear %s cause %s is sleeping.
                        %s shakes %s.
                        %s is still sleeping. %s takes out tongs and starts knocking on his shelf.
                        %s is now awake.
                        Current station is %s's destination station.
                        %s leaves the train at %s.""",
                neverForgettingConductor, mediumAsleepPassenger, mediumAsleepPassenger, neverForgettingConductor, mediumAsleepPassenger, neverForgettingConductor,
                mediumAsleepPassenger, mediumAsleepPassenger,
                neverForgettingConductor, mediumAsleepPassenger, mediumAsleepPassenger, mediumAsleepPassenger, rightStation);
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testCheckDeepAsleepPassengersOutNeverForgetting() {
        Train train = new SomeTrain(new SomeStation("TEST1", 2), neverForgettingConductor, timeCounter);
        train.stopAt(rightStation);
        train.addPassenger(deepAsleepPassenger);
        System.setOut(new PrintStream(outputStreamCaptor));
        neverForgettingConductor.checkPassengersOut(train);
        String output = String.format("""
                        %s says: "%s, you have to leave now!"
                        %s didn't hear %s cause %s is sleeping.
                        %s shakes %s.
                        %s is still sleeping. %s realizes that he won't wake up and gets angry. %s shouts into %s's ear.
                        %s is now awake.
                        %s kicks %s out of the train.
                        %s is now shocked.
                        Current station is %s's destination station.
                        %s leaves the train at %s.""",
                neverForgettingConductor, deepAsleepPassenger, deepAsleepPassenger, neverForgettingConductor, deepAsleepPassenger, neverForgettingConductor,
                deepAsleepPassenger, deepAsleepPassenger, neverForgettingConductor, neverForgettingConductor, deepAsleepPassenger,
                deepAsleepPassenger, neverForgettingConductor, deepAsleepPassenger, deepAsleepPassenger, deepAsleepPassenger, deepAsleepPassenger, rightStation);
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }


    @Test
    void testCheckPassengersOut() {
        Train train = new SomeTrain(new SomeStation("TEST1", 2), basicConductor, timeCounter);
        train.stopAt(rightStation);
        train.addPassenger(awakePassenger);
        System.setOut(new PrintStream(outputStreamCaptor));
        basicConductor.checkPassengersOut(train);
        String output = "";
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }
}
