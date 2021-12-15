package spacegame.app;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;



/**
 * Extends Component, provides methods for control of player entity
 */
public class PlayerComponent extends Component {

    private int lives = 5;
    private int initialScore = 0;

    public int getInitialScore() {
        return initialScore;
    }

    public void setInitialScore(int initialScore) {
        this.initialScore = initialScore;
    }
    public boolean startScore(){
        return initialScore == 0;
    }

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public boolean startLives(){
        return lives == 5;
    }

    /**
     * On entity spawn, set the origin to the center of the entity
     */
    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(entity.getCenter());
    }
    /**
     * Rotate player entity to the right 2.5 degrees on method call
     */
    public void rotateRight() {
        entity.rotateBy(5);
    }
    /**
     * Rotate player entity to the left 2.5 degrees on method call
     */
    public void rotateLeft() {
        entity.rotateBy(-5);
    }
    /**
     * Move player entity in direction it is facing
     */
    public void move() {
        // Grab vector direction sprite is facing
        Vec2 dir = Vec2.fromAngle(entity.getRotation() - 90)
                .mulLocal(5);
        // Translate sprite in vector direction
        entity.translate(dir);
    }

    /**
     * Fire a projectile in direction player entity is facing, calls spawn() from
     * GameEntityFactory
     */
    public void shoot() {
        // Save a point object of the player entity's center
        Point2D center = entity.getCenter();
        // Grabs and stores a vector direction for the projectile
        Vec2 dir = Vec2.fromAngle(entity.getRotation() - 90);
        // Finally, spawns in a projectile on player entity center, subtracting half the pixel size of the png.
        spawn("projectile", new SpawnData(center.getX() - 50, center.getY() - 16).put("dir", dir.toPoint2D()));
    }
}
