import bagel.Input;
import bagel.Window;

import java.util.Properties;
/**
 * Code for win message
 * written by
 * @xulin2
 */
public class Win extends Message {
    private final String WIN_MESSAGE;
    private final double MESSAGE_WIDTH;
    /**
     * The constructor
     */
    public Win() {
        super();
        WIN_MESSAGE = MESSAGE_PROPS.getProperty("gameWon");
        MESSAGE_WIDTH = FONT.getWidth(WIN_MESSAGE);
    }
    /**
     * Performs a state update.
     * display win message on screen
     */
    public void update() {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        FONT.drawString(WIN_MESSAGE, (int)((WINDOW_WIDTH - MESSAGE_WIDTH)/2), Y);
    }
}
