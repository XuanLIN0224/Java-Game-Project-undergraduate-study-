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
    ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    ArrayList<FlyingPlatform> flyingPlatforms = new ArrayList<FlyingPlatform>();
    ArrayList<DoubleScorePower> doubleScorePowers = new ArrayList<DoubleScorePower>();
    ArrayList<InvinciblePower> invinciblePowers = new ArrayList<InvinciblePower>();
    private final Image BACKGROUND_IMAGE;
    private Player player;
    private EndFlag endFlag;
    private Platform platform;
    private EnemyBoss enemyBoss;
    private Fireball fireball;
    private Score score = new Score();
    private Health health = new Health();
    private Win win = new Win();
    private boolean WonGame = false;
    private boolean GiveInstruction = true;
    private Title title = new Title();
    private GameOver gameOver = new GameOver();
    private boolean isGameOver = false;
    private StartInstruction startInstruction = new StartInstruction();
    private String fileName;

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

        game.run();
    }
    public void LoadLevel(String fileName){
        //create a 2D array 'data' to store and initialise the positions for all game objects
        int dataLength = IOUtils.countLines(fileName);
        String[][] data = IOUtils.readCsv(fileName);
        for (int i = 0; i < dataLength; i++){
            String entityType = data[i][0];
            double x = Double.parseDouble(data[i][1]);
            double y = Double.parseDouble(data[i][2]);
            switch (entityType) {
                case "ENEMY_BOSS":
                    enemyBoss = new EnemyBoss(x, y);
                    break;
                case "INVINCIBLE_POWER":
                    invinciblePowers.add(new InvinciblePower(x, y));
                    break;
                case "FLYING_PLATFORM":
                    flyingPlatforms.add(new FlyingPlatform(x, y));
                    break;
                case "DOUBLE_SCORE":
                    doubleScorePowers.add(new DoubleScorePower(x, y));
                    break;
                case "PLATFORM":
                    platform = new Platform(x, y);
                    break;
                case "PLAYER":
                    player = new Player(x, y);
                    break;
                case "COIN":
                    coins.add(new Coin(x, y));
                    break;
                case "ENEMY":
                    enemies.add(new Enemy(x, y));
                    break;
                case "END_FLAG":
                    endFlag = new EndFlag(x, y);
                    break;
            }
        }
    }

    public void resetGame(){
        coins.clear();
        enemies.clear();
        health.resetHealth();
        score.resetScore();
        player = null;
        endFlag = null;
        enemyBoss = null;
        platform = null;
        GiveInstruction = false;
        isGameOver = false;
        WonGame = false;
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
                if (input.wasPressed(Keys.NUM_1)){
                    fileName = game_props.getProperty("level1File");
                    LoadLevel(fileName);
                    GiveInstruction = false;
                }
                else if (input.wasPressed(Keys.NUM_2)){
                    fileName = game_props.getProperty("level2File");
                    LoadLevel(fileName);
                    GiveInstruction = false;
                }
                else if (input.wasPressed(Keys.NUM_3)){
                    fileName = game_props.getProperty("level3File");
                    LoadLevel(fileName);
                    GiveInstruction = false;
                }
            }
            else{
                //start the game
                platform.update(input);
                player.update(input);
                endFlag.update(input);
                if (enemyBoss!=null){
                    enemyBoss.update(input);
                }

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
                //if collide with a DoubleScorePower
                for (DoubleScorePower doubleScorePower : doubleScorePowers){
                    doubleScorePower.update((input));
                    if (player.getX() < doubleScorePower.getX_boundary()&&
                            player.getX_boundary() > doubleScorePower.getX() &&
                            player.getY() < doubleScorePower.getY_boundary() &&
                            player.getY_boundary() > doubleScorePower.getY()) {
                        doubleScorePower.setVerticalSpeed(-10);
                        doubleScorePower.setActive();
                    }
                }
                //if collide with a InvinciblePower
                for (InvinciblePower invinciblePower: invinciblePowers){
                    invinciblePower.update((input));
                    if (player.getX() < invinciblePower.getX_boundary()&&
                            player.getX_boundary() > invinciblePower.getX() &&
                            player.getY() < invinciblePower.getY_boundary() &&
                            player.getY_boundary() > invinciblePower.getY()) {
                        invinciblePower.setVerticalSpeed(-10);
                        invinciblePower.setActive();
                    }
                }
                //show flying platforms and make player stand on flying platforms
                for (FlyingPlatform flyingPlatform: flyingPlatforms){
                    flyingPlatform.update((input));
                    //check if the player lands on flying platforms
                    if ((Math.abs(player.getX() - flyingPlatform.x) < flyingPlatform.getHalfLength()) &&
                            (Math.abs(player.getY() - flyingPlatform.y) <= flyingPlatform.getHalfHeight()) &&
                            (Math.abs(player.getY() - flyingPlatform.y) >= flyingPlatform.getHalfHeight() - 1)) {
                        //place the player on the platform
                        if (!player.isOnFlyingPlatform()){
                            player.setVerticalSpeed(0);
                            player.setOnFlyingPlatform(true);
                            player.setJumping(false);
                            flyingPlatform.setPlayerOn(true);
                        }
                    }
                    //when the player falls from the flying platforms
                    else if ((Math.abs(player.getX() - flyingPlatform.x) > flyingPlatform.getHalfLength()) &&
                        flyingPlatform.isPlayerOn()){
                        player.setOnFlyingPlatform(false);
                        flyingPlatform.setPlayerOn(false);
                        player.setJumping(true);
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
                if (input.wasPressed(Keys.NUM_1)){
                    resetGame();
                    fileName = game_props.getProperty("level1File");
                    LoadLevel(fileName);
                }
                else if (input.wasPressed(Keys.NUM_2)){
                    resetGame();
                    fileName = game_props.getProperty("level2File");
                    LoadLevel(fileName);
                }
                else if (input.wasPressed(Keys.NUM_3)){
                    resetGame();
                    fileName = game_props.getProperty("level3File");
                    LoadLevel(fileName);
                }
            }
        }
    }
}
