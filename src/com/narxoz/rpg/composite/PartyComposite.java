package com.narxoz.rpg.composite;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PartyComposite implements CombatNode {
    private final String name;
    private final List<CombatNode> children = new ArrayList<>();

    public PartyComposite(String name) {
        this.name = name;
    }

    public void add(CombatNode node) {
        children.add(node);
    }

    public void remove(CombatNode node) {
        children.remove(node);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public int getHealth() {
        int total = 0;
        for (CombatNode child : children) {
            total += child.getHealth();
        }
        return total;
    }

    @Override
    public int getAttackPower() {
        int total = 0;
        for (CombatNode child : children) {
            total += child.getAttackPower();
        }
        return total;
    }

    @Override
    public void takeDamage(int amount) {
        if (amount <= 0) {
            return;
        }
        List<CombatNode> aliveChildren = getAliveChildren();
        if (aliveChildren.isEmpty()) {
            return;
        }
        int perChild = amount / aliveChildren.size();
        int remainder = amount % aliveChildren.size();
        for (int i = 0; i < aliveChildren.size(); i++) {
            int extra = i < remainder ? 1 : 0;
            aliveChildren.get(i).takeDamage(perChild + extra);
        }
    }

    @Override
    public boolean isAlive() {
        for (CombatNode child : children) {
            if (child.isAlive()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<CombatNode> getChildren() {
        return Collections.unmodifiableList(children);
    }

    @Override
    public void printTree(String indent) {
        System.out.println(indent + "+ " + name + " [HP=" + getHealth() + ", ATK=" + getAttackPower() + "]");
        String childIndent = indent + "  ";
        for (CombatNode child : children) {
            child.printTree(childIndent);
        }
    }

    private List<CombatNode> getAliveChildren() {
        List<CombatNode> alive = new ArrayList<>();
        for (CombatNode child : children) {
            if (child.isAlive()) {
                alive.add(child);
            }
        }
        return alive;
    }
}
