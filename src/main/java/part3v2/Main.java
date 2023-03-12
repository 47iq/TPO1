package part3v2;

import part3v2.items.Bottle;
import part3v2.items.BottleImpl;
import part3v2.items.BottleState;
import part3v2.items.Item;
import part3v2.people.*;
import part3v2.route.Station;
import part3v2.route.StationImpl;
import part3v2.route.Train;
import part3v2.route.TrainImpl;

import java.util.HashSet;
import java.util.Set;

public class Main {

    public static void main(String[] args) {
        Station start = new StationImpl("Unknown station");
        Station brehenville = new StationImpl("Brehenville");
        Station panopticon = new StationImpl("Panopticon");
        Station sanKomarik = new StationImpl("San-Komarik");
        Passenger neznaykaAndKozlik = new NeznaykaAndKozlikImpl(sanKomarik);
        Bottle bottle = new BottleImpl(BottleState.DEFAULT, 10);
        Item lostCane = new Item() {
            @Override
            public double getCost() {
                return 10000;
            }

            @Override
            public String toString() {
                return "Cane";
            }
        };
        Passenger scuperfield = new ScuperfieldImpl(bottle, lostCane, brehenville, bottle);
        Set<Passenger> passengers = new HashSet<>();
        passengers.add(neznaykaAndKozlik);
        passengers.add(scuperfield);
        Conductor conductor = new ConductorImpl();
        Train train = new TrainImpl(start, passengers, conductor);
        train.startFromStation();
        neznaykaAndKozlik.completeActions();
        neznaykaAndKozlik.fallAsleep(false);
        scuperfield.completeActions();
        scuperfield.fallAsleep(true);
        train.arriveToStation(brehenville);
        train.startFromStation();
        train.arriveToStation(panopticon);
        train.removePassenger(scuperfield);
        train.startFromStation();
        train.arriveToStation(sanKomarik);
        train.removePassenger(neznaykaAndKozlik);
        train.disappear();
    }
}
