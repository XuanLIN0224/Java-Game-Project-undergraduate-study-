import bagel.Input;
import bagel.Keys;

import java.util.Properties;

public abstract class GameObject {
    public final Properties GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
    //I made the variables protected rather than private because otherwise the child classes cannot access them
    protected double x;
    protected double y;
    public final double ORIGINAL_X;
    public final double ORIGINAL_Y;
    protected boolean isPlayerDead = false;
    public GameObject(double x, double y) {
        this.x = x;
        this.y = y;
        this.ORIGINAL_X = x;
        this.ORIGINAL_Y = y;
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
        x = ORIGINAL_X;
        y = ORIGINAL_Y;
        isPlayerDead = false;
    }
    public abstract void update(Input input, Level level);
}
