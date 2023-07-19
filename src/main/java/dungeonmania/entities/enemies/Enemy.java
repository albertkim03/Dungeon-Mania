package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.battles.Battleable;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.map.GameMap;
import dungeonmania.util.Direction;
import dungeonmania.util.Position;

public abstract class Enemy extends Entity implements Battleable {
    private BattleStatistics battleStatistics;

    public Enemy(Position position, double health, double attack) {
        super(position.asLayer(Entity.CHARACTER_LAYER));
        battleStatistics = new BattleStatistics(health, attack, 0, BattleStatistics.DEFAULT_DAMAGE_MAGNIFIER,
                BattleStatistics.DEFAULT_ENEMY_DAMAGE_REDUCER);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return entity instanceof Player;
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        return battleStatistics;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            map.getGame().battle(player, this);
        }
    }

    @Override
    public void onDestroy(GameMap map) {
        Game g = map.getGame();
        g.unsubscribe(getId());
    }

    // @Override
    // public void onMovedAway(GameMap map, Entity entity) {
    // return;
    // }

    public abstract void move(Game game);

    /**
     * Moves an Enemy when the player is affected by Invincibility potion
     *
     * @param map
     * @param currentPosition
     * @return
     */
    protected Position moveWhenInvincible(GameMap map, Position currentPosition) {
        Position plrDiff = Position.calculatePositionBetween(map.getPlayer().getPosition(), currentPosition);

        Position moveX = (plrDiff.getX() >= 0) ? Position.translateBy(currentPosition, Direction.RIGHT)
                : Position.translateBy(currentPosition, Direction.LEFT);
        Position moveY = (plrDiff.getY() >= 0) ? Position.translateBy(currentPosition, Direction.UP)
                : Position.translateBy(currentPosition, Direction.DOWN);
        Position offset = currentPosition;
        if (plrDiff.getY() == 0 && map.canMoveTo(this, moveX))
            offset = moveX;
        else if (plrDiff.getX() == 0 && map.canMoveTo(this, moveY))
            offset = moveY;
        else if (Math.abs(plrDiff.getX()) >= Math.abs(plrDiff.getY())) {
            if (map.canMoveTo(this, moveX))
                offset = moveX;
            else if (map.canMoveTo(this, moveY))
                offset = moveY;
            else
                offset = currentPosition;
        } else {
            if (map.canMoveTo(this, moveY))
                offset = moveY;
            else if (map.canMoveTo(this, moveX))
                offset = moveX;
            else
                offset = currentPosition;
        }
        return offset;
    }
}
