package spacegame.app;

import com.almasb.fxgl.core.math.Vec2;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.component.Component;
import javafx.geometry.Point2D;

import static com.almasb.fxgl.dsl.FXGLForKtKt.spawn;

public class PlayerComponent extends Component {

    @Override
    public void onAdded() {
        entity.getTransformComponent().setScaleOrigin(entity.getCenter());
    }
    public void rotateRight() {
        entity.rotateBy(4);
    }
    public void rotateLeft() {
        entity.rotateBy(-4);
    }
    public void move() {
        // Grab vector direction sprite is facing
        Vec2 dir = Vec2.fromAngle(entity.getRotation() - 90)
                .mulLocal(4);
        // Translate sprite in vector direction
        entity.translate(dir);
    }
    public void moveLeft() {
        // Grab vector direction sprite is facing
        Vec2 dir = Vec2.fromAngle(entity.getRotation() - 180)
                .mulLocal(1);
        // Translate sprite in vector direction
        entity.translate(dir);
    }
    public void moveRight() {
        // Grab vector direction sprite is facing
        Vec2 dir = Vec2.fromAngle(entity.getRotation())
                .mulLocal(1);
        // Translate sprite in vector direction
        entity.translate(dir);
    }
    public void shoot() {
        Point2D center = entity.getCenter();

        Vec2 dir = Vec2.fromAngle(entity.getRotation() - 90);

        spawn("projectile", new SpawnData(center.getX() - 50, center.getY() - 16).put("dir", dir.toPoint2D()));
    }
}
