package dungeonmania;

import dungeonmania.util.Position;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONObject;

public class DungeonGenerationBuilder {
    private static Random rand = new Random();

    /**
     * Create a Dungeon using Randomized Prims algorithm.
     * Pseudocode of the algo goes as follow:
     *
     * function RandomizedPrims(width, height, start, end):
     *    let maze be a 2D array of booleans (of size width and height) default false
     *   // false representing a wall and true representing empty space
     *
     *    maze[start] = empty
     *
     *    let options be a set of unique positions
     *    add to options all neighbours of 'start' not on boundary that are of distance 2 away and are walls
     *
     *       while options is not empty:
     *       let next = remove random from options
     *
     *       let neighbours = each neighbour of distance 2 from next not on boundary that are empty
     *        if neighbours is not empty:
     *           let neighbour = random from neighbours
     *           maze[ next ] = empty (i.e. true)
     *            maze[ position inbetween next and neighbour ] = empty (i.e. true)
     *            maze[ neighbour ] = empty (i.e. true)
     *
     *        add to options all neighbours of 'next' not on boundary that are of distance 2 away and are walls
     *
     *    // at the end there is still a case where our end position isn't connected to the map
     *    // we don't necessarily need this, you can just keep randomly generating maps (was original intention)
     *    // but this will make it consistently have a pathway between the two.
     *    if maze[end] is a wall:
     *        maze[end] = empty*
     *
     *        let neighbours = neighbours not on boundary of distance 1 from maze[end]
     *       *if there are no cells in neighbours that are empty:
     *         *   // let's connect it to the grid
     *            let neighbour = random from neighbours
     *            maze[neighbour] = empty*
     *
     * @param width
     * @param height
     * @param start
     * @param end
     * @return maze[][] boolean, where false represent a wall, and true represents empty space.
     */
    public static boolean[][] createRandomDungeon(int xStart, int xEnd, int yStart, int yEnd) {
        // Set new maze, adjust mathematical dilatation for the coordinates
        int xS = 0;
        int yS = 0;
        int xE = xEnd - xStart;
        int yE = yEnd - yStart;
        int height = yE + 1;
        int width = xE + 1;

        boolean[][] maze = new boolean[height + 1][width + 1];
        // FALSE == WALL, TRUE == EMPTY SPACE
        maze[yS][xS] = true;
        // let options be a set of unique positions
        // add to options all neighbours of 'start' not on boundary that are of distance 2 away and are walls
        Position currentPosition = new Position(xS, yS);
        List<Position> options = new ArrayList<>();
        options.addAll(getNeighboursOfTwo(currentPosition, maze, width, height));
        // while options is not empty:
        //    let next = remove random from options

        while (!options.isEmpty()) {
            Position next = getRandomFromList(options);
            options.remove(next);
            // let neighbours = each neighbour of distance 2 from next not on boundary that are empty
            // if neighbours is not empty:
            List<Position> neighbours = getEmptyNeigbhours(next, maze, width, height);
            if (!neighbours.isEmpty()) {
                // let neighbour = random from neighbours
                Position neighbour = getRandomFromList(neighbours);
                // maze[ next ] = empty (i.e. true)

                maze[next.getY()][next.getX()] = true;

                // maze[ position inbetween next and neighbour ] = empty (i.e. true)
                System.out.println(next);
                System.out.println(neighbour);
                System.out.println(height);
                System.out.println(width);
                int inbetweenX = (next.getX() + neighbour.getX()) / 2;
                int inbetweenY = (next.getY() + neighbour.getY()) / 2;
                maze[inbetweenY][inbetweenX] = true;

                // maze[ neighbour ] = empty (i.e. true)
                maze[neighbour.getY()][neighbour.getX()] = true;
            }
            // add to options all neighbours of 'next' not on boundary that are of distance 2 away and are walls
            options.addAll(getNeighboursOfTwo(next, maze, width, height));
        }
        // if maze[end] is a wall:
        //  maze[end] = empty
        if (!maze[yE][xE]) {
            maze[yE][xE] = true;
            // let neighbours = neighbours not on boundary of distance 1 from maze[end]
            // if there are no cells in neighbours that are empty:
            // get Everythins in neighbours should be false
            List<Position> neighbours = getNeighboursOfOne(new Position(xE, yE), maze, width, height);
            boolean connectToGrid = true;
            for (Position p : neighbours) {
                // if one cell in neighbour is not empty
                // don't connect to grid
                if (!maze[p.getY()][p.getX()]) {
                    connectToGrid = false;
                }
            }
            if (connectToGrid) {
                //let neighbour = random from neighbours
                //maze[neighbour] = empty
                Position neighbour = getRandomFromList(neighbours);
                maze[neighbour.getY()][neighbour.getX()] = true;
            }
        }
        return maze;
    }

    /**
     * Gets the neighbours of Position P of distance 2, is a wall
     * @param p
     * @param maze
     * @param width
     * @param height
     * @return
     */
    private static List<Position> getNeighboursOfTwo(Position p, boolean[][] maze, int width, int height) {
        List<Position> neighbours = new ArrayList<>();
        // Checks all directions for valid neighbours of distance 2
        int[][] directions = {
                {
                        2, 0
                }, {
                        -2, 0
                }, {
                        0, 2
                }, {
                        0, -2
                }
        };
        for (int[] dir : directions) {
            int x = p.getX() + dir[0];
            int y = p.getY() + dir[1];
            // Checks if thes neigbhours are not on boundary
            // distance of 2 away and are walls
            if (x >= 0 && x < width && y >= 0 && y < height && !maze[y][x]) {
                neighbours.add(new Position(x, y));
            }
        }
        return neighbours;
    }

