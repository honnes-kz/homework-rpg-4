package com.narxoz.rpg.bridge;

import com.narxoz.rpg.composite.CombatNode;

public class SingleTargetSkill extends Skill {
    public SingleTargetSkill(String skillName, int basePower, EffectImplementor effect) {
        super(skillName, basePower, effect);
    }

    @Override
    public void cast(CombatNode target) {
        if (target == null) {
            return;
        }
        CombatNode leafTarget = findFirstAliveLeaf(target);
        if (leafTarget == null) {
            return;
        }
        int damage = resolvedDamage();
        leafTarget.takeDamage(damage);
    }

    private CombatNode findFirstAliveLeaf(CombatNode node) {
        if (node == null || !node.isAlive()) {
            return null;
        }
        if (node.getChildren().isEmpty()) {
            return node;
        }
        for (CombatNode child : node.getChildren()) {
            CombatNode found = findFirstAliveLeaf(child);
            if (found != null) {
                return found;
            }
        }
        return null;
    }
}
