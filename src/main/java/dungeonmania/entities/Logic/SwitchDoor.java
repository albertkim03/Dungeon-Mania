package dungeonmania.entities.Logic;

import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwitchDoor extends LogicRuleEntity {
    public SwitchDoor(Position position, String logicalRule) {
        super(position, logicalRule);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return isActivated() || entity instanceof Spider;
    }

}
