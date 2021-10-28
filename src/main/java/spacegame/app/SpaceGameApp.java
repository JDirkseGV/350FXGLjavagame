package spacegame.app;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import javafx.scene.input.KeyCode;
import javafx.util.Duration;

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
        getGameWorld().addEntityFactory(new GameEntityFactory()); //these both use the FXGL static import
        // Spawns background
        spawn("background");
        // Spawns player spaceship
        player = spawn("player", (getAppWidth()/2)-(64),(getAppHeight()/2)-(64));
        // Spawns asteroids in set locations every 5 seconds
        run(() -> spawn("asteroid", 321, 100), Duration.seconds(5));
        run(() -> spawn("asteroid", 1700, 700), Duration.seconds(5));
    }
    public static void main(String[] args){
        // runs app
        launch(args);
    }
}
