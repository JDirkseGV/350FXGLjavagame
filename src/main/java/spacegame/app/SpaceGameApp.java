package spacegame.app;

import com.almasb.fxgl.app.GameApplication;
import com.almasb.fxgl.app.GameSettings;
import com.almasb.fxgl.dsl.FXGL;

import static com.almasb.fxgl.dsl.FXGL.*;

public class SpaceGameApp extends GameApplication{ //This class inherits functions from GameApplication from library

    @Override
    protected void initSettings(GameSettings settings){ //overrides to use these settings that defines the game window
        settings.setWidth(2000);
        settings.setHeight(1000);
        settings.setTitle("Space Game");
        settings.setVersion("0.1");
    }
    @Override
    protected void initGame() {
        getGameWorld().addEntityFactory(new GameEntityFactory()); //these both use the FXGL static import
        spawn("player", (getAppWidth()/2)-(128),(getAppHeight()/2)-(128));
    }
    public static void main(String[] args){ //runs app
        launch(args);
    }
}
