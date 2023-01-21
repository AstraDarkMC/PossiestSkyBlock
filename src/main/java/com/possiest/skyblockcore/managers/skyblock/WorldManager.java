package com.possiest.skyblockcore.managers.skyblock;

import com.possiest.skyblockcore.SkyBlockCore;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.UUID;

public class WorldManager implements Listener{
    private HashMap<UUID, Long> lastPlayerLeaveTime = new HashMap<>();
    private final long UNLOAD_DELAY = 5 * 60 * 1000; // 5 minutes in milliseconds
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        UUID worldUUID = player.getUniqueId();
        World world = Bukkit.getWorld(worldUUID.toString());
        if (world != null) {
            player.teleport(world.getSpawnLocation());
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        UUID worldUUID = player.getUniqueId();
        World world = Bukkit.getWorld(worldUUID.toString());
        System.out.println("World Unloaded");
        if (world != null) {
            lastPlayerLeaveTime.put(worldUUID, System.currentTimeMillis());
            scheduleUnload(worldUUID);
        }
    }

    private void scheduleUnload(UUID worldUUID) {
        Bukkit.getScheduler().runTaskLater(SkyBlockCore.getInstance(), () -> {
            World world = Bukkit.getWorld(worldUUID.toString());
            if (world != null && System.currentTimeMillis() - lastPlayerLeaveTime.get(worldUUID) >= UNLOAD_DELAY) {
                Bukkit.unloadWorld(world, true);
            }
        }, UNLOAD_DELAY);
    }


    public static boolean isLoaded(UUID worldUUID) {
        return Bukkit.getWorld(worldUUID.toString()) != null;
    }
}