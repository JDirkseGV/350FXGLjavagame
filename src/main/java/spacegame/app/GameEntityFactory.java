package spacegame.app;

import com.almasb.fxgl.animation.Interpolators;
import com.almasb.fxgl.dsl.FXGL;
import com.almasb.fxgl.dsl.FXGLForKtKt;
import com.almasb.fxgl.dsl.components.ExpireCleanComponent;
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
import javafx.util.Duration;
import com.almasb.fxgl.dsl.AnimationBuilder;
import javafx.animation.Interpolator;
import com.almasb.fxgl.animation.EasingInterpolator;
import com.almasb.fxgl.animation.Interpolators;


import static com.almasb.fxgl.dsl.FXGLForKtKt.*;

/**
 * Class handles creation of game object entities, such as Asteroids, Player, Background, and Projectiles
 */
public class GameEntityFactory implements EntityFactory { //inherits Entity factory from github repo
    /**
     * Builds a background entity upon calling the spawn("background") method in main()
     * @param data SpawnData
     * @return Entity
     */
    @Spawns("background")
    public Entity newBackground(SpawnData data) {
        // builds an entity, background, using SpawnData
        // Uses spacebackground.png as a resource
        return entityBuilder()
                .from(data)
                .view("openspace.jpg")
                .build();
    }
    /**
     * Builds a player entity upon calling the spawn("player") method in main()
     * @param data SpawnData
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
     * @param data SpawnData
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
                .with(new RandomMoveComponent(new Rectangle2D(-960, -540, 3840, 2160), 200))
                .collidable()
                .build();
    }

    /**
     * Creates an Upgrade entity that looks like a heart. Gives
     * player life and projectile upgrade.
     * @param data SpawnData
     * @return Entity gunUpgrade entity
     */
    @Spawns("gunUpgrade")
    public Entity newGunUpgrade(SpawnData data) {
        // Constructs an asteroid from smallasteroid.png with a bounding box
        // Adds a RandomMoveComponent with a bounding Rectangle2D object to each spawned asteroid
        return entityBuilder()
                .type(EntityType.UPGRADE)
                .from(data)
                .viewWithBBox("lives.png")
                .with(new RandomMoveComponent(new Rectangle2D(-960, -540, 2880, 1620), 50))
                .collidable()
                .build();
    }
    /**
     * Builds a projectile entity upon calling the spawn("projectile") method in PlayerComponent().shoot()
     * @param data SpawnData
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
                .with(new ProjectileComponent(dir, 800))
                .with(new OffscreenCleanComponent())
                .collidable()
                .build();
    }

    /**
     * Creates a new Explosion entity
     * @param data SpawnData
     * @return Explosion Entity
     */
    @Spawns("explosion")
    public Entity newExplosion(SpawnData data){
        play("explosion.wav");
        return entityBuilder()
                .from(data)
                .view(texture("explosion.png").toAnimatedTexture(16, Duration.seconds(0.66)).play())
                .with(new ExpireCleanComponent(Duration.seconds(0.66)))
                .build();

    }

    /**
     * Creates a scoreText entity (for destruction of asteroids)
     * @param data SpawnData
     * @return scoreText Entity
     */
    @Spawns("scoreText")
    public Entity newScoreText(SpawnData data){
        String text = data.get("text");


        return entityBuilder()
                .from(data)
                .view(getUIFactory().newText(text, 24))
                .with(new ExpireCleanComponent(Duration.seconds(0.66)))
                .build();

    }
}
