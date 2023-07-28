package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import dungeonmania.exceptions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class MidnightArmourTest {
    @Test
    @DisplayName("Test InvalidActionException for making MidnightArmour without enough materials")
    public void buildSwordIllegalArgumentException() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        dmc.newGame("d_BuildablesTest_BuildSwordIllegalArgumentException",
                "c_BuildablesTest_BuildSwordIllegalArgumentException");
        assertThrows(InvalidActionException.class, () -> dmc.build("midnight_armour"));
    }

    @Test
    @DisplayName("Test building a midnight_armour method 1")
    public void buildSceptre2() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_BuildablesTest_BuildMidnightArmour", "c_BuildablesTest_BuildSceptre");

        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());

        // right once to initiate test condition: 2
        res = dmc.tick(Direction.RIGHT);

        // Pick up Wood
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "sword").size());

        // Pick up Treasure
        res = dmc.tick(Direction.DOWN);
        assertEquals(1, TestUtils.getInventory(res, "sun_stone").size());

        // Build midnightArmour
        assertEquals(0, TestUtils.getInventory(res, "midnight_armour").size());
        res = assertDoesNotThrow(() -> dmc.build("midnight_armour"));
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());

        // Materials used in construction disappear from inventory
        assertEquals(0, TestUtils.getInventory(res, "wood").size());
        assertEquals(0, TestUtils.getInventory(res, "arrow").size());
        assertEquals(0, TestUtils.getInventory(res, "key").size());
        assertEquals(0, TestUtils.getInventory(res, "treasure").size());
        assertEquals(0, TestUtils.getInventory(res, "sun_stone").size());
        assertEquals(1, TestUtils.getInventory(res, "midnight_armour").size());
    }

}
