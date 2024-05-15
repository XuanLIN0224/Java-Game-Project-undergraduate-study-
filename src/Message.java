import bagel.Font;
import bagel.Image;

import java.util.Properties;

public class Message {
    private final Properties GAME_PROPS;
    public final Properties MESSAGE_PROPS;
    //I made the variables protected rather than private because otherwise the child classes cannot access them
    public final Image BACKGROUND_IMAGE;
    public final String FONT_SOURCE;
    public final int MESSAGE_FONT_SIZE;
    public final int Y;
    public final int WINDOW_WIDTH;
    public final Font FONT;
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
