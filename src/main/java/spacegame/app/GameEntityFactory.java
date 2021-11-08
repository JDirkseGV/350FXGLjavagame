package spacegame.app;

import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.components.OffscreenCleanComponent;
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

/**
 * Class handles creation of game object entities, such as Asteroids, Player, Background, and Projectiles
 */
public class GameEntityFactory implements EntityFactory { //inherits Entity factory from github repo
    /**
     * Builds a background entity upon calling the spawn("background") method in main()
     * @param data
     * @return Entity
     */
    @Spawns("background")
    public Entity newBackground(SpawnData data) {
        // builds an entity, background, using SpawnData
        // Uses spacebackground.png as a resource
        return entityBuilder()
                .from(data)
                .view("spacebackground.png")
                .build();
    }
    /**
     * Builds a player entity upon calling the spawn("player") method in main()
     * @param data
     * @return Entity
     */
    @Spawns("player")
    public Entity newPlayer(SpawnData data) {
        // builds an entity, player, using SpawnData
        // Uses smallspaceship.png as a resource
        // Creates a bounding box around the png
        // Atachess PlayerComponent
        return entityBuilder()
                .type(EntityType.PLAYER)
                .from(data)
                .viewWithBBox("smallspaceship.png")
                .with(new PlayerComponent())
                .collidable()
                .build();
    }
    /**
     * Builds an asteroid entity upon calling the spawn("asteroid") method in main()
     * @param data
     * @return Entity
     */
    @Spawns("asteroid")
    public Entity newAsteroid(SpawnData data) {
        // Constructs an asteroid from smallasteroid.png with a bounding box
        // Adds a RandomMoveComponent with a bounding Rectangle2D object to each spawned asteroid
        return entityBuilder()
                .type(EntityType.DEBRIS)
                .from(data)
                .viewWithBBox("smallasteroid.png")
                .with(new RandomMoveComponent(new Rectangle2D(0, 0, getAppWidth(), getAppHeight()), 100))
                .collidable()
                .build();
    }
    /**
     * Builds a projectile entity upon calling the spawn("projectile") method in PlayerComponent().shoot()
     * @param data
     * @return Entity
     */
    @Spawns("projectile")
    public Entity newProjectile(SpawnData data) {
        // Point object representing the current direction the png is oriented upon spawn
        Point2D dir = data.get("dir");
        // Creates a projectile entity with projectile.png and bound box
        // Projectile built from ProjectileComponent class, provided with vector dir and speed
        return entityBuilder()
                .type(EntityType.PROJECTILE)
                .from(data)
                .viewWithBBox("projectile.png")
                .with(new ProjectileComponent(dir, 400))
                .with(new OffscreenCleanComponent())
                .collidable()
                .build();
    }
}
