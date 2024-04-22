import bagel.Font;
import bagel.Input;

import java.util.Properties;

public class Score {
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");
    private String FontSource;
    private String ScoreMessage;
    private int ScoreFontSize;
    private Font font;
    private int Score_x;
    private int Score_y;
    private int score;

    public Score() {
        FontSource = game_props.getProperty("font");
        ScoreMessage = message_props.getProperty("score");
        ScoreFontSize = Integer.parseInt(game_props.getProperty("score.fontSize"));
        font = new Font(FontSource, ScoreFontSize);
        Score_x = Integer.parseInt(game_props.getProperty("score.x"));
        Score_y = Integer.parseInt(game_props.getProperty("score.y"));
        score = 0;
    }

    public void updateScore(int score) {
        this.score += score;
    }
    public void resetScore() {
        score = 0;
    }

    public void update(Input input) {
        String scoreText = ScoreMessage + score;
        font.drawString(scoreText, Score_x, Score_y);
    }

}
