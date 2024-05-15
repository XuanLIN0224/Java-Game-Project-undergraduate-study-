import bagel.Font;
import bagel.Image;
import bagel.Input;

import java.util.Properties;
/**
 * Code for title
 * written by
 * @xulin2
 */
public class Title {
    private final Properties GAME_PROPS;
    private final Properties MESSAGE_PROPS;
    private final String FONT_SOURCE;
    private final String TITLE;
    private final int TITLE_FONT_SIZE;
    private final int X;
    private final int Y;
    private final Font FONT;
    /**
     * The constructor
     */
    public Title() {
        GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
        MESSAGE_PROPS = IOUtils.readPropertiesFile("res/message_en.properties");
        FONT_SOURCE = GAME_PROPS.getProperty("font");
        TITLE = MESSAGE_PROPS.getProperty("title");
        TITLE_FONT_SIZE = Integer.parseInt(GAME_PROPS.getProperty("title.fontSize"));
        X = Integer.parseInt(GAME_PROPS.getProperty("title.x"));
        Y = Integer.parseInt(GAME_PROPS.getProperty("title.y"));
        FONT = new Font(FONT_SOURCE, TITLE_FONT_SIZE);
    }
    /**
     * Performs a state update.
     * display title on screen
     */
    public void update() {
        FONT.drawString(TITLE, X, Y);
    }
}
