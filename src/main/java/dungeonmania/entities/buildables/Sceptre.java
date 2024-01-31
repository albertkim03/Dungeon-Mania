package dungeonmania.entities.buildables;

import dungeonmania.entities.enemies.Mercenary;

import java.util.List;

import dungeonmania.Game;

public class Sceptre extends Buildable {
    public static final int MIND_CONTROL_DURATION = 5;
    // private int durability;
    // private double defence;

    public Sceptre() {
        super(null);
        this.setDurability(999999999);
        this.setAttackMagnifier(1);
        this.setDamageReducer(1);
    }

    @Override
    public void use(Game game) {
        List<Mercenary> mercs = game.getMap().getEntities(Mercenary.class);

        for (Mercenary merc : mercs) {
            merc.setMindControlled(true, MIND_CONTROL_DURATION);
        }

        return;
    }

}
