import bagel.Font;
import bagel.Image;
import bagel.Input;
import bagel.Window;

import java.util.Properties;

public class StartInstruction {
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");
    private final Image BACKGROUND_IMAGE;
    private String FontSource;
    private String instructionMessage;
    private int ScoreFontSize;
    private int y;
    private int windowWidth;
    private Font font;
    private double messageWidth;

    public StartInstruction() {
        BACKGROUND_IMAGE = new Image(game_props.getProperty("backgroundImage"));
        FontSource = game_props.getProperty("font");
        instructionMessage = message_props.getProperty("instruction");
        ScoreFontSize = Integer.parseInt(game_props.getProperty("instruction.fontSize"));
        y = Integer.parseInt(game_props.getProperty("instruction.y"));
        windowWidth = Integer.parseInt(game_props.getProperty("windowWidth"));
        font = new Font(FontSource, ScoreFontSize);
        messageWidth = font.getWidth(instructionMessage);
    }
    public void update(Input input) {
        BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
        font.drawString(instructionMessage, (int)((windowWidth -messageWidth)/2), y);
    }
}
