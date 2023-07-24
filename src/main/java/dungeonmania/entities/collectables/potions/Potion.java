package dungeonmania.entities.collectables.potions;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Potion extends Entity implements InventoryItem, BattleItem {
    private int duration;
    private int attackMagnifier;
    private int damageReducer;
    private boolean isInvincible;
    private boolean isEnabled;

    public void setAttackMagnifier(int attackMagnifier) {
        this.attackMagnifier = attackMagnifier;
    }

    public void setDamageReducer(int damageReducer) {
        this.damageReducer = damageReducer;
    }

    public void setIsInvincible(boolean isInvincible) {
        this.isInvincible = isInvincible;
    }

    public void setIsEnabled(boolean isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Potion(Position position, int duration) {
        super(position);
        this.duration = duration;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player) {
            if (!((Player) entity).pickUp(this))
                return;
            map.destroyEntity(this);
        }
    }

    // @Override
    // public void onDestroy(GameMap gameMap) {
    //     return;
    // }

    // @Override
    // public void onMovedAway(GameMap map, Entity entity) {
    //     return;
    // }

    @Override
    public void use(Game game) {
        return;
    }

    public int getDuration() {
        return duration;
    }

    @Override
    public BattleStatistics applyBuff(BattleStatistics origin) {
        return BattleStatistics.applyBuff(origin,
         new BattleStatistics(0, 0, 0,
          attackMagnifier, damageReducer, isInvincible, isEnabled));
    }

    @Override
    public int getDurability() {
        return 1;
    }
}
