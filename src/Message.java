import bagel.Font;
import bagel.Image;

import java.util.Properties;

public class Message {
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    //I made the variables protected rather than private because otherwise the child classes cannot access them
    public final Image BACKGROUND_IMAGE;
    public final String FontSource;
    public final int messageFontSize;
    public final int y;
    public final int windowWidth;
    public final Font font;
    public Message() {
        BACKGROUND_IMAGE = new Image(game_props.getProperty("backgroundImage"));
        FontSource = game_props.getProperty("font");
        messageFontSize = Integer.parseInt(game_props.getProperty("message.fontSize"));
        y = Integer.parseInt(game_props.getProperty("message.y"));
        windowWidth = Integer.parseInt(game_props.getProperty("windowWidth"));
        font = new Font(FontSource, messageFontSize);
    }
}
