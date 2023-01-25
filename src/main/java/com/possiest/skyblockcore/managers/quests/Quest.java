package com.possiest.skyblockcore.managers.quests;

import org.bukkit.Material;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public abstract class Quest {

    private final String name;
    private final Material reward;
    private final List<UUID> players;

    public Quest(String name, Material reward) {
        this.name = name;
        this.reward = reward;
        this.players = new ArrayList<>();
    }

    public abstract boolean isCompleted(Object event);

    public String getName() {
        return name;
    }

    public Material getReward() {
        return reward;
    }

    public List<UUID> getPlayers() {
        return players;
    }
}
