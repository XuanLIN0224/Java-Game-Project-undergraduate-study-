import java.util.Properties;

public class Health {
    public final Properties GAME_PROPS;
    private final Properties MESSAGE_PROPS;
    public final String FONT_SOURCE;
    public final String HEALTH_MESSAGE;
    protected double health;

    public Health() {
        GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
        MESSAGE_PROPS = IOUtils.readPropertiesFile("res/message_en.properties");
        FONT_SOURCE = GAME_PROPS.getProperty("font");
        HEALTH_MESSAGE = MESSAGE_PROPS.getProperty("health");
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
