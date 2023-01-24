package com.possiest.skyblockcore.managers.quests;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.UUID;

public class QuestSystem implements Listener {
    private HashMap<UUID, Quest> activeQuests = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        UUID playerId = player.getUniqueId();

        // Check if player has an active quest
        if (!activeQuests.containsKey(playerId)) {
            return;
        }

        Quest quest = activeQuests.get(playerId);

        // Check if the quest objective is completed
        if (event.getClickedBlock().getType() == quest.getObjective()) {
            // Give reward to player
            player.getInventory().addItem(new ItemStack(quest.getReward(), 1));
            player.sendMessage(ChatColor.GREEN + "Quest completed! You received a " + quest.getReward().name());

            // Remove quest from player's quest list
            activeQuests.remove(playerId);
        }
    }

    public void assignQuest(Player player, Quest quest) {
        activeQuests.put(player.getUniqueId(), quest);
        player.sendMessage(ChatColor.GREEN + "You have been assigned a new quest: " + quest.getName());
    }
}

class Quest {
    private final String name;
    private final Material objective;
    private final Material reward;

    public Quest(String name, Material objective, Material reward) {
        this.name = name;
        this.objective = objective;
        this.reward = reward;
    }

    public String getName() {
        return name;
    }

    public Material getObjective() {
        return objective;
    }

    public Material getReward() {
        return reward;
    }
}
