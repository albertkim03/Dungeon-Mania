package dungeonmania.mvp;

import dungeonmania.DungeonManiaController;
import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.util.Direction;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class LogicTest {
    @Test
    @DisplayName("Test LightBulb exist")
    public void lightBulbExist() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logics_entities", "c_logic_rule_or");
        assertEquals(7, TestUtils.getEntities(res, "light_bulb_off").size());
    }

    @Test
    @DisplayName("Test SwitchDoor exist")
    public void switchDoorExist() {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logics_entities", "c_logic_rule_or");
        assertEquals(1, TestUtils.getEntities(res, "switch_door").size());
    }

    @Test
    @DisplayName("Test LightBulbOn OR")
    public void wireLightBulbOn() throws InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logics_entities", "c_logic_rule_or");

        // Light bulb turn on Using wires
        res = dmc.tick(Direction.LEFT);
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());
    }

    @Test
    @DisplayName("Test LightBulbOn And")
    public void lightBulbOnAnd() throws InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logics_entities", "c_logic_rule_and");

        // Light bulb turn on Using wires
        for (int i = 0; i < 8; i++)
            res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());
    }

    @Test
    @DisplayName("Test LightBulbOn XOR")
    public void lightBulbOffXOR() throws InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logics_entities", "c_logic_rule_xor");

        // Light bulb stay Off because XOR
        for (int i = 0; i < 8; i++)
            res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        assertEquals(7, TestUtils.getEntities(res, "light_bulb_off").size());
    }

    @Test
    @DisplayName("Test LightBulbOn CO_AND")
    public void lightBulbOnCOAND() throws InvalidActionException {
        DungeonManiaController dmc;
        dmc = new DungeonManiaController();
        DungeonResponse res = dmc.newGame("d_logics_entities", "c_logic_rule_and");

        // Light bulb turn on Using wires
        for (int i = 0; i < 8; i++)
            res = dmc.tick(Direction.DOWN);
        res = dmc.tick(Direction.LEFT);
        assertEquals(1, TestUtils.getEntities(res, "light_bulb_on").size());
    }

}
