package spacegame.app;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.ProjectileComponent;
import com.almasb.fxgl.dsl.components.RandomMoveComponent;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;
import javafx.geometry.Point2D;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;



import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

public class GameEntityFactory implements EntityFactory { //inherits Entity factory from github repo

    @Spawns("background")
    public Entity newBackground(SpawnData data) {
        return entityBuilder()
                .from(data)
                .view("spacebackground.png")
                .with(new PlayerComponent())
                .build();
    }

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return entityBuilder()
                .from(data)
                .viewWithBBox("spaceship.png")
                .with(new PlayerComponent())
                .build();
    }

    @Spawns("asteroid")
    public Entity newAsteroid(SpawnData data) {
        return entityBuilder()
                .from(data)
                .viewWithBBox("asteroid.png")
                .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), getAppHeight()), 100))
                .build();
    }

    @Spawns("projectile")
    public Entity newProjectile(SpawnData data) {
        Point2D dir = data.get("dir");

        return entityBuilder()
                .from(data)
                .viewWithBBox("projectile.png")
                .with(new ProjectileComponent(dir, 500))
                .build();
    }
}
