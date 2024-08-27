package bicyclemodel;

public class Bicycle {
    private final double drag, pedalPower, brakingPower;
    private double speed;
    private double direction;

    public Bicycle() {
        this(0.1, 2, 3);
    }

    public Bicycle(double drag, double pedalPower, double brakingPower) {
        this.drag = drag;
        this.pedalPower = pedalPower;
        this.brakingPower = brakingPower;
    }

    public double getDirection() {
        return direction;
    }

    public void setDirection(double direction) {
        this.direction = Math.min(90, Math.max(-90, direction));
    }

    public double getSpeed() {
        return speed;
    }

    public void pedalHarder() {
        speed = (speed + pedalPower) * (1 - drag);
    }

    public void brake() {
        speed = Math.max(0, (speed - brakingPower) * (1 - drag));
    }
}
