package part3v2.route;

import part3v2.people.Passenger;

import javax.management.InstanceAlreadyExistsException;

public interface Train {
    void startFromStation();
    void disappear();
    void arriveToStation(Station station);
    void addPassenger(Passenger passenger) throws InstanceAlreadyExistsException;
    void removePassenger(Passenger passenger);
}
