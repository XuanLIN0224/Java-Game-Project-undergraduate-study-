import bagel.Keys;

public class GameObject {
    //I made the variables protected rather than private because otherwise the child classes cannot access them
    protected double x;
    protected double y;
    public final double original_x;
    public final double original_y;
    protected boolean isPlayerDead = false;
    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
        this.original_x = x;
        this.original_y = y;
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public void setPlayerDead() {
        isPlayerDead = true;
    }
    public void resetObject() {
        x = original_x;
        y = original_y;
        isPlayerDead = false;
    }
}
