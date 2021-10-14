package spacegame.app;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.entity.Entity;
import com.almasb.fxgl.entity.EntityFactory;
import com.almasb.fxgl.entity.SpawnData;
import com.almasb.fxgl.entity.Spawns;

import static com.almasb.fxgl.dsl.FXGLForKtKt.entityBuilder;

public class GameEntityFactory implements EntityFactory { //inherits Entity factory from github repo

    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        return entityBuilder()
                .from(data)
                .view("spaceship.png")
                .build();
    }

    @Spawns("asteroid")
    public Entity newAsteroid(SpawnData data) {
        return entityBuilder()
                .from(data)
                .view("asteroid.png")
                .build();
    }

    @Spawns("projectile")
    public Entity newProjectile(SpawnData data) {
        return entityBuilder()
                .from(data)
                .view("projectile.png")
                .build();
    }
}
