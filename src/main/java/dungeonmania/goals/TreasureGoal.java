package dungeonmania.goals;

// import java.util.List;

import dungeonmania.Game;
// import dungeonmania.entities.Entity;
// import dungeonmania.entities.Exit;
// import dungeonmania.entities.Player;
// import dungeonmania.entities.Switch;
// import dungeonmania.util.Position;

public class TreasureGoal implements Goal {
    private int target;

    public TreasureGoal(int target) {
        this.target = target;
    }

    public boolean achieved(Game game) {
        return game.getCollectedTreasureCount() >= target;
    }

    public String toString(Game game) {
        if (this.achieved(game))
            return "";
        else
            return ":treasure";
    }

    public int getTarget() {
        return target;
    }
}
