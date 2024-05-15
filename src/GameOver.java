import bagel.Window;
/**
 * Code for GameOver
 * written by
 * @xulin2
 */
public class GameOver extends Message{
    private final String GAME_OVER_MESSAGE;
    private final double MESSAGE_WIDTH;
    /**
     * The constructor
     */
    public GameOver() {
        super();
        GAME_OVER_MESSAGE = MESSAGE_PROPS.getProperty("gameOver");
        MESSAGE_WIDTH = FONT.getWidth(GAME_OVER_MESSAGE);
    }
    /**
     * Performs a state update.
     * display on screen
     */
    public void update() {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        FONT.drawString(GAME_OVER_MESSAGE, (int)((WINDOW_WIDTH - MESSAGE_WIDTH)/2), Y);
    }
}
