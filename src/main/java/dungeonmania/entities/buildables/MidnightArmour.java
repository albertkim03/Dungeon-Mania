package dungeonmania.entities.buildables;

import dungeonmania.Game;
//import dungeonmania.battles.BattleStatistics;

public class MidnightArmour extends Buildable {
    // private int durability;
    // private double defence;

    public MidnightArmour(double defence) {
        super(null);
        this.setDurability(999999999);
        this.setDefence(defence);
        this.setAttackMagnifier(2);
        this.setDamageReducer(1);
    }

    @Override
    public void use(Game game) {
        return;
    }
    // @Override
    // public void use(Game game) {
    // durability--;
    // if (durability <= 0) {
    // game.getPlayer().remove(this);
    // }
    // }

    // @Override
    // public BattleStatistics applyBuff(BattleStatistics origin) {
    // return BattleStatistics.applyBuff(origin, new BattleStatistics(0, 0, defence,
    // 1, 1));
    // }

    // @Override
    // public int getDurability() {
    // return durability;
    // }

}