    /**
     * Gets neigbhours of Position p of distance one
     * @param p
     * @param maze
     * @param width
     * @param height
     * @return
     */
    private static List<Position> getNeighboursOfOne(Position p, boolean[][] maze, int width, int height) {
        List<Position> neighbours = new ArrayList<>();
        // Checks all directions for valid neighbours of distance 2
        int[][] directions = {
                {
                        1, 0
                }, {
                        -1, 0
                }, {
                        0, 1
                }, {
                        0, -1
                }
        };
        for (int[] dir : directions) {
            int x = p.getX() + dir[0];
            int y = p.getY() + dir[1];
            // Checks if thes neigbhours are not on boundary
            // distance of 2 away and are walls
            if (x >= 0 && x < width && y >= 0 && y < height) {
                neighbours.add(new Position(x, y));
            }
        }
        return neighbours;
    }

    /**
     * Gets neighbours of Position p of distance 2, and is empty (not a wall)
     * @param p
     * @param maze
     * @param width
     * @param height
     * @return
     */
    private static List<Position> getEmptyNeigbhours(Position p, boolean[][] maze, int width, int height) {
        List<Position> emptyNeighbours = new ArrayList<>();
        // Checks all directions for valid neighbours of distance 2
        int[][] directions = {
                {
                        2, 0
                }, {
                        -2, 0
                }, {
                        0, 2
                }, {
                        0, -2
                }
        };
        for (int[] dir : directions) {
            int x = p.getX() + dir[0];
            int y = p.getY() + dir[1];
            if (x >= 0 && x < width && y >= 0 && y < height && maze[y][x]) {
                emptyNeighbours.add(new Position(x, y));
            }
        }
        return emptyNeighbours;
    }

    /**
     * Gets a random Position, from list of Positions list
     * @param list
     * @return
     */
    private static Position getRandomFromList(List<Position> list) {
        int index = Math.abs(rand.nextInt(list.size()));
        return list.get(index);
    }

    /**
     * maze From createRandomDungeon is processed to a json file
     * and then saved to src/main/resources/dungeons/
     * @param maze
     * @param xStart
     * @param yStart
     * @param xEnd
     * @param yEnd
     */
    public static void dungeonToJSON(boolean[][] maze, int xStart, int yStart, int xEnd, int yEnd) {
        JSONObject dungeon = new JSONObject();
        JSONArray entities = new JSONArray();
        // Adds Entity to JSON file
        // Add inner walls, player, exit goal
        for (int x = 0; x < maze[0].length - 1; x++) {
            for (int y = 0; y < maze.length - 1; y++) {
                if (!maze[y][x] || (x + xStart == xStart && y + yStart == yStart)
                        || (x + xStart == xEnd && y + yStart == yEnd)) {
                    JSONObject entity = new JSONObject();
                    // Readjust mathematical dilatation done
                    //   on createRandomDungeon
                    if (!maze[y][x]) {
                        entity.put("x", x + xStart);
                        entity.put("y", y + yStart);
                        entity.put("type", "wall");
                    } else if (x + xStart == xStart && y + yStart == yStart) {
                        entity.put("x", x + xStart);
                        entity.put("y", y + yStart);

                        entity.put("type", "player");
                    } else if (x + xStart == xEnd && y + yStart == yEnd) {
                        entity.put("x", x + xStart);
                        entity.put("y", y + yStart);
                        entity.put("type", "exit");
                    }
                    entities.put(entity);
                }
            }
        }
        // Add outer walls
        for (int x = xStart - 1; x < maze[0].length + 2; x++) {
            JSONObject entity1 = new JSONObject();
            entity1.put("x", x);
            entity1.put("y", yStart - 1);
            entity1.put("type", "wall");
            entities.put(entity1);
            JSONObject entity2 = new JSONObject();
            entity2.put("x", x);
            entity2.put("y", yEnd + 1);
            entity2.put("type", "wall");
            entities.put(entity2);
        }

        for (int y = yStart - 1; y < maze.length + 2; y++) {
            JSONObject entity1 = new JSONObject();
            entity1.put("x", xStart - 1);
            entity1.put("y", y);
            entity1.put("type", "wall");
            entities.put(entity1);
            JSONObject entity2 = new JSONObject();
            entity2.put("x", xEnd + 1);
            entity2.put("y", y);
            entity2.put("type", "wall");
            entities.put(entity2);
        }

        dungeon.put("entities", entities);

        // ADD GOALS (exit only)
        JSONObject goalCondition = new JSONObject();
        goalCondition.put("goal", "exit");
        dungeon.put("goal-condition", goalCondition);

        try {
            FileWriter file = new FileWriter("src/main/resources/dungeons/" + "dungeon_generation.json");
            file.write(dungeon.toString());
            file.close();
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        System.out.println("Successfully saved maze to JSON file...");
    }

}
