package com.ancientshores.AncientRPG.Quests;

public class Objective {
    public boolean fulfilled;
    public ObjectiveType type;

    public Objective(String s) {
        parseObjective(s);
    }

    public void parseObjective(String line) {
        if (line.toLowerCase().startsWith("move")) {
            type = ObjectiveType.Move;
        } else if (line.toLowerCase().startsWith("kill")) {
            type = ObjectiveType.Kill;
        } else if (line.toLowerCase().startsWith("mine")) {
            type = ObjectiveType.Mine;
        }
    }

    public void checkObjective() {

    }

    public enum ObjectiveType {
        Move, Kill, Mine;
    }
}