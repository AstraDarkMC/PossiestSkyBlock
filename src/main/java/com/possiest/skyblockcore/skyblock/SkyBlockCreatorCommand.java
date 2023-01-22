package com.possiest.skyblockcore.skyblock;

import com.possiest.skyblockcore.managers.skyblock.generator.IslandGenerator;
import com.possiest.skyblockcore.managers.skyblock.generator.VoidGen;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

public class SkyBlockCreatorCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command can only be executed by a player.");
            return true;
        }
        Player player = (Player) sender;
        UUID playerUUID = player.getUniqueId();
        String worldName = playerUUID.toString();
        World isworld = Bukkit.getWorld(worldName);
        Material blockType = Material.STONE; //default value
        if(args.length > 0) {
            blockType = Material.getMaterial(args[0]);
        }
        // check if the world already exists
        if(Bukkit.getWorld(playerUUID.toString()) != null) {
            worldName = String.valueOf(Bukkit.getWorld(playerUUID.toString()));
            Location isspawn = isworld.getSpawnLocation();
            isspawn.setY(100);
            isspawn.setX(0.5);
            isspawn.setZ(0.5);
            player.teleport(isspawn);
            player.sendMessage("Teleporting to SkyBlock world...");
            return true;
        } else {
            // Create new void world
            WorldCreator creator = new WorldCreator(worldName);
            creator.generator(new VoidGen());
            World newWorld = creator.createWorld();
            Location isspawn = newWorld.getSpawnLocation();
            isspawn.setY(100);
            isspawn.setX(0.5);
            isspawn.setZ(0.5);
            player.teleport(isspawn);
            //set block on spawn
            Block block = newWorld.getSpawnLocation().getBlock();
            block.setType(blockType);
            IslandGenerator.generateIsland(newWorld, 0, 90,0 );
                System.out.println("Trees Generated");
            }
            System.out.println("Not Trees Generated");
            player.sendMessage("Generating SkyBlock world...");
            return true;
    }
}
