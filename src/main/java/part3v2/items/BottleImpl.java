package part3v2.items;

import part3v2.people.Passenger;
import part3v2.people.PassengerState;

public class BottleImpl implements Bottle {

    private BottleState bottleState;

    private final double cost;

    public BottleImpl(BottleState bottleState, double cost) {
        this.bottleState = bottleState;
        this.cost = cost;
    }


    @Override
    public void bendDown(Passenger nearestPassenger) {
        switch (bottleState) {
            case DEFAULT -> {
                this.bottleState = BottleState.BENT_DOWN;
                System.out.println("Bottle has bent down.");
                this.spillOnPassenger(nearestPassenger);
            }
            case BENT_DOWN -> {
                this.bottleState = BottleState.FELL_DOWN;
                this.spillOnPassenger(nearestPassenger);
                System.out.printf("Bottle falls down on %s head.\n", nearestPassenger);
                nearestPassenger.setPassengerState(PassengerState.SHOCKED);
            }
            case FELL_DOWN -> {
                throw new IllegalStateException();
            }
        }
    }

    private void spillOnPassenger(Passenger passenger) {
        System.out.printf("Bottle spills on %s\nSweet liquid appears in %s's mouth.\n", passenger, passenger);
        passenger.setPassengerState(PassengerState.SATISFIED);
    }

    public BottleState getBottleState() {
        return bottleState;
    }

    public double getCost() {
        return cost;
    }
}
