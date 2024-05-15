import bagel.Font;
import bagel.Input;
import bagel.Window;

import java.util.Properties;

public class GameOver extends Message{
    private final String GAME_OVER_MESSAGE;
    private final double MESSAGE_WIDTH;

    public GameOver() {
        super();
        GAME_OVER_MESSAGE = MESSAGE_PROPS.getProperty("gameOver");
        MESSAGE_WIDTH = FONT.getWidth(GAME_OVER_MESSAGE);
    }
    public void update() {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        FONT.drawString(GAME_OVER_MESSAGE, (int)((WINDOW_WIDTH - MESSAGE_WIDTH)/2), Y);
    }
}
