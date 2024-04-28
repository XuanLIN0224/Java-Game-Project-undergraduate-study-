import bagel.*;

import java.util.Properties;

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
    private Properties game_props = IOUtils.readPropertiesFile("res/app.properties");
    private int windowHeight = Integer.parseInt(game_props.getProperty("windowHeight"));
    private ArrayList <Coin> coins = new ArrayList<Coin>();
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private ArrayList<FlyingPlatform> flyingPlatforms = new ArrayList<FlyingPlatform>();
    private ArrayList<DoubleScorePower> doubleScorePowers = new ArrayList<DoubleScorePower>();
    private ArrayList<InvinciblePower> invinciblePowers = new ArrayList<InvinciblePower>();
    private ArrayList<Fireball> fireballsPlayer = new ArrayList<Fireball>();
    private ArrayList<Fireball> fireballsEnemy = new ArrayList<Fireball>();
    private final Image BACKGROUND_IMAGE;
    private Player player;
    private EndFlag endFlag;
    private Platform platform;
    private EnemyBoss enemyBoss;
    private Score score = new Score();
    private PlayerHealth playerHealth = new PlayerHealth();
    private enemyBossHealth enemyBossHealth = new enemyBossHealth();
    private Win win = new Win();
    private boolean WonGame = false;
    private boolean GiveInstruction = true;
    private Title title = new Title();
    private GameOver gameOver = new GameOver();
    private boolean isGameOver = false;
    private StartInstruction startInstruction = new StartInstruction();
    private String fileName;
    private boolean isInvinciblePowerActive = false;
    private boolean isDoubleScorePowerActive = false;
    private int activeFramesInvincible = 0;
    private int activeFramesDoubleScore = 0;
    private boolean isFallingFromPlatform = false;

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
        fireballsPlayer.clear();
        fireballsEnemy.clear();
        playerHealth.resetHealth();
        score.resetScore();
        if (enemyBoss != null){
            enemyBoss.resetObject();
        }
        enemyBossHealth.resetHealth();
        player = null;
        endFlag = null;
        enemyBoss = null;
        platform = null;
        GiveInstruction = false;
        isGameOver = false;
        WonGame = false;
    }

    public boolean isCollideWithPlayer(double x, double y, double radius){
        return Math.sqrt(Math.pow(player.getX() - x, 2) +
                Math.pow(player.getY() - y, 2)) <= player.getRADIUS() + radius;
    }
    public boolean isCollideWithBoss(double x, double y, double radius){
        return Math.sqrt(Math.pow(enemyBoss.getX() - x, 2) +
                Math.pow(enemyBoss.getY() - y, 2)) <= enemyBoss.getRADIUS() + radius;
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
                startInstruction.update();
                title.update();
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
                if (isCollideWithPlayer(endFlag.getX(), endFlag.getY(), endFlag.getRADIUS())) {
                    WonGame = true;
                }
                score.update();

                //if collide with a coin, update score
                for (Coin coin : coins){
                    coin.update(input);
                    if (isCollideWithPlayer(coin.getX(), coin.getY(), coin.getRADIUS())) {
                        coin.setVerticalMoveSpeed(-10);
                        if (isDoubleScorePowerActive){
                            score.updateScore(coin.getValue() * 2);
                        }
                        else {
                            score.updateScore(coin.getValue());
                        }
                        coin.setValue(0);
                    }
                }
                //if collide with an enemy, update health
                for (Enemy enemy : enemies){
                    enemy.update((input));
                    if (isCollideWithPlayer(enemy.getX(), enemy.getY(), enemy.getRADIUS())) {
                        if (!isInvinciblePowerActive){
                            playerHealth.updateHealth(enemy.getDamage());
                            System.out.println(playerHealth.getHealth() +"," + enemy.getDamage());
                            enemy.setDamage(0);
                        }
                    }
                }
                if (isDoubleScorePowerActive){
                    activeFramesDoubleScore++;
                    if (activeFramesDoubleScore > 500){
                        isDoubleScorePowerActive = false;
                        activeFramesDoubleScore = 0;
                    }
                }
                //if collide with a DoubleScorePower
                for (DoubleScorePower doubleScorePower : doubleScorePowers){
                    doubleScorePower.update((input));
                    if (isCollideWithPlayer(doubleScorePower.getX(), doubleScorePower.getY(), doubleScorePower.getRadius())) {
                        doubleScorePower.setVerticalSpeed(-10);
                        isDoubleScorePowerActive = true;
                    }
                }
                if (isInvinciblePowerActive){
                    activeFramesInvincible++;
                    if (activeFramesInvincible > 500){
                        isInvinciblePowerActive = false;
                        activeFramesInvincible = 0;
                    }
                }
                //if collide with a InvinciblePower
                for (InvinciblePower invinciblePower: invinciblePowers){
                    invinciblePower.update((input));
                    if (isCollideWithPlayer(invinciblePower.getX(), invinciblePower.getY(),
                            invinciblePower.getRadius())) {
                        invinciblePower.setVerticalSpeed(-10);
                        isInvinciblePowerActive = true;
                    }
                }
                //show flying platforms and make player stand on flying platforms
                for (FlyingPlatform flyingPlatform: flyingPlatforms){
                    flyingPlatform.update((input));
                    //check if the player lands on flying platforms
                    if ((Math.abs(player.getX() - flyingPlatform.x) < flyingPlatform.getHalfLength()) &&
                            (Math.abs(player.getY() - flyingPlatform.y) <= flyingPlatform.getHalfHeight()) &&
                            (Math.abs(player.getY() - flyingPlatform.y) >= flyingPlatform.getHalfHeight() - 1)
                            && !isFallingFromPlatform) {
                        //place the player on the platform
                        if (!player.isOnFlyingPlatform()){
                            player.setVerticalSpeed(0);
                            player.setOnFlyingPlatform(true);
                            player.setJumping(false);
                            flyingPlatform.setPlayerOn(true);
                            isFallingFromPlatform = false;
                        }
                    }
                    //when the player falls from the flying platforms
                    else if ((Math.abs(player.getX() - flyingPlatform.x) > flyingPlatform.getHalfLength()) &&
                            (Math.abs(player.getY() - flyingPlatform.y) <= flyingPlatform.getHalfHeight()) &&
                            (Math.abs(player.getY() - flyingPlatform.y) >= flyingPlatform.getHalfHeight() - 1) &&
                        flyingPlatform.isPlayerOn()){
                        player.setOnFlyingPlatform(false);
                        flyingPlatform.setPlayerOn(false);
                        player.setJumping(true);
                        isFallingFromPlatform = true;
                    }
                }
                if (player.isOnGround()){
                    isFallingFromPlatform = false;
                }
                //shooting fireBall
                if ((enemyBoss != null) && player.isShootFireBall() && enemyBoss.isInActivationRadius(player)){
                    player.setShootFireBall(false);
                    if (player.isTurnLeft()){
                        fireballsPlayer.add(new Fireball(player.x, player.y, -1));
                    }
                    else {
                        fireballsPlayer.add(new Fireball(player.x, player.y, 1));
                    }
                }
                for (Fireball fireball : fireballsPlayer){
                    fireball.update();
                    if (isCollideWithBoss(fireball.getX(), fireball.getY(), fireball.getRADIUS())) {
                        fireball.setActive(false);
                        enemyBossHealth.updateHealth(fireball.getDamageSize());
                    }
                    if ((fireball.getX() > Window.getWidth()) || (fireball.getX() < 0)){
                        fireball.setActive(false);
                    }
                }
                if (enemyBoss != null){
                    enemyBoss.isInActivationRadius(player);
                }
                if (enemyBoss != null && enemyBoss.isShootFireBall()){
                    System.out.println("SHoot Player");
                    fireballsEnemy.add(new Fireball(enemyBoss.x, enemyBoss.y, -1));
                    enemyBoss.setShootFireBall(false);
                }
                for (Fireball fireball : fireballsEnemy){
                    fireball.update();
                    if (isCollideWithPlayer(fireball.getX(), fireball.getY(), fireball.getRADIUS())) {
                        fireball.setActive(false);
                        playerHealth.updateHealth(fireball.getDamageSize());
                    }
                    if ((fireball.getX() > Window.getWidth()) || (fireball.getX() < 0)){
                        fireball.setActive(false);
                    }
                }

                playerHealth.update();
                if (fileName.equals(game_props.getProperty("level3File"))){
                    enemyBossHealth.update();
                }

                //if health is less or equal to 0, game over and set the player to dead
                if (playerHealth.getHealth() <= 0){
                    player.setDead();
                    for (Coin coin : coins) {
                        coin.setPlayerDead();
                    }
                    for (Enemy enemy : enemies) {
                        enemy.setPlayerDead();
                    }
                    endFlag.setPlayerDead();
                    platform.setPlayerDead();
                    enemyBoss.setPlayerDead();
                    if (player.getY() > windowHeight){
                        isGameOver = true;
                    }
                    for (FlyingPlatform flyingPlatform : flyingPlatforms){
                        flyingPlatform.setPlayerDead();
                    }
                    for (InvinciblePower invinciblePower : invinciblePowers){
                        invinciblePower.setPlayerDead();
                    }
                    for (DoubleScorePower doubleScorePower : doubleScorePowers){
                        doubleScorePower.setPlayerDead();
                    }
                }
                if ((enemyBoss != null) && (enemyBossHealth.getHealth() <= 0)){
                    enemyBoss.setDead();
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
                        gameOver.update();
                        if (input.wasPressed(Keys.SPACE)) {
                            GiveInstruction = true;
                        }
                    }
                }
                //when winning the game
                else {
                    win.update();
                    if (input.wasPressed(Keys.SPACE)){
                        GiveInstruction = true;
                    }
                }
            }
            //reset the game entities and restart the game
            else {
                startInstruction.update();
                title.update();
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
