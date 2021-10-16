package spacegame.app;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

/**
 * Extends Componenet, provides methods for control of player entity
 */
public class PlayerComponent extends Component {
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
        entity.rotateBy(2.5);
    }
    /**
     * Rotate player entity to the left 2.5 degrees on method call
     */
    public void rotateLeft() {
        entity.rotateBy(-2.5);
    }
    /**
     * Move player entity in direction it is facing
     */
    public void move() {
        // Grab vector direction sprite is facing
        Vec2 dir = Vec2.fromAngle(entity.getRotation() - 90)
                .mulLocal(2.5);
        // Translate sprite in vector direction
        entity.translate(dir);
    }
    /**
     * Apply slow left strafing movement to player entity
     */
    public void moveLeft() {
        // Grab vector direction sprite is facing
        Vec2 dir = Vec2.fromAngle(entity.getRotation() - 180)
                .mulLocal(1);
        // Translate sprite in vector direction
        entity.translate(dir);
    }
    /**
     * Apply slow right strafing movement to player entity
     */
    public void moveRight() {
        // Grab vector direction sprite is facing
        Vec2 dir = Vec2.fromAngle(entity.getRotation())
                .mulLocal(1);
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
