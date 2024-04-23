import java.util.Properties;

public class Health {
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");
    public final String FontSource;
    public final String healthMessage;
    protected double health;

    public Health() {
        FontSource = game_props.getProperty("font");
        healthMessage = message_props.getProperty("health");
        health = 1f;
    }
    public void resetHealth() {
        health = 1f;
    }

    public double getHealth() {
        return health;
    }
    public void updateHealth(double health) {
        this.health = (this.health * 100 - health * 100)/100;
    }
}
