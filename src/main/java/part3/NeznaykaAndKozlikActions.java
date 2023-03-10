package part3;

public class NeznaykaAndKozlikActions implements Actions{
    NeznaykaAndKozlikActions() { }

    public void completeActions(Passenger passenger) {
        if(!passenger.getName().equals("Neznayka and Kozlik") || !(passenger instanceof Groupable))
            throw new IllegalActionsTarget("Either passenger must be instance of \"Groupable\" or " +
                    "passengerGroup name must be \"Neznayka and Kozlik\"!");
        System.out.println(passenger + " are watching " + new SomeThing("Horror movies"){});
    }
}
