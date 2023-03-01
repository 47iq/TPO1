package part3;

public class SomeGroupOfPassengers extends SomePassenger implements Groupable {
    SomeGroupOfPassengers(String names, PassengerCondition condition, Station destination, double depthOfSleep,
                          Actions actions) {
        super(names, condition, destination, depthOfSleep, actions);
    }

    public String toString() {
        return super.getType() + "s " + super.getName();
    }

    public void say(String s) {
        System.out.print(this + " say: \"" + s + "\"\n");
    }

    public void sleep() {
        System.out.print(this+ " are sleeping. ");
        say("Zzz..");
    }

    protected void commentOnSetCondition(PassengerCondition condition){
        System.out.print(this + " are now " + condition + ".\n");
    }

    public void commentOnLeavingTrain(Station station) {
        if(station == null)
            throw new NullPointerException();
        if(getCondition().equals(PassengerCondition.ASLEEP))
            return;
        System.out.print(this + " leave the train at " + station + ".\n");
        if(station != getDestination()) {
            if(!getCondition().equals(PassengerCondition.SHOCKED))
                say("Hey! That's not our station!");
            else
                System.out.println(this + " want to say something but are too shocked to open their mouths.");
        }
    }

    public void fallAsleepTime(TimeCounter counter) {
        if(counter == null)
            throw new NullPointerException();
        if(!super.getCondition().equals(PassengerCondition.ASLEEP)) {
            System.out.print(this + " fall asleep because the time is " + counter + "\n");
            super.setCondition(PassengerCondition.ASLEEP);
        }
    }

}
