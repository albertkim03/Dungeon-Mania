package dungeonmania.goals;

// import java.util.List;

import dungeonmania.Game;
// import dungeonmania.entities.Entity;
// import dungeonmania.entities.Exit;
// import dungeonmania.entities.Player;
// import dungeonmania.entities.Switch;
// import dungeonmania.util.Position;

public class OrGoal implements Goal {
    private Goal goal1;
    private Goal goal2;

    public OrGoal(Goal goal1, Goal goal2) {
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    public boolean achieved(Game game) {
        return goal1.achieved(game) || goal2.achieved(game);
    }

    public String toString(Game game) {
        if (this.achieved(game))
            return "";
        else
            return "(" + goal1.toString(game) + " OR " + goal2.toString(game) + ")";
    }

    public Goal getGoal1() {
        return goal1;
    }

    public Goal getGoal2() {
        return goal2;
    }
}
