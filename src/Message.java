import bagel.Font;
import bagel.Image;

import java.util.Properties;
/**
 * Code for the parent class message
 * the subclasses can be Win and GameOver
 * written by
 * @xulin2
 */
public class Message {
    private final Properties GAME_PROPS;
    /**
     * MESSAGE_PROPS can be reused in the subclasses therefore it is protected
     */
    protected final Properties MESSAGE_PROPS;
    /**
     * BACKGROUND_IMAGE will be used in different subclasses therefore it is protected
     */
    protected final Image BACKGROUND_IMAGE;
    /**
     * FONT_SOURCE can be different in the subclasses therefore it is protected
     */
    protected final String FONT_SOURCE;
    /**
     * MESSAGE_FONT_SIZE can be different in the subclasses therefore it is protected
     */
    protected final int MESSAGE_FONT_SIZE;
    /**
     * Y can be different in the subclasses therefore it is protected
     */
    protected final int Y;
    /**
     * WINDOW_WIDTH will be used in different subclasses therefore it is protected
     */
    protected final int WINDOW_WIDTH;
    /**
     * FONT can be different in the subclasses therefore it is protected
     */
    protected final Font FONT;

    /**
     * The constructor
     */
    public Message() {
        GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
        MESSAGE_PROPS = IOUtils.readPropertiesFile("res/message_en.properties");
        BACKGROUND_IMAGE = new Image(GAME_PROPS.getProperty("backgroundImage"));
        FONT_SOURCE = GAME_PROPS.getProperty("font");
        MESSAGE_FONT_SIZE = Integer.parseInt(GAME_PROPS.getProperty("message.fontSize"));
        Y = Integer.parseInt(GAME_PROPS.getProperty("message.y"));
        WINDOW_WIDTH = Integer.parseInt(GAME_PROPS.getProperty("windowWidth"));
        FONT = new Font(FONT_SOURCE, MESSAGE_FONT_SIZE);
    }
}
