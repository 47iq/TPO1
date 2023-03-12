package part3v2.people;

import part3v2.route.Station;

import java.util.Set;

public interface Conductor {
    void checkPassengersOut(Passenger passenger, Station station);
}
