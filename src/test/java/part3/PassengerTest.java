package part3;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PassengerTest {

    Passenger asleepPassenger;

    Passenger awakePassenger;

    Passenger shockedPassenger;

    Passenger passengerGroup;

    Station rightStation;

    TimeCounter timeCounter;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    public void setUp() {
        this.rightStation = new SomeStation("TEST_STATION", 2);
        this.asleepPassenger = new SomePassenger("ASLEEP", PassengerCondition.ASLEEP,
                rightStation, 1, new ScuperfieldActions());
        this.awakePassenger = new SomePassenger("AWAKE", PassengerCondition.REGULAR_AWAKE,
                rightStation, 1, new ScuperfieldActions());
        this.shockedPassenger = new SomePassenger("SHOCKED", PassengerCondition.SHOCKED,
                rightStation, 1, new ScuperfieldActions());
        this.passengerGroup = new SomeGroupOfPassengers("GROUP", PassengerCondition.REGULAR_AWAKE,
                rightStation, 1, new ScuperfieldActions());
        this.timeCounter = new SomeTimeCounter(3);
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @Test
    void testPassengerSleep() {
        this.asleepPassenger.sleep();
        String output = String.format("Passenger %s is sleeping. Passenger %s says: \"Zzz..\"",
                this.asleepPassenger.getName(), this.asleepPassenger.getName());
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testPassengerGroupSleep() {
        this.passengerGroup.sleep();
        String output = String.format("Passengers %s are sleeping. Passengers %s say: \"Zzz..\"",
                this.passengerGroup.getName(), this.passengerGroup.getName());
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testPassengerFallAsleep() {
        this.awakePassenger.fallAsleepTime(this.timeCounter);
        String output = String.format("%s falls asleep because the time is %s\n%s is now asleep.",
                this.awakePassenger.toString(), this.timeCounter.toString(), this.awakePassenger.toString());
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testPassengerGroupFallAsleep() {
        this.passengerGroup.fallAsleepTime(this.timeCounter);
        String output = String.format("%s fall asleep because the time is %s\n%s are now asleep.",
                this.passengerGroup.toString(), this.timeCounter.toString(), this.passengerGroup.toString());
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testSleepingPassengerFallAsleep() {
        this.asleepPassenger.fallAsleepTime(this.timeCounter);
        String output = "";
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testShockedLeavingTrain() {
        Station outStation = new SomeStation("TEST2", 2);
        this.shockedPassenger.commentOnLeavingTrain(outStation);
        String output = String.format("%s leaves the train at %s.\n%s wants to say something but is too shocked to open his mouth.",
                this.shockedPassenger.toString(), outStation.toString(),this.shockedPassenger.toString());
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testLeavingTrain() {
        Station outStation = new SomeStation("TEST2", 2);
        this.awakePassenger.commentOnLeavingTrain(outStation);
        String output = String.format("%s leaves the train at %s.\n%s says: \"Hey! That's not my station!\"",
                this.awakePassenger.toString(), outStation.toString(), this.awakePassenger.toString());
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testGroupLeavingTrain() {
        Station outStation = new SomeStation("TEST2", 2);
        this.passengerGroup.commentOnLeavingTrain(outStation);
        String output = String.format("%s leave the train at %s.\n%s say: \"Hey! That's not our station!\"",
                this.passengerGroup.toString(), outStation.toString(), this.passengerGroup.toString());
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testLeavingTrainRightStation() {
        this.awakePassenger.commentOnLeavingTrain(this.rightStation);
        String output = String.format("%s leaves the train at %s.",
                this.awakePassenger.toString(), this.rightStation.toString());
        Assertions.assertEquals(output, outputStreamCaptor.toString()
                .trim());
    }

    @Test
    void testAsleepLeavingTrain() {
        Exception exception = assertThrows(Exception.class, () -> {
            this.asleepPassenger.commentOnLeavingTrain(this.rightStation);
        });
    }
}
