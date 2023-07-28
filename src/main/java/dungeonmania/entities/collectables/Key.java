package dungeonmania.entities.collectables;

import dungeonmania.util.Position;

public class Key extends Collectables {
    private int number;

    public Key(Position position, int number) {
        super(position);
        this.number = number;
    }

    // @Override
    // public void onMovedAway(GameMap map, Entity entity) {
    // return;
    // }

    // @Override
    // public void onDestroy(GameMap gameMap) {
    // return;
    // }

    public int getnumber() {
        return number;
    }

}
