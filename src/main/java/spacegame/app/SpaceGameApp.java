package spacegame.app;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.app.MenuItem;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.SpawnData;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

import java.util.Map;

import static com.almasb.fxgl.dsl.FXGL.*;

/**
 * Main game application class
 */
public class SpaceGameApp extends GameApplication{ //This class inherits functions from GameApplication from library

    private Entity player;

    /**
     * Initializes game settings, like view window width and height, game name, and version
     * Protected
     * @param settings
     */
    @Override
    protected void initSettings(GameSettings settings){ //overrides to use these settings that defines the game window
        settings.setWidth(2000);
        settings.setHeight(1000);
        settings.setMenuEnabled(true);
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
     * Initializes game variables. score, lives, etc
     * @param vars map containing global vars
     */
    protected void initGameVars(Map<String, Object> vars) {

        vars.put("score", 0);
        vars.put("lives", 3);
    }

    /**
     * Initializes game. Calls getGameWorld(), spawns background, player entity, and asteroids
     */
    @Override
    protected void initGame() {
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
        addVarText("score", 50, 50); //adds score counter to game
        addVarText("lives", 50, 70);
    }

        public static void main(String[] args){
        // runs app
        launch(args);
    }
}
