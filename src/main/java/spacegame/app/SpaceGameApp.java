package spacegame.app;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.profile.DataFile;
import com.almasb.fxgl.profile.SaveLoadHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.text.*;
import javafx.scene.text.Text;

import javafx.util.Duration;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.core.serialization.Bundle;
import com.almasb.fxgl.profile.DataFile;
import com.almasb.fxgl.profile.SaveLoadHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.util.EnumSet;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

import java.util.EnumSet;
import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * Main game application class
 */

public class SpaceGameApp extends GameApplication{
    private static final Object SAVE_FILE_NAME = "SpaceGameSave"; //This class inherits functions from GameApplication from library

    private Entity player;


    /**
     * Initializes game variables. score, lives, etc
     * @param vars map containing global vars
     */
    protected void initGameVars(Map<String, Object> vars) {

        vars.put("highScore", 0);
        vars.put("time", 0.0);
        vars.put("score", 0);
        vars.put("lives", 3);
    }


    /**
     * Initializes game settings, like view window width and height, game name, and version
     * Protected
     * @param settings
     */
    @Override
    protected void initSettings(GameSettings settings){ //overrides to use these settings that defines the game window
        settings.setWidth(2000);
        settings.setHeight(1000);
        //settings.setUserProfileEnabled(true);
        settings.setEnabledMenuItems(EnumSet.allOf(MenuItem.class));
        settings.setMenuEnabled(true);
        //settings.setMe
        //settings.setTicksPerSecond(60);
        settings.setTitle("Space Game");
        settings.setVersion("0.2");
    }

    /**
     * Initializes game inputs for player entity control
     * Protected
     */
    @Override
    protected void initInput() { // Initialize key controls
        // Turn Right
        onKey(KeyCode.D, () -> player.getComponent(PlayerComponent.class).rotateRight());
        // Turn Left
        onKey(KeyCode.A, () -> player.getComponent(PlayerComponent.class).rotateLeft());
        // Move forward
        onKey(KeyCode.W, () -> player.getComponent(PlayerComponent.class).move());
        // Speed boost
        onKey(KeyCode.C, () -> player.getComponent(PlayerComponent.class).move());
        // Strafe Left
        onKey(KeyCode.LEFT, () -> player.getComponent(PlayerComponent.class).moveLeft());
        //Strafe Right
        onKey(KeyCode.RIGHT, () -> player.getComponent(PlayerComponent.class).moveRight());
        // Handle firing projectiles
        onKeyDown(KeyCode.SPACE, () -> player.getComponent(PlayerComponent.class).shoot());
    }


    /**
     * Initializes game. Calls getGameWorld(), spawns background, player entity, and asteroids
     */
    @Override
    protected void initGame() {
        run(() -> inc("time", 1.0), Duration.seconds(1.0));
        getSettings().setGlobalSoundVolume(0.1);
        getGameWorld().addEntityFactory(new GameEntityFactory()); //these both use the FXGL static import
        // Spawns background
        spawn("background");
        // Spawns player spaceship
        player = spawn("player", (getAppWidth()/2)-(64),(getAppHeight()/2)-(64));
        // Spawns asteroids in set locations every 5 seconds
        // run(() -> spawn("asteroid", 321, 100), Duration.seconds(5));
        //run(() -> spawn("asteroid", 1700, 700), Duration.seconds(5));
        run(() -> {
            Entity a = getGameWorld().create("asteroid", new SpawnData(321, 100));
            spawnWithScale(a, Duration.seconds(.5));
        }, Duration.seconds(5));

        run(() -> {
            Entity b = getGameWorld().create("asteroid", new SpawnData(1700, 700));
            spawnWithScale(b, Duration.seconds(.5));
        }, Duration.seconds(4.5));
    }

    /**
     *  sets up collisions and their logic
     */
    @Override
    protected void initPhysics(){

        onCollisionBegin(EntityType.PROJECTILE, EntityType.DEBRIS, (projectile, debris) -> { //if bullet and asteroid collide, remove both
            spawn("scoreText", new SpawnData(debris.getX(), debris.getY()).put("text", "+100"));
            killDebris(debris);
            projectile.removeFromWorld();
            inc("score", +100);
        });

        onCollisionBegin(EntityType.PLAYER, EntityType.DEBRIS, (projectile, debris) -> { //if bullet and asteroid collide, remove both
            killDebris(debris);
            inc("lives", -1);
            if(geti("lives") <= 0){
                gameOver();
            }

            player.setPosition(getAppWidth()/2, getAppHeight()/2);
        });

        }

    private void killDebris(Entity debris) {
        spawn("explosion", debris.getPosition());
        debris.removeFromWorld();
    }

    /**
     *  method containing all UI
     */
    @Override
    protected void initUI() {
        String name = "";
        try{
            SaveData data = (SaveData) ResourceManager.load("1.save");
            inc("highScore", +(data.highScore));
            name = data.name;

        }
        catch (Exception e){
            System.out.println("Couldn't load save data: " + e.getMessage());
        }

        var nameText = getUIFactoryService().newText("", Color.WHITE, 24.0);
        nameText.setTranslateX(1600);
        nameText.setTranslateY(65);
        nameText.textProperty().setValue(name + "'s");

        var hscoreText = getUIFactoryService().newText("", Color.WHITE, 24.0);
        hscoreText.setTranslateX(1600);
        hscoreText.setTranslateY(100);
        hscoreText.textProperty().bind(getip("highScore").asString("Highscore: %d"));

        var time = getUIFactoryService().newText("", Color.WHITE, 32.0);
        time.textProperty().bind(getdp("time").asString());
        addUINode(time, 950, 50);

        Text scoreText = getUIFactoryService().newText("", Color.GOLD, 28);
        scoreText.setTranslateX(60);
        scoreText.setTranslateY(70);
        scoreText.textProperty().bind(getip("score").asString());
        scoreText.setStroke(Color.GOLD);

        var livesText = getUIFactoryService().newText("", Color.WHITE, 24.0);
        livesText.setTranslateX(60);
        livesText.setTranslateY(110);
        livesText.textProperty().bind(getip("lives").asString("Lives: %d"));

        getGameScene().addUINodes(scoreText, livesText, hscoreText, nameText);

        //addVarText("score", 50, 50); //adds score counter to game
        //addVarText("lives", 50, 70);


    }
    private void gameOver() {
        getGameController().gotoMainMenu();
        if(geti("highScore") < geti("score")){
        getDialogService().showInputBox("Your score:" + geti("score") + "\nEnter your name", s -> s.matches("[a-zA-Z]*"), name -> {

            SaveData data = new SaveData();


                data.name = name;
                data.highScore =  geti("score");
                try{
                    ResourceManager.save(data, "1.save");
                }
                catch (Exception e){
                    System.out.println("Couldn't save: " + e.getMessage());
                }

        });
        }
        else{
            getDialogService().showMessageBox("You died and didn't get the high score, better luck next time!");
            }

    }



        public static void main(String[] args){
        // runs app
        launch(args);
    }
}
