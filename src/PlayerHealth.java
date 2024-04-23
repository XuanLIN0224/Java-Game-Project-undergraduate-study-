import bagel.Font;
import bagel.Input;

import java.util.Properties;

public class PlayerHealth extends Health{
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    private int HealthFontSize;
    private Font font;
    private int Health_x;
    private int Health_y;

    public PlayerHealth() {
        super();
        HealthFontSize = Integer.parseInt(game_props.getProperty("playerHealth.fontSize"));
        font = new Font(FontSource, HealthFontSize);
        Health_x = Integer.parseInt(game_props.getProperty("playerHealth.x"));
        Health_y = Integer.parseInt(game_props.getProperty("playerHealth.y"));
    }

    public void update(Input input) {
        String scoreText = healthMessage + (int)(health*100);
        font.drawString(scoreText, Health_x, Health_y);
    }
}
