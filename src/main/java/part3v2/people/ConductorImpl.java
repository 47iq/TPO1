package part3v2.people;

import part3v2.route.Station;

import java.util.HashSet;
import java.util.Set;

public class ConductorImpl implements Conductor{

    private boolean canForget;

    Set<Passenger> forgottenPassengers = new HashSet<>();

    public ConductorImpl(boolean canForget) {
        this.canForget = canForget;
    }

    @Override
    public void checkPassengersOut(Passenger passenger, Station station) {
        if(!passenger.getArrivalStation().equals(station)) {
            System.out.printf("%s forgot to tell %s to leave the train at %s. He decided to wait until %s to avoid explanations.\n",
                    this, passenger, passenger.getArrivalStation(), station);
        }
        switch (passenger.getPassengerState()) {
            case AWAKE, SHOCKED, SATISFIED -> {
                System.out.printf("%s kindly asks %s to leave the train.\n", this, passenger);
            }
            case LIGHTLY_ASLEEP -> {
                System.out.printf("%s kindly asks %s to leave the train, but %s is asleep. " +
                                "%s takes out his tongs and starts knocking on the shelves.\n",
                        this, passenger, passenger, this);
                passenger.setPassengerState(PassengerState.AWAKE);
            }
            case DEEPLY_ASLEEP -> {
                System.out.printf("""
                                %s kindly asks %s to leave the train, but %s is asleep. %s takes out his tongs and starts knocking on the shelves.
                                %s is still asleep. %s kicks %s and his baggage out of the train.
                                """,
                        this, passenger, passenger, this, passenger, this, passenger);
                passenger.setPassengerState(PassengerState.SHOCKED);
            }
        }
    }

    @Override
    public String toString() {
        return "Conductor";
    }
}
