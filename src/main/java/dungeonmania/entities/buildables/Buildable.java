package dungeonmania.entities.buildables;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Entity;
import dungeonmania.entities.inventory.InventoryItem;
// import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Buildable extends Entity implements InventoryItem, BattleItem {
    private int durability;
    private double defence;
    private double attackMagnifier;
    private double damageReducer;

    public Buildable(Position position) {
        super(position);
        this.defence = 0;
        this.attackMagnifier = 0;
        this.damageReducer = 0;
    }

    public void use(Game game) {
        durability--;
        if (durability <= 0) {
            game.getPlayer().remove(this);
        }
    }

    public int getDurability() {
        return durability;
    }

    public void setDurability(int durability) {
        this.durability = durability;
    }

    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, defence, attackMagnifier, damageReducer));
    }

    public void setDefence(double defence) {
        this.defence = defence;
    }

    public void setAttackMagnifier(double attackMagnifier) {
        this.attackMagnifier = attackMagnifier;
    }

    public void setDamageReducer(double damageReducer) {
        this.damageReducer = damageReducer;
    }

    // @Override
    // public void onOverlap(GameMap map, Entity entity) {
    // return;
    // }

    // @Override
    // public void onMovedAway(GameMap map, Entity entity) {
    // return;
    // }

    // @Override
    // public void onDestroy(GameMap gameMap) {
    // return;
    // }
}
