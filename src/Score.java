import bagel.Font;
import bagel.Input;

import java.util.Properties;

public class Score {
    private final Properties GAME_PROPS;
    private final Properties MESSAGE_PROPS;
    private final String FONT_SOURCE;
    private final String SCORE_MESSAGE;
    private final int SCORE_FONT_SIZE;
    private final Font FONT;
    private final int SCORE_X;
    private final int SCORE_Y;
    private int score;

    public Score() {
        GAME_PROPS = IOUtils.readPropertiesFile("res/app.properties");
        MESSAGE_PROPS = IOUtils.readPropertiesFile("res/message_en.properties");
        FONT_SOURCE = GAME_PROPS.getProperty("font");
        SCORE_MESSAGE = MESSAGE_PROPS.getProperty("score");
        SCORE_FONT_SIZE = Integer.parseInt(GAME_PROPS.getProperty("score.fontSize"));
        FONT = new Font(FONT_SOURCE, SCORE_FONT_SIZE);
        SCORE_X = Integer.parseInt(GAME_PROPS.getProperty("score.x"));
        SCORE_Y = Integer.parseInt(GAME_PROPS.getProperty("score.y"));
        score = 0;
    }

    public void updateScore(int score) {
        this.score += score;
    }
    public void resetScore() {
        score = 0;
    }

    public void update() {
        String scoreText = SCORE_MESSAGE + score;
        FONT.drawString(scoreText, SCORE_X, SCORE_Y);
    }

}
