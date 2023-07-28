package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.exceptions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SceptreTest {
    @Test
    @DisplayName("Test InvalidActionException for making sceptre without enough materials")
    public void buildSwordIllegalArgumentException() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        dmc.newGame("d_BuildablesTest_BuildSwordIllegalArgumentException",
                "c_BuildablesTest_BuildSwordIllegalArgumentException");
        assertThrows(InvalidActionException.class, () -> dmc.build("sceptre"));
    }

    @Test
    @DisplayName("Test building a sceptre method 1")
    public void buildSceptre1() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_BuildablesTest_BuildSceptre", "c_BuildablesTest_BuildSceptre");

        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // right once to initiate test condition: 1
        res = dmc.tick(Direction.RIGHT);

        // Up once: Pick up Wood
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // Up once: Pick up Key
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "key").size());

        // Up once: Pick up SunStone
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build Sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

    }

    @Test
    @DisplayName("Test building a sceptre method 2")
    public void buildSceptre2() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_BuildablesTest_BuildSceptre", "c_BuildablesTest_BuildSceptre");

        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // right once to initiate test condition: 2
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Pick up Wood
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "wood").size());

        // Pick up Treasure
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "treasure").size());

        // Pick up SunStone
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

    }

    @Test
    @DisplayName("Test building a sceptre method 3")
    public void buildSceptre3() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_BuildablesTest_BuildSceptre", "c_BuildablesTest_BuildSceptre");

        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // right once to initiate test condition: 3
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);
        res = dmc.tick(Direction.RIGHT);

        // Pick up Arrows x2
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        assertEquals(2, TestUtils.getInventory(res, "arrow").size());

        // Pick up SunStone x2
        res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.DOWN);
        assertEquals(2, TestUtils.getInventory(res, "sun_stone").size());

        // Build sceptre
        assertEquals(0, TestUtils.getInventory(res, "sceptre").size());
        res = assertDoesNotThrow(() -> dmc.build("sceptre"));
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "sceptre").size());

    }

}
