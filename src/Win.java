import bagel.Input;
import bagel.Window;

import java.util.Properties;

public class Win extends Message {
    Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");
    private String winMessage;
    private double messageWidth;

    public Win() {
        super();
        winMessage = message_props.getProperty("gameWon");
        messageWidth = font.getWidth(winMessage);
    }

    public void update() {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        font.drawString(winMessage, (int)((windowWidth - messageWidth)/2), y);
    }
}
