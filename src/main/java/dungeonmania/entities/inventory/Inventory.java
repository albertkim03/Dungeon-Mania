package dungeonmania.entities.inventory;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.BattleItem;
import dungeonmania.entities.Entity;
import dungeonmania.entities.EntityFactory;
import dungeonmania.entities.Player;
import dungeonmania.entities.buildables.Bow;
import dungeonmania.entities.buildables.Sceptre;
import dungeonmania.entities.collectables.Arrow;
import dungeonmania.entities.collectables.Key;
import dungeonmania.entities.collectables.SunStone;
import dungeonmania.entities.collectables.Sword;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.Wood;
import dungeonmania.entities.enemies.ZombieToast;

public class Inventory {
    private List<InventoryItem> items = new ArrayList<>();

    public boolean add(InventoryItem item) {
        items.add(item);
        return true;
    }

    public void remove(InventoryItem item) {
        items.remove(item);
    }

    public List<String> getBuildables() {

        int wood = count(Wood.class);
        int arrows = count(Arrow.class);
        int treasure = count(Treasure.class);
        int falseTreasure = count(Treasure.class);
        int keys = count(Key.class);
        int falseKeys = count(Key.class);
        int sunStones = count(SunStone.class);
        int swords = count(Sword.class);

        if (keys == 0 && treasure > 0) {
            keys = keys + count(SunStone.class);
        } else if (keys > 0 && treasure == 0) {
            treasure = treasure + count(SunStone.class);
        }

        List<String> result = new ArrayList<>();

        // Bow creation
        if (wood >= 1 && arrows >= 3) {
            result.add("bow");
        }

        // MidnightArmour creation
        // (1 sword + 1 sun stone)
        if (swords >= 1 && sunStones >= 1) {
            result.add("midnight_armour");
        }

        // sunStone acting as a substitute
        if (sunStones > 1) {
            if (keys == 0 && treasure >= 1) {
                falseKeys++;
            } else if (keys >= 1 && treasure == 0) {
                falseTreasure++;
            } else if (keys == 0 && treasure == 1) {
            }
        }
        // sceptre creation
        // (1 wood OR 2 arrows) + (1 key OR 1 treasure) + (1 sun stone)
        if ((wood >= 1 || arrows >= 2) && (falseKeys >= 1 || falseTreasure >= 1) && (sunStones >= 1)) {
            result.add("sceptre");
        }

        // reset counts back to normal
        falseTreasure = treasure;
        falseKeys = keys;

        // sunStones acting as substitute
        if (sunStones >= 1) {
            if (keys == 0 && treasure >= 1) {
                falseKeys++;
            } else if (keys >= 1 && treasure == 0) {
                falseTreasure++;
            }
        }

        // shield creation
        if (wood >= 2 && (falseTreasure >= 1 || falseKeys >= 1)) {
            result.add("shield");
        }

        return result;
    }

    public InventoryItem checkBuildCriteria(Player p, boolean remove, int buildIndex, EntityFactory factory) {

        List<Wood> wood = getEntities(Wood.class);
        List<Arrow> arrows = getEntities(Arrow.class);
        List<Treasure> treasure = getEntities(Treasure.class);
        List<Key> keys = getEntities(Key.class);
        List<SunStone> sunStones = getEntities(SunStone.class);
        List<Sword> swords = getEntities(Sword.class);
        List<ZombieToast> zombieToasts = getEntities(ZombieToast.class);

        // build bow
        if (buildIndex == 0 && getBuildables().contains("bow") && wood.size() >= 1 && arrows.size() >= 3) {
            if (remove) {
                // (1 wood)
                items.remove(wood.get(0));

                // (3 arrows)
                items.remove(arrows.get(0));
                items.remove(arrows.get(1));
                items.remove(arrows.get(2));
            }
            return factory.buildBow();
        }

        // build shield
        if (buildIndex == 1 && getBuildables().contains("shield") && wood.size() >= 2
                && (treasure.size() >= 1 || keys.size() >= 1 || sunStones.size() >= 1)) {
            if (remove) {
                // (2 wood)
                items.remove(wood.get(0));
                items.remove(wood.get(1));

                // (1 treasure OR 1 key)
                if (treasure.size() >= 1) {
                    items.remove(treasure.get(0));
                } else if (keys.size() >= 1) {
                    items.remove(keys.get(0));
                }
            }

            return factory.buildShield();
        }

        // build midnight_armour
        if (buildIndex == 2 && getBuildables().contains("midnight_armour") && zombieToasts.isEmpty()
                && swords.size() >= 1
                && sunStones.size() >= 1) {
            if (remove) {
                // (1 sword)
                items.remove(swords.get(0));

                // (1 sunStone)
                items.remove(sunStones.get(0));
            }

            return factory.buildMidnightArmour();
        }

        // build sceptre
        if (buildIndex == 3 && getBuildables().contains("sceptre") && (wood.size() >= 1 || arrows.size() >= 2)
                && (keys.size() >= 1 || treasure.size() >= 1)
                && (sunStones.size() >= 1)) {
            if (remove) {
                // (1 wood OR 2 arrows)
                if (wood.size() >= 1) {
                    items.remove(wood.get(0));

                } else if (arrows.size() >= 2) {
                    items.remove(arrows.get(0));
                    items.remove(arrows.get(1));
                }

                // (1 key OR 1 treasure)
                if (keys.size() >= 1) {
                    items.remove(keys.get(0));

                } else if (treasure.size() >= 1) {
                    items.remove(treasure.get(0));
                }

                // (1 sun stone)
                items.remove(sunStones.get(0));
            }

            return factory.buildSceptre();
        }

        return null;
    }

    public <T extends InventoryItem> T getFirst(Class<T> itemType) {
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                return itemType.cast(item);
        return null;
    }

    public <T extends InventoryItem> int count(Class<T> itemType) {
        int count = 0;
        for (InventoryItem item : items)
            if (itemType.isInstance(item))
                count++;
        return count;
    }

    public Entity getEntity(String itemUsedId) {
        for (InventoryItem item : items)
            if (((Entity) item).getId().equals(itemUsedId))
                return (Entity) item;
        return null;
    }

    public List<Entity> getEntities() {
        return items.stream().map(Entity.class::cast).collect(Collectors.toList());
    }

    public <T> List<T> getEntities(Class<T> clz) {
        return items.stream().filter(clz::isInstance).map(clz::cast).collect(Collectors.toList());
    }

    public boolean hasWeapon() {
        return getFirst(Sword.class) != null || getFirst(Bow.class) != null;
    }

    public boolean hasSceptre() {
        return getFirst(Sceptre.class) != null;
    }

    public BattleItem getWeapon() {
        BattleItem weapon = getFirst(Sword.class);
        if (weapon == null)
            return getFirst(Bow.class);
        return weapon;
    }

}
