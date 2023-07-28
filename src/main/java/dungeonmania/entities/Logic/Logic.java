package dungeonmania.entities.Logic;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

import dungeonmania.entities.Entity;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Logic extends Entity implements InventoryItem {
    private boolean activated = false;
    private List<Logic> adjLogics = new ArrayList<>();

    public Logic(Position position) {
        super(position.asLayer(Entity.DOOR_LAYER));
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    public boolean isActivated() {
        return activated;
    }

    public List<Position> adjPosList() {
        return getPosition().getCardinallyAdjacentPositions();
    }

    public void setActivated(boolean change) {
        this.activated = change;
    }

    public List<Logic> getAdjLogics() {
        return adjLogics;
    }

    public void subscribe(Logic l) {
        adjLogics.add(l);
    }

    public void processAdjLogics(GameMap map, boolean activate, Integer tick) {
        Queue<Logic> queue = new LinkedList<>();
        Set<Logic> processedLogics = new HashSet<>();

        queue.add(this);
        processedLogics.add(this);

        while (!queue.isEmpty()) {
            Logic currentLogic = queue.poll();
            if (!(currentLogic instanceof Conductor)) {
                continue; // Skip processing if the currentLogic is not a Conductor
            }
            for (Logic logic : currentLogic.getAdjLogics()) {
                if (logic instanceof Conductor) {
                    ((Conductor) logic).setActivated(map, activate, tick);
                } else {
                    ((LogicRuleEntity) logic).setActivated(activate, ((Conductor) currentLogic).getActiveTick());
                }
                if (!processedLogics.contains(logic)) {
                    processedLogics.add(logic);
                    queue.add(logic);
                }
            }
        }

    }

    public Integer sumProcessAdjLogics(boolean activate) {
        Integer sum = 0;
        for (Logic logic : adjLogics) {
            if ((logic.isActivated() == activate) && logic instanceof Conductor) {
                sum++;
            }
        }
        return sum;
    }

    public Integer sumProcessTickAdjLogics(boolean activate, Integer tick) {
        Integer sum = 0;
        for (Logic logic : adjLogics) {
            if ((logic.isActivated() == activate) && logic instanceof Conductor
                    && (((Conductor) logic).getActiveTick() == tick)) {
                sum++;
            }
        }
        return sum;
    }

    public void logicSubscribe(Logic l) {
        this.adjLogics.add(l);
    }

    public void logicUnsubscribe(Logic l) {
        adjLogics.remove(l);
    }
}
