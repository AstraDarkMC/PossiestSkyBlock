package com.possiest.skyblockcore.managers.skyblock.generator;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class IslandGenerator {
    public static final int ISLAND_SIZE = 80;
    public static final int ISLAND_CENTER = ISLAND_SIZE / 2;
    public static final int ISLAND_RADIUS = ISLAND_SIZE / 4;
    public static final int ISLAND_HEIGHT = 20;

    public static void generateIsland(World world, int islandX, int islandY, int islandZ) {
        SimplexOctaveGenerator generator = new SimplexOctaveGenerator(world, 8);
        generator.setScale(0.05);

        for (int x = islandX - ISLAND_CENTER; x < islandX + ISLAND_CENTER; x++) {
            for (int z = islandZ - ISLAND_CENTER; z < islandZ + ISLAND_CENTER; z++) {
                double distance = Math.sqrt(Math.pow(x - islandX, 2) + Math.pow(z - islandZ, 2));
                if (distance > ISLAND_RADIUS) {
                    continue;
                }
                double noise = generator.noise(x, z, 0.5, 0.5) * ISLAND_HEIGHT;
                int height = (int) (ISLAND_RADIUS - distance + noise);
                for (int y = islandY; y > islandY - height; y--) {
                    world.getBlockAt(x, y, z).setType(Material.STONE);
                }
                world.getBlockAt(x, islandY - height, z).setType(Material.GRASS_BLOCK);

                //Filling gaps
                for(int y = islandY - height - 1; y > islandY - height - 5; y--) {
                    if(world.getBlockAt(x, y, z).getType() == Material.AIR) {
                        world.getBlockAt(x, y, z).setType(Material.DIRT);
                    }
                }
                //Smoothing out the terrain
                for(int y = islandY - height; y > islandY - height - 5; y--) {
                    if(world.getBlockAt(x-1, y, z).getType() != world.getBlockAt(x, y, z).getType()) {
                        world.getBlockAt(x-1, y, z).setType(world.getBlockAt(x, y, z).getType());
                    }
                    if(world.getBlockAt(x+1, y, z).getType() != world.getBlockAt(x, y, z).getType()) {
                        world.getBlockAt(x+1, y, z).setType(world.getBlockAt(x, y, z).getType());
                    }
                    if(world.getBlockAt(x, y, z-1).getType() != world.getBlockAt(x, y, z).getType()) {
                        world.getBlockAt(x, y, z-1).setType(world.getBlockAt(x, y, z).getType());
                    }
                    if(world.getBlockAt(x, y, z+1).getType() != world.getBlockAt(x, y, z).getType()) {
                        world.getBlockAt(x, y, z+1).setType(world.getBlockAt(x, y, z).getType());
                    }
                }
            }
        }
    }
}