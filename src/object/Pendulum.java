package object;

/**
 * Simple pendulum helper — encapsulates pendulum state and update logic.
 * Returns bob (x,y) in world coordinates.
 */
public class Pendulum {
    private double pivotX;
    private double pivotY;
    private double length;
    private double angle; // radians
    private double angularVelocity;
    private double angularAcceleration;
    private double damping = 5;
    private double gravity = 980.0; // pixels / s^2

    public Pendulum(double pivotX, double pivotY, double length, double startAngleDegrees) {
        this.pivotX = pivotX;
        this.pivotY = pivotY;
        this.length = Math.max(1.0, length);
        this.angle = Math.toRadians(startAngleDegrees);
        this.angularVelocity = 0.0;
        this.angularAcceleration = 0.0;
    }

    public void update(double dt) {
        if (length <= 0) return;
        angularAcceleration = - (gravity / length) * Math.sin(angle);
        angularVelocity += angularAcceleration * dt;
        angularVelocity *= damping;
        angle += angularVelocity * dt;
    }

    public double getBobX() {
        return pivotX + length * Math.sin(angle);
    }

    public double getBobY() {
        return pivotY + length * Math.cos(angle);
    }

    // setters/getters
    public void setPivot(double x, double y) { this.pivotX = x; this.pivotY = y; }
    public void setLength(double l) { this.length = Math.max(1.0, l); }
    public void setDamping(double d) { this.damping = d; }
    public void setGravity(double g) { this.gravity = g; }
    public double getPivotX() { return pivotX; }
    public double getPivotY() { return pivotY; }
}
