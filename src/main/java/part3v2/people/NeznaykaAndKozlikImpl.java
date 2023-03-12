package part3v2.people;

import part3v2.route.Station;

import java.util.Objects;

public class NeznaykaAndKozlikImpl extends Passenger {

    public NeznaykaAndKozlikImpl(Station station) {
        this.arrivalStation = station;
        this.name = "Neznayka and Kozlik";
    }

    @Override
    protected void completeActionsInternal() {
        System.out.printf("%s is watching horror movies.\n", this);
        this.areActionsCompleted = true;
    }
}
