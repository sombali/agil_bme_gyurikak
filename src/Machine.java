import java.util.List;

public class Machine {
    private int type;
    private double temperatureTreshold;
    private double powerTreshold;
    private List<Sensor> sensors;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public double getTemperatureTreshold() {
        return temperatureTreshold;
    }

    public void setTemperatureTreshold(double temperatureTreshold) {
        this.temperatureTreshold = temperatureTreshold;
    }

    public double getPowerTreshold() {
        return powerTreshold;
    }

    public void setPowerTreshold(double powerTreshold) {
        this.powerTreshold = powerTreshold;
    }
}
