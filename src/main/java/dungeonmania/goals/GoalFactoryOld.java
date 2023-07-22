// package dungeonmania.goals;

// import org.json.JSONArray;
// import org.json.JSONObject;

// public class GoalFactoryOld {
// public static GoalOld createGoal(JSONObject jsonGoal, JSONObject config) {
// JSONArray subgoals;
// switch (jsonGoal.getString("goal")) {
// case "AND":
// subgoals = jsonGoal.getJSONArray("subgoals");
// return new GoalOld("AND", createGoal(subgoals.getJSONObject(0), config),
// createGoal(subgoals.getJSONObject(1), config));
// case "OR":
// subgoals = jsonGoal.getJSONArray("subgoals");
// return new GoalOld("OR", createGoal(subgoals.getJSONObject(0), config),
// createGoal(subgoals.getJSONObject(1), config));
// case "exit":
// return new GoalOld("exit");
// case "boulders":
// return new GoalOld("boulders");
// case "treasure":
// int treasureGoal = config.optInt("treasure_goal", 1);
// return new GoalOld("treasure", treasureGoal);
// default:
// return null;
// }
// }
// }
