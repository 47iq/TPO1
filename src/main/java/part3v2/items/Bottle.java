package part3v2.items;

import part3v2.people.Passenger;

public interface Bottle extends Item {
    void bendDown(Passenger nearestPassenger);
    BottleState getBottleState();
}
