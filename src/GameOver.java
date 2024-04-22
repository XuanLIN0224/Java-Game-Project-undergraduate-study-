import bagel.Input;
import bagel.Window;

import java.util.Properties;

public class GameOver extends Message{
    Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");
    private String gameOverMessage;
    private double messageWidth;

    public GameOver() {
        super();
        gameOverMessage = message_props.getProperty("gameOver");
        messageWidth = font.getWidth(gameOverMessage);
    }
    public void update(Input input) {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        font.drawString(gameOverMessage, (int)((windowWidth - messageWidth)/2), y);
    }
}
