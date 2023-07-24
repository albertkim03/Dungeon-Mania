package dungeonmania.entities.collectables.potions;

// import dungeonmania.battles.BattleStatistics;
import dungeonmania.util.Position;

public class InvincibilityPotion extends Potion {
    public static final int DEFAULT_DURATION = 8;

    public InvincibilityPotion(Position position, int duration) {
        super(position, duration);
        this.setAttackMagnifier(1);
        this.setDamageReducer(1);
        this.setIsInvincible(true);
        this.setIsEnabled(true);
    }

    // @Override
    // public BattleStatistics applyBuff(BattleStatistics origin) {
    //     return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, 0, 1, 1, true, true));
    // }
}
