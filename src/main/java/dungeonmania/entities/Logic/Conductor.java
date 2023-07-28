package dungeonmania.entities.Logic;

import java.util.ArrayList;
import java.util.List;

import dungeonmania.entities.collectables.Bomb;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Conductor extends Logic {
    private Integer activeTick = -1;
    private List<Bomb> bombs = new ArrayList<>();

    public Conductor(Position position) {
        super(position);
    }

    public void setActivated(GameMap map, boolean change, Integer tick) {
        super.setActivated(change);
        if (change) {
            this.activeTick = tick;
            for (Bomb b : bombs) {
                if (b.checkLogicRule(change, tick))
                    b.notify(map);
            }
        } else {
            this.activeTick = -1;
        }
    }

    public Integer getActiveTick() {
        return activeTick;
    }

    public void setActiveTick(Integer tick) {
        this.activeTick = tick;
    }

    public void subscribe(Bomb b) {
        this.bombs.add(b);
    }

    public void subscribe(Bomb bomb, GameMap map) {
        bombs.add(bomb);
        if (isActivated()) {
            bombs.stream().forEach(b -> b.notify(map));
        }
    }

    public void unsubscribe(Bomb b) {
        bombs.remove(b);
    }

    public List<Bomb> getBombs() {
        return bombs;
    }

}
