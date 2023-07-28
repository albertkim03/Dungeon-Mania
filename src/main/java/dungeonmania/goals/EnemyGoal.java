package dungeonmania.goals;

import dungeonmania.Game;

public class EnemyGoal implements Goal {
    private int target;

    public EnemyGoal(int target) {
        this.target = target;
    }

    public boolean achieved(Game game) {
        return game.getKillCount() >= target && game.areAllSpawnersDestroyed();
    }

    public String toString(Game game) {
        if (this.achieved(game))
            return "";
        else
            return ":enemies";
    }

    public int getTarget() {
        return target;
    }
}
