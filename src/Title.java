import bagel.Font;
import bagel.Image;
import bagel.Input;

import java.util.Properties;

public class Title {
    private final Properties GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
    private final Properties MESSAGE_PROPS = IOUtils.readPropertiesFile("res/message_en.properties");
    private final String FONT_SOURCE;
    private final String TITLE;
    private final int TITLE_FONT_SIZE;
    private final int X;
    private final int Y;
    private final Font FONT;

    public Title() {
        FONT_SOURCE = GAME_PROPS.getProperty("font");
        TITLE = MESSAGE_PROPS.getProperty("title");
        TITLE_FONT_SIZE = Integer.parseInt(GAME_PROPS.getProperty("title.fontSize"));
        X = Integer.parseInt(GAME_PROPS.getProperty("title.x"));
        Y = Integer.parseInt(GAME_PROPS.getProperty("title.y"));
        FONT = new Font(FONT_SOURCE, TITLE_FONT_SIZE);
    }
    public void update() {
        FONT.drawString(TITLE, X, Y);
    }
}
