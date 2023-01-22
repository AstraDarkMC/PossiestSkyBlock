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
        // Generate the trunk of the tree
        for (int i = 0; i < 10; i++) {
            Location location = new Location(world, x, y + i, z);
            location.getBlock().setType(Material.SPRUCE_LOG);
        }

        // Generate branches
        generateBranches(world, x, y + 5, z, 3, 0);
        generateBranches(world, x, y + 7, z, 2, 1);
        generateBranches(world, x, y + 8, z, 2, -1);

        // Generate leaves
        for (int i = -2; i <= 2; i++) {
            for (int j = -2; j <= 2; j++) {
                for (int k = 0; k <= 2; k++) {
                    Location location = new Location(world, x + i, y + 10 + k, z + j);
                    location.getBlock().setType(Material.SPRUCE_LEAVES);
                }
            }
        }
    }

    public static void generateRandomBranches(World world, int x, int y, int z, int height) {
        int numberOfBranches = (int) (Math.random() * 5 + 1);
        for (int i = 0; i < numberOfBranches; i++) {
            double angle = Math.random() * 2 * Math.PI;
            int branchX = (int) (x + Math.cos(angle) * 2);
            int branchZ = (int) (z + Math.sin(angle) * 2);
            int branchLength = (int) (Math.random() * 4 + 2);
            IslandGenerator.generateBranches(world, branchX, y + height, branchZ, branchLength, 0);
        }
    }

    private static void generateBranches(World world, int x, int y, int z, int length, int direction) {
        for (int i = 0; i < length; i++) {
            Location location = new Location(world, x + (i * direction), y + i, z);
            location.getBlock().setType(Material.SPRUCE_LOG);
        }

        // Generate leaves around the base of the branch
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                Location location = new Location(world, x + (length * direction) + i, y + length, z + j);
                location.getBlock().setType(Material.SPRUCE_LEAVES);
            }
        }
    }
}
