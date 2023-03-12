package part3v2.route;

public class StationImpl implements Station{

    private String name;

    public StationImpl(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Station " + name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        StationImpl station = (StationImpl) o;

        return station.getName().equals(this.name);
    }
}
