package part3v2.people;

import part3v2.route.Station;

import java.util.Locale;
import java.util.Objects;

public abstract class Passenger {

    protected String name;

    protected boolean areActionsCompleted = false;

    protected Station arrivalStation;

    protected PassengerState passengerState= PassengerState.AWAKE;

    public void reactOnLeavingTrain(Station station) {
        if(this.getPassengerState().equals(PassengerState.LIGHTLY_ASLEEP) | this.getPassengerState().equals(PassengerState.DEEPLY_ASLEEP)) {
            throw new IllegalStateException();
        }
        if(station.equals(this.arrivalStation)) {
            System.out.printf("%s leaves at his station.\n", this);
            this.setPassengerState(PassengerState.SATISFIED);
        } else {
            System.out.printf("%s leaves at the wrong station.\n", this);
            this.setPassengerState(PassengerState.ANGRY);
        }
    }

    public void completeActions() {
        if(this.areActionsCompleted())
            throw new IllegalStateException("Actions have been already completed.");
        if(this.passengerState.equals(PassengerState.DEEPLY_ASLEEP) || this.passengerState.equals(PassengerState.LIGHTLY_ASLEEP))
            throw new IllegalStateException(String.format("%s is asleep and can't complete actions", this));
        this.completeActionsInternal();
    }

    protected abstract void completeActionsInternal();

    public void fallAsleep(boolean isDeeplyAsleep) {
        if (isDeeplyAsleep)
            this.setPassengerState(PassengerState.DEEPLY_ASLEEP);
        else
            this.setPassengerState(PassengerState.LIGHTLY_ASLEEP);
    }

    public boolean areActionsCompleted() {
        return this.areActionsCompleted;
    }

    public void setPassengerState(PassengerState passengerState) {
        this.passengerState = passengerState;
        System.out.printf("%s is now %s.\n", this, passengerState.toString().toLowerCase(Locale.ROOT));
    }

    public Station getArrivalStation() {
        return arrivalStation;
    }

    public PassengerState getPassengerState() {
        return passengerState;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Passenger passenger = (Passenger) o;
        return Objects.equals(name, passenger.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

    @Override
    public String toString() {
        return name;
    }
}
