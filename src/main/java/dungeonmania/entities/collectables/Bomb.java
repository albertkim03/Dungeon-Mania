package dungeonmania.entities.collectables;

import dungeonmania.util.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.Logic.Conductor;
import dungeonmania.entities.Logic.Logic;
import dungeonmania.entities.Logic.LogicRuleEntity;
import dungeonmania.map.GameMap;

public class Bomb extends LogicRuleEntity {
    public enum State {
        SPAWNED, INVENTORY, PLACED
    }

    public static final int DEFAULT_RADIUS = 1;
    private State state;
    private int radius;

    private List<Conductor> logics = new ArrayList<>();

    public Bomb(Position position, int radius, String logicalRule) {
        super(position.asLayer(Entity.ITEM_LAYER), logicalRule);
        state = State.SPAWNED;
        this.radius = radius;
    }

    public void subscribe(Conductor s) {
        this.logics.add(s);
    }

    public void notify(GameMap map) {
        explode(map);
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (state != State.SPAWNED)
            return;
        if (entity instanceof Player) {
            if (!((Player) entity).pickUp(this))
                return;
            logics.stream().forEach(l -> l.unsubscribe(this));
            logics.stream().forEach(l -> l.logicUnsubscribe(this));
            map.destroyEntity(this);
        }
        this.state = State.INVENTORY;
    }

    // @Override
    // public void onMovedAway(GameMap map, Entity entity) {
    // return;
    // }

    // @Override
    // public void onDestroy(GameMap gameMap) {
    // return;
    // }

    public void onPutDown(GameMap map, Position p) {
        translate(Position.calculatePositionBetween(getPosition(), p));
        map.addEntity(this);
        this.state = State.PLACED;
        List<Position> adjPosList = getPosition().getCardinallyAdjacentPositions();
        adjPosList.stream().forEach(node -> {
            List<Entity> entities = map.getEntities(node).stream().filter(e -> (e instanceof Conductor))
                    .collect(Collectors.toList());
            entities.stream().map(Conductor.class::cast).forEach(s -> s.subscribe(this, map));
            entities.stream().map(Logic.class::cast).forEach(s -> s.logicSubscribe(this));
            entities.stream().map(Conductor.class::cast).forEach(s -> this.subscribe(s));
            entities.stream().map(Logic.class::cast).forEach(s -> s.logicSubscribe(s));
        });
    }

    public void explode(GameMap map) {
        int x = getPosition().getX();
        int y = getPosition().getY();
        for (int i = x - radius; i <= x + radius; i++) {
            for (int j = y - radius; j <= y + radius; j++) {
                List<Entity> entities = map.getEntities(new Position(i, j));
                entities = entities.stream().filter(e -> !(e instanceof Player)).collect(Collectors.toList());
                for (Entity e : entities)
                    map.destroyEntity(e);
            }
        }
    }

    public State getState() {
        return state;
    }

}
