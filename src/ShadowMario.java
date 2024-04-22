import bagel.*;

import java.util.Properties;
import java.io.File;
import java.util.Scanner;
import bagel.Image;
import java.util.ArrayList;

import static javax.swing.plaf.basic.BasicGraphicsUtils.drawString;

/**
 * Skeleton Code for SWEN20003 Project 1, Semester 1, 2024
 *
 * Please enter your name below
 * @xulin2
 */
public class ShadowMario extends AbstractGame {
    Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    int windowHeight = Integer.parseInt(game_props.getProperty("windowHeight"));
    ArrayList <Coin> coins = new ArrayList<Coin>();
    int countCoins = 0;
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    int countEnemies = 0;
    private final Image BACKGROUND_IMAGE;
    private Player player;
    private EndFlag endFlag;
    private Platform platform;
    private Score score = new Score();
    private Health health = new Health();
    private Win win = new Win();
    private boolean WonGame = false;
    private boolean GiveInstruction = true;
    private Title title = new Title();
    private GameOver gameOver = new GameOver();
    private boolean isGameOver = false;
    private StartInstruction startInstruction = new StartInstruction();

    /**
     * The constructor
     */
    public ShadowMario(Properties game_props, Properties message_props) {
        super(Integer.parseInt(game_props.getProperty("windowWidth")),
              Integer.parseInt(game_props.getProperty("windowHeight")),
              message_props.getProperty("title"));

        BACKGROUND_IMAGE = new Image(game_props.getProperty("backgroundImage"));

        // you can initialise other values from the property files here
        // I prefer initialise them in the main function and it works fine
    }
    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
        Properties message_props = IOUtils.readPropertiesFile("res/message_en.properties");
        //initialising the game
        ShadowMario game = new ShadowMario(game_props, message_props);
        String fileName = game_props.getProperty("level1File");
        //create a 2D array 'data' to store and initialise the positions for all game objects
        int dataLength = IOUtils.countLines(fileName);
        String[][] data = IOUtils.readCsv(fileName);
        for (int i = 0; i < dataLength; i++){
            String entityType = data[i][0];
            double x = Double.parseDouble(data[i][1]);
            double y = Double.parseDouble(data[i][2]);
            switch (entityType) {
                case "PLATFORM":
                    game.platform = new Platform(x, y);
                    break;
                case "PLAYER":
                    game.player = new Player(x, y);
                    break;
                case "COIN":
                    game.coins.add(new Coin(x, y));
                    game.countCoins += 1;
                    break;
                case "ENEMY":
                    game.enemies.add(new Enemy(x, y));
                    game.countEnemies += 1;
                    break;
                case "END_FLAG":
                    game.endFlag = new EndFlag(x, y);
                    break;
            }
        }
        game.run();
    }

    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */
    @Override
    protected void update(Input input) {
        //while the game is not finished
        if (!WonGame && !isGameOver){
            // close window
            if (input.wasPressed(Keys.ESCAPE)){
                Window.close();
            }
            //before starting the game, show the title and instruction
            BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);
            if (GiveInstruction){
                startInstruction.update(input);
                title.update(input);
                if (input.wasPressed(Keys.SPACE)){
                    GiveInstruction = false;
                }
            }
            else{
                //start the game
                platform.update(input);
                player.update(input);
                endFlag.update(input);
                //if collide with EndFlag, win the game
                if (player.getX() < endFlag.getX_boundary()&&
                        player.getX_boundary() > endFlag.getX() &&
                        player.getY() > endFlag.getY_boundary() &&
                        player.getY_boundary() > endFlag.getY()) {
                    WonGame = true;
                }
                score.update(input);

                //if collide with a coin, update score
                for (Coin coin : coins){
                    coin.update((input));
                    if (player.getX() < coin.getX_boundary()&&
                            player.getX_boundary() > coin.getX() &&
                            player.getY() < coin.getY_boundary() &&
                            player.getY_boundary() > coin.getY()) {
                        coin.setVerticalMoveSpeed(-10);
                        score.updateScore(coin.getValue());
                        coin.setValue(0);
                    }
                }
                //if collide with an enemy, update health
                for (Enemy enemy : enemies){
                    enemy.update((input));
                    if (player.getX() < enemy.getX_boundary()&&
                            player.getX_boundary() > enemy.getX() &&
                            player.getY() < enemy.getY_boundary() &&
                            player.getY_boundary() > enemy.getY()) {
                        health.updateHealth(enemy.getDamage());
                        enemy.setDamage(0);
                    }
                }
                health.update(input);
                //if health is less or equal to 0, game over and set the player to dead
                if (health.getHealth() <= 0){
                    player.setDead();
                    for (Coin coin : coins) {
                        coin.setPlayerDead();
                    }
                    for (Enemy enemy : enemies) {
                        enemy.setPlayerDead();
                    }
                    endFlag.setPlayerDead();
                    platform.setPlayerDead();
                    if (player.getY() > windowHeight){
                        isGameOver = true;
                    }
                }
            }
        }
        else {
            if (input.wasPressed(Keys.ESCAPE)) {
                Window.close();
            }
            if (!GiveInstruction){
                //when game over
                if (isGameOver){
                    if (player.getY() >= windowHeight) {
                        gameOver.update(input);
                        if (input.wasPressed(Keys.SPACE)) {
                            GiveInstruction = true;
                        }
                    }
                }
                //when winning the game
                else {
                    win.update(input);
                    if (input.wasPressed(Keys.SPACE)){
                        GiveInstruction = true;
                    }
                }
            }
            //reset the game entities and restart the game
            else {
                startInstruction.update(input);
                title.update(input);
                if (input.wasPressed(Keys.SPACE)){
                    score.resetScore();
                    endFlag.resetObject();
                    platform.resetObject();
                    player.resetObject();
                    for (Coin coin : coins) {
                        coin.resetObject();
                    }
                    for (Enemy enemy : enemies) {
                        enemy.resetObject();
                    }
                    health.resetHealth();
                    GiveInstruction = false;
                    isGameOver = false;
                    WonGame = false;
                }
            }
        }
    }
}
