package dungeonmania;

import java.util.List;

import org.json.JSONException;

import dungeonmania.exceptions.InvalidActionException;
import dungeonmania.map.GameMap;
import dungeonmania.response.models.DungeonResponse;
import dungeonmania.response.models.ResponseBuilder;
import dungeonmania.util.Direction;
import dungeonmania.util.FileLoader;
import dungeonmania.util.Position;

import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.Exit;
import dungeonmania.entities.Player;
import dungeonmania.entities.Wall;
/**
 * DO NOT CHANGE METHOD SIGNITURES OF THIS FILE
 * */
public class DungeonManiaController {
    private Game game = null;

    public String getSkin() {
        return "default";
    }

    public String getLocalisation() {
        return "en_US";
    }

    /**
     * /dungeons
     */
    public static List<String> dungeons() {
        return FileLoader.listFileNamesInResourceDirectory("dungeons");
    }

    /**
     * /configs
     */
    public static List<String> configs() {
        return FileLoader.listFileNamesInResourceDirectory("configs");
    }

    /**
     * /game/new
     */
    public DungeonResponse newGame(String dungeonName, String configName) throws IllegalArgumentException {
        if (!dungeons().contains(dungeonName)) {
            throw new IllegalArgumentException(dungeonName + " is not a dungeon that exists");
        }

        if (!configs().contains(configName)) {
            throw new IllegalArgumentException(configName + " is not a configuration that exists");
        }

        try {
            GameBuilder builder = new GameBuilder();
            game = builder.setConfigName(configName).setDungeonName(dungeonName).buildGame();
            return ResponseBuilder.getDungeonResponse(game);
        } catch (JSONException e) {
            return null;
        }
    }

    /**
     * /game/dungeonResponseModel
     */
    public DungeonResponse getDungeonResponseModel() {
        return null;
    }

    /**
     * /game/tick/item
     */
    public DungeonResponse tick(String itemUsedId) throws IllegalArgumentException, InvalidActionException {
        return ResponseBuilder.getDungeonResponse(game.tick(itemUsedId));
    }

    /**
     * /game/tick/movement
     */
    public DungeonResponse tick(Direction movementDirection) {
        return ResponseBuilder.getDungeonResponse(game.tick(movementDirection));
    }

    /**
     * /game/build
     */
    public DungeonResponse build(String buildable) throws IllegalArgumentException, InvalidActionException {
        List<String> validBuildables = List.of("bow", "shield", "midnight_armour", "sceptre");
        if (!validBuildables.contains(buildable)) {
            throw new IllegalArgumentException("Only bow, shield, midnight_armour and sceptre can be built");
        }

        return ResponseBuilder.getDungeonResponse(game.build(buildable));
    }

    /**
     * /game/interact
     */
    public DungeonResponse interact(String entityId) throws IllegalArgumentException, InvalidActionException {
        return ResponseBuilder.getDungeonResponse(game.interact(entityId));
    }

    /**
     * /game/new/generate
     */
    public DungeonResponse generateDungeon(int xStart, int yStart, int xEnd, int yEnd, String configName)
            throws IllegalArgumentException {
                //  NOTE NEED TO BE SET CONFIG AND BUILD THE GAEME
        // Checks if configName exist
        if (!configs().contains(configName)) {
            throw new IllegalArgumentException(configName + " is not a configuration that exists");
        }
        // Set the newly created Prims algo maze to entities required
        // to create the game map, then build it and return it
        int height = Math.abs(yEnd - yStart) + 3;
        int width = Math.abs(xEnd - xStart) + 3;
        Position start = new Position(xStart, yStart);
        Position end = new Position(xEnd, yEnd);
        boolean[][] maze = DungeonGenerationBuilder.createRandomDungeon(width, height, start, end);
        // Modify Game game with the appropriate map maze
        GameMap map = new GameMap();
        game = new Game("dungeon_generation");
        EntityFactory entityFactory = game.getEntityFactory();
        // ADD ENTITIES (Wall, Player, Exit)
        // Add Player and Exit
        map.setPlayer((Player) entityFactory.buildPlayer(start));
        map.addEntity(new Exit(end));
        // Add (outer) Walls
        for (int x = xStart - 1; x <= xEnd + 1; x++) {
            map.addEntity(createWallEntityDungeonGeneration(x, yStart + 1));
            map.addEntity(createWallEntityDungeonGeneration(x, yEnd - 1));
        }
        for (int y = yStart; y >= yEnd; y--) {
            map.addEntity(createWallEntityDungeonGeneration(xStart - 1, y));
            map.addEntity(createWallEntityDungeonGeneration(xEnd + 1, y));
        }
        // Add (inner) Walls
        for (int x = xStart; x <= xEnd; x++) {
            for (int y = yStart; y >= yEnd; y--) {
               if (!maze[y][x]) {
                // If its a wall
                map.addEntity(createWallEntityDungeonGeneration(x, y));
               }
            }
        }
        // UPDATE MAP TO GAME
        game.setMap(map);
        return ResponseBuilder.getDungeonResponse(game);
    }

    /**
     * /game/rewind
     */
    public DungeonResponse rewind(int ticks) throws IllegalArgumentException {
        return null;
    }

    private Entity createWallEntityDungeonGeneration(int x, int y) {
        return new Wall(new Position(x, y));
    }
}
