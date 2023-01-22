package com.possiest.skyblockcore.managers.skyblock.generator;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.ChunkGenerator;

public class IslandGenerator {
    public static final int ISLAND_SIZE = 60;
    public static final int ISLAND_CENTER = ISLAND_SIZE / 2;
    public static final int ISLAND_RADIUS = ISLAND_SIZE / 4;

    public static void generateIsland(World world, int x, int y, int z) {
        int highestBlock = 0;
        for (int i = x - ISLAND_CENTER; i < x + ISLAND_CENTER; i++) {
            for (int j = z - ISLAND_CENTER; j < z + ISLAND_CENTER; j++) {
                double distance = Math.sqrt(Math.pow(i - x, 2) + Math.pow(j - z, 2));
                int height = (int) (ISLAND_RADIUS - distance + Math.random() * 8 - 4);
                for (int k = y; k > y - height; k--) {
                    Location location = new Location(world, i, k, j);
                    location.getBlock().setType(Material.STONE);
                    if (k > highestBlock) {
                        highestBlock = k;
                    }
                }
            }
        }
        for (int i = x - ISLAND_CENTER; i < x + ISLAND_CENTER; i++) {
            for (int j = z - ISLAND_CENTER; j < z + ISLAND_CENTER; j++) {
                Location location = new Location(world, i, highestBlock, j);
                if(location.getBlock().getType() == Material.STONE) location.getBlock().setType(Material.GRASS_BLOCK);
            }
        }

        for (int i = x - ISLAND_CENTER; i < x + ISLAND_CENTER; i++) {
            for (int j = z - ISLAND_CENTER; j < z + ISLAND_CENTER; j++) {
                Location location = new Location(world, i, highestBlock -1, j);
                if(location.getBlock().getType() == Material.STONE) location.getBlock().setType(Material.DIRT);
            }
        }


    }

    //Tree generation

    public static void generateTrees(World world, int x, int y, int z) {
        int treeHeight = (int) (Math.random() * 7 + 3);
        for (int i = 0; i < treeHeight; i++) {
            Location location = new Location(world, x, y + i, z);
            location.getBlock().setType(Material.OAK_LOG);
        }
        for (int i = -4; i <= 4; i++) {
            for (int j = -4; j <= 4; j++) {
                for (int k = -2; k <= 1; k++) {
                    int randomLeaves = (int) (Math.random() * 2);
                    if (randomLeaves == 1) {
                        Location location = new Location(world, x + i, y + treeHeight + k, z + j);
                        location.getBlock().setType(Material.OAK_LEAVES);
                    }
                }
            }
        }
    }
}
