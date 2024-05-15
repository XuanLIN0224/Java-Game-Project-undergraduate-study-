import java.util.Properties;
/**
 * Code for the parent class Health
 * written by
 * @xulin2
 */
public class Health {
    /**
     * GAME_PROPS can be reused in the subclasses therefore it is protected
     */
    public final Properties GAME_PROPS;
    private final Properties MESSAGE_PROPS;
    /**
     * FONT_SOURCE can be different in the subclasses therefore it is protected
     */
    protected final String FONT_SOURCE;
    /**
     * HEALTH_MESSAGE can be different in the subclasses therefore it is protected
     */
    protected final String HEALTH_MESSAGE;
    private double health;
    /**
     * The constructor
     */
    public Health() {
        GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
        MESSAGE_PROPS = IOUtils.readPropertiesFile("res/message_en.properties");
        FONT_SOURCE = GAME_PROPS.getProperty("font");
        HEALTH_MESSAGE = MESSAGE_PROPS.getProperty("health");
        health = 1f;
    }
    /**
     * reset the health to 1
     */
    public void resetHealth() {
        health = 1f;
    }

    /**
     * get the health
     */
    public double getHealth() {
        return health;
    }

    /**
     * Performs a state update.
     * display on screen
     */
    public void updateHealth(double health) {
        this.health = (this.health * 100 - health * 100)/100;
    }
}
