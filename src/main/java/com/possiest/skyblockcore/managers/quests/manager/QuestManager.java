package com.possiest.skyblockcore.managers.quests.manager;

import com.possiest.skyblockcore.managers.quests.Quest;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.UUID;

public class QuestManager {

    private HashMap<UUID, Quest> activeQuests = new HashMap<>();

    public void assignQuest(Player player, Quest quest) {
        quest.getPlayers().add(player.getUniqueId());
        activeQuests.put(player.getUniqueId(), quest);
        player.sendMessage(ChatColor.GREEN + "You have been assigned a new quest: " + quest.getName());
    }

    public void checkQuestCompletion(Player player, Object event) {
        UUID playerId = player.getUniqueId();
        if (!activeQuests.containsKey(playerId)) {
            return;
        }
        Quest quest = activeQuests.get(playerId);
        if (quest.isCompleted(event)) {
            player.getInventory().addItem(new ItemStack(quest.getReward(), 1));
            player.sendMessage(ChatColor.GREEN + "Quest completed! You received a " + quest.getReward().name());
            activeQuests.remove(playerId);
        }
    }
}

