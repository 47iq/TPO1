package part3v2.route;

import part3v2.people.Passenger;

import javax.management.InstanceAlreadyExistsException;
import java.util.Set;

public interface Train {
    void startFromStation();
    void disappear();
    void arriveToStation(Station station);
    void addPassenger(Passenger passenger) throws InstanceAlreadyExistsException;
    void removePassenger(Passenger passenger);
    Set<Passenger> getPassengers();
    TrainCondition getCondition();
    Station getCurrentStation();
}
