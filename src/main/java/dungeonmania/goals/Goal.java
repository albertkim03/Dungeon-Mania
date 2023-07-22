package dungeonmania.goals;

import java.util.List;

import dungeonmania.Game;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Exit;
import dungeonmania.entities.Player;
import dungeonmania.entities.Switch;
import dungeonmania.util.Position;

public interface Goal {
    boolean achieved(Game game);

    String toString(Game game);
}