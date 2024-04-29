import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Window;

import java.util.Properties;

public class StartInstruction {
    private final Properties GAME_PROPS;
    private final Properties MESSAGE_PROPS;
    private final Image BACKGROUND_IMAGE;
    private final String FONT_SOURCE;
    private final String INSTRUCTION_MESSAGE;
    private final int INSTRUCTION_FONT_SIZE;
    private final int Y;
    private final int WINDOW_WIDTH;
    private final Font FONT;
    private final double MESSAGE_WIDTH;

    public StartInstruction() {
        GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
        MESSAGE_PROPS = IOUtils.readPropertiesFile("res/message_en.properties");
        BACKGROUND_IMAGE = new Image(GAME_PROPS.getProperty("backgroundImage"));
        FONT_SOURCE = GAME_PROPS.getProperty("font");
        INSTRUCTION_MESSAGE = MESSAGE_PROPS.getProperty("instruction");
        INSTRUCTION_FONT_SIZE= Integer.parseInt(GAME_PROPS.getProperty("instruction.fontSize"));
        Y = Integer.parseInt(GAME_PROPS.getProperty("instruction.y"));
        WINDOW_WIDTH = Integer.parseInt(GAME_PROPS.getProperty("windowWidth"));
        FONT = new Font(FONT_SOURCE, INSTRUCTION_FONT_SIZE);
        MESSAGE_WIDTH = FONT.getWidth(INSTRUCTION_MESSAGE);
    }
    public void update() {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        FONT.drawString(INSTRUCTION_MESSAGE, (int)((WINDOW_WIDTH - MESSAGE_WIDTH)/2), Y);
    }
}
