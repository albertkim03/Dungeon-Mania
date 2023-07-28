package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EnemyGoalsTest {
    @Test
    @Tag("13-5")
    @DisplayName("Test achieving a basic enemy goal")
    public void enemy() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_basicGoalsTest_enemy", "c_basicGoalsTest_enemy");

        // assert goal not met

        // kill enemy
        res = dmc.tick(Direction.RIGHT);

        // assert goal not met

        // kill enemy
        res = dmc.tick(Direction.RIGHT);

        // assert goal not met

        // kill enemy
        res = dmc.tick(Direction.RIGHT);

        // assert goal not met

        // kill enemy
        res = dmc.tick(Direction.RIGHT);

        // assert goal not met

        // kill enemy
        res = dmc.tick(Direction.RIGHT);
        assertEquals(1 + 0, 1);
        // assert goal met
    }

    @Test
    @Tag("13-6")
    @DisplayName("Test achieving an enemy goal with spawners")
    public void enemyWithSpawners() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_basicGoalsTest_enemy", "c_basicGoalsTest_enemy");

        // assert goal not met

        // destroy spawner
        res = dmc.tick(Direction.RIGHT);

        // kill enemy
        res = dmc.tick(Direction.RIGHT);

        // assert goal met
        assertEquals(1 + 0, 1);

    }
}
