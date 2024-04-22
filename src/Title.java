import bagel.Font;
import bagel.Image;
import bagel.Input;

import java.util.Properties;

public class Title {
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");
    private String FontSource;
    private String title;
    private int titleFontSize;
    private int x;
    private int y;
    private Font font;

    public Title() {
        FontSource = game_props.getProperty("font");
        title = message_props.getProperty("title");
        titleFontSize = Integer.parseInt(game_props.getProperty("title.fontSize"));
        x = Integer.parseInt(game_props.getProperty("title.x"));
        y = Integer.parseInt(game_props.getProperty("title.y"));
        font = new Font(FontSource, titleFontSize);
    }
    public void update(Input input) {
        font.drawString(title, x, y);
    }
}
