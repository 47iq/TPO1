package part3v2.route;

import part3v2.people.Conductor;
import part3v2.people.Passenger;
import part3v2.people.PassengerState;

import javax.management.InstanceAlreadyExistsException;
import java.util.NoSuchElementException;
import java.util.Set;

public class TrainImpl implements Train {

    private Station currentStation;

    private TrainCondition condition = TrainCondition.STAYING;

    private Set<Passenger> passengers;

    private Conductor conductor;

    public TrainImpl(Station currentStation, Set<Passenger> passengers, Conductor conductor) {
        this.currentStation = currentStation;
        this.passengers = passengers;
        this.conductor = conductor;
    }

    @Override
    public void startFromStation() {
        if(this.condition.equals(TrainCondition.GOING))
            throw new IllegalStateException("Train can't start from station if it is already moving.");
        if(passengers.isEmpty())
            throw new IllegalStateException("Train can't start from station if there are no passengers.");
        this.condition = TrainCondition.GOING;
    }

    @Override
    public void disappear() {
        this.condition = TrainCondition.GOING;
        System.out.printf("%s disappears in the distance...\n", this);
    }

    @Override
    public void arriveToStation(Station station) {
        if(this.condition.equals(TrainCondition.STAYING))
            throw new IllegalStateException("Train can't arrive to station if it os already staying somewhere.");
        System.out.printf("%s stops at %s\n", this, station);
        this.condition = TrainCondition.STAYING;
        this.currentStation = station;
    }

    @Override
    public void addPassenger(Passenger passenger) throws InstanceAlreadyExistsException {
        if(this.condition != TrainCondition.STAYING) {
            throw new IllegalStateException("Can't add passenger while the train is on move.");
        }
        if(this.passengers.contains(passenger)) {
            throw new InstanceAlreadyExistsException("This passenger is already in the train");
        }
        this.passengers.add(passenger);
    }

    @Override
    public void removePassenger(Passenger passenger) {
        this.conductor.checkPassengersOut(passenger, this.currentStation);
        if(this.condition != TrainCondition.STAYING) {
            throw new IllegalStateException("Can't remove passenger while the train is on move.");
        }
        if(passenger.getPassengerState().equals(PassengerState.LIGHTLY_ASLEEP) | passenger.getPassengerState().equals(PassengerState.DEEPLY_ASLEEP)) {
            throw new IllegalStateException("Can't remove sleeping passenger.");
        }
        boolean isRemoved = this.passengers.remove(passenger);
        if (!isRemoved) {
            throw new NoSuchElementException("This passenger isn't in the train");
        }
        System.out.printf("%s leaves the %s at %s.\n", passenger, this, this.currentStation);
        passenger.reactOnLeavingTrain(this.currentStation);
    }

    @Override
    public String toString() {
        return "Train";
    }
}
