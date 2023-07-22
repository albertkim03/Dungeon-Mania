package dungeonmania.goals;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Exit;
import dungeonmania.entities.Player;
import dungeonmania.entities.Switch;
import dungeonmania.util.Position;

public class AndGoal implements Goal {
    Goal goal1;
    Goal goal2;

    public AndGoal(Goal goal1, Goal goal2) {
        this.goal1 = goal1;
        this.goal2 = goal2;
    }

    public boolean achieved(Game game) {
        return goal1.achieved(game) && goal2.achieved(game);
    }

    public String toString(Game game) {
        if (this.achieved(game))
            return "";
        else
            return "(" + goal1.toString(game) + " AND " + goal2.toString(game) + ")";
    }
}