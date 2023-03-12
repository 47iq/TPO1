package part3v2.people;

import part3v2.items.Bottle;
import part3v2.items.Item;
import part3v2.route.Station;

import java.util.Objects;

public class ScuperfieldImpl extends Passenger {

    private final Item freeItem;

    private final Item lostItem;

    private Bottle bottle;

    public ScuperfieldImpl(Item freeItem, Item lostItem, Station station, Bottle bottle) {
        this.freeItem = freeItem;
        this.lostItem = lostItem;
        this.bottle = bottle;
        this.arrivalStation = station;
        this.name = "Scuperfield";
    }

    public ScuperfieldImpl(Item freeItem, Item lostItem, Station station, Bottle bottle, PassengerState passengerState) {
        this.passengerState = passengerState;
        this.freeItem = freeItem;
        this.lostItem = lostItem;
        this.bottle = bottle;
        this.arrivalStation = station;
        this.name = "Scuperfield";
    }

    @Override
    public void completeActionsInternal() {
        bottle.bendDown(this);
        int numberOfTrips = (int) Math.ceil(calculateLoss());
        System.out.printf("%s has calculated, that he has to take only %s train trips to satisfy the loss of his %s.\n",
                this, numberOfTrips, lostItem);
        this.setPassengerState(PassengerState.HAPPY);
        bottle.bendDown(this);
        this.areActionsCompleted = true;
    }

    public double calculateLoss() {
        if(freeItem.getCost() == 0 || lostItem.getCost() == 0)
            throw new IllegalArgumentException("Item cost can't be zero.");
        return lostItem.getCost() / freeItem.getCost();
    }
}
