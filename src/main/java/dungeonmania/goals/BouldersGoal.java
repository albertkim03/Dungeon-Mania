package dungeonmania.goals;

// import java.util.List;

import dungeonmania.Game;
// import dungeonmania.entities.Entity;
// import dungeonmania.entities.Exit;
// import dungeonmania.entities.Player;
import dungeonmania.entities.Switch;
// import dungeonmania.util.Position;

public class BouldersGoal implements Goal {
    public boolean achieved(Game game) {
        return game.getMap().getEntities(Switch.class).stream().allMatch(s -> s.isActivated());
    }

    public String toString(Game game) {
        if (this.achieved(game))
            return "";
        else
            return ":boulders";
    }
}
