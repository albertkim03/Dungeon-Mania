package dungeonmania.entities;

import dungeonmania.entities.Logic.Conductor;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Switch extends Conductor {
    private boolean activated = false;

    public Switch(Position position) {
        super(position.asLayer(Entity.ITEM_LAYER));
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = true;
            if (!getAdjLogics().isEmpty()) {
                processAdjLogics(map, true, map.getGame().getTick());
            }
            getBombs().stream().forEach(b -> b.notify(map));
        }
    }

    @Override
    public void onMovedAway(GameMap map, Entity entity) {
        if (entity instanceof Boulder) {
            activated = false;
            if (!getAdjLogics().isEmpty()) {
                processAdjLogics(map, false, map.getGame().getTick());
                processAdjLogics(map, false, map.getGame().getTick());
            }
        }
    }

    public boolean isActivated() {
        return activated;
    }

    // @Override
    // public void onDestroy(GameMap gameMap) {
    // return;
    // }
}
