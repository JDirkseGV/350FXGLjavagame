package spacegame.app;

import com.almasb.fxgl.entity.GameWorld;
import org.junit.Test;

public class PlayerComponentTest {

    @Test
    public void testLives (){
            var playerComponent = new PlayerComponent();
            assert(playerComponent.startLives());
    }

    @Test
    public void testStartScore(){
        var playerComponent = new PlayerComponent();
        assert(playerComponent.startScore());
    }

}
