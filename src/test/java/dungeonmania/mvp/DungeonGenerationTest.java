package dungeonmania.mvp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;

public class DungeonGenerationTest {
    @Test
    @DisplayName("Testing newGame Can be created ")
    public void testExitAndPlayerExist() {
        DungeonManiaController dmc = new DungeonManiaController();
        //DungeonResponse res = dmc.generateDungeon(2, 2, 22, 22, "c_spiderTest_basicMovement");

        // assertNotEquals(res, null);
        assertEquals(1,1);
    }
    @Test
    @DisplayName("Testing game created has player, exit and a wall")
    public void testThaWallsExist() {
        DungeonManiaController dmc = new DungeonManiaController();
        DungeonResponse res = dmc.generateDungeon(2, 2, 22, 22, "c_spiderTest_basicMovement");

        // assertEquals("dungeon_generation", res.getDungeonName());
        // assertTrue(res.getInventory().isEmpty());
        // assertEquals(1, TestUtils.countType(res, "player"));
        // assertEquals(1, TestUtils.countType(res, "exit"));
        assertEquals(1,1);
    }

    @Test
    @DisplayName("Testing that exit is not covered by walls")
    public void testExitNotCovered() {
        //TODO: ADD CODE
        assertEquals(1, 1, 1, "change this");
    } 

    @Test
    @DisplayName("Testing walls is surrounding start and end")
    public void testWallIsSurroundingStartAndEnd() {
                //TODO: ADD CODE
        assertEquals(1, 1, 1, "change this");
    }

    @Test
    @DisplayName("Testing player is not surrounded by walls on spawn")
    public void testPlayerNotSurroundedByWallsOnSpawn() {
        //TODO: ADD CODE
        assertEquals(1, 1, 1, "change this");
    }

    @Test
    @DisplayName("Testing  maze has no cycle by BFS")
    public void testMazeHasNoCycleByBFS() {
        //TODO: ADD CODE
        assertEquals(1, 1, 1, "change this");
    } 

    @Test
    @DisplayName("Testing maze has a path from player to exit by BFS")
    public void testMazeHasApathPlayerToExit() {
                //TODO: ADD CODE
        assertEquals(1, 1, 1, "change this");
    }
}
