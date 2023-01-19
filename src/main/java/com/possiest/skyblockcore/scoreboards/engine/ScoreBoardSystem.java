package com.possiest.skyblockcore.scoreboards.engine;

import java.util.EnumMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class ScoreBoardSystem {

    private ScoreboardManager manager = Bukkit.getScoreboardManager();
    private EnumMap<ScoreboardEnum, Scoreboard> scoreboards = new EnumMap<>(ScoreboardEnum.class);

    public ScoreBoardSystem() {
        for (ScoreboardEnum scoreboardEnum : ScoreboardEnum.values()) {
            Scoreboard scoreboard = manager.getNewScoreboard();
            Objective objective = scoreboard.registerNewObjective(scoreboardEnum.name(), "dummy", ChatColor.RED + scoreboardEnum.getTitle());
            objective.setDisplaySlot(DisplaySlot.SIDEBAR);
            scoreboards.put(scoreboardEnum, scoreboard);
        }
    }

    public void setScoreboard(Player player, ScoreboardEnum scoreboardEnum) {
        player.setScoreboard(scoreboards.get(scoreboardEnum));
    }

    public void setLine(ScoreboardEnum scoreboardEnum, int lineNumber, String text) {
        Scoreboard scoreboard = scoreboards.get(scoreboardEnum);
        Objective objective = scoreboard.getObjective(scoreboardEnum.name());
        Score scoreObject = objective.getScore(text);
        scoreObject.setScore(15 - lineNumber);
    }

    public void updateScore(ScoreboardEnum scoreboardEnum, String playerName, int score) {
        Scoreboard scoreboard = scoreboards.get(scoreboardEnum);
        Objective objective = scoreboard.getObjective(scoreboardEnum.name());
        Score scoreObject = objective.getScore(playerName);
        scoreObject.setScore(score);
    }

    public void updateScoreboard(ScoreboardEnum scoreboardEnum, Map<String, Integer> scores) {
        Scoreboard scoreboard = scoreboards.get(scoreboardEnum);
        Objective objective = scoreboard.getObjective(scoreboardEnum.name());
        scores.forEach((playerName, score) -> {
            Score scoreObject = objective.getScore(playerName);
            scoreObject.setScore(score);
        });
    }

    public void updateEmptyLines(ScoreboardEnum scoreboardEnum, int emptyLines) {
        Scoreboard scoreboard = scoreboards.get(scoreboardEnum);
        Objective objective = scoreboard.getObjective(scoreboardEnum.name());
        for (int i = 0; i < emptyLines; i++) {
            objective.getScore("").setScore(-i - 1);
        }
    }

    public enum ScoreboardEnum {
        EXAMPLE("Example Scoreboard"),
        ANOTHER_EXAMPLE("Another Example Scoreboard");

        private String title;

        ScoreboardEnum(String title) {
            this.title = title;
        }

        public String getTitle() {
            return title;
        }


    }
}
