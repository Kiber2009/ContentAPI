package io.github.kiber2009.plugin.contentapi.api.item.properties.property;

import io.github.kiber2009.plugin.contentapi.api.collections.bukkit.BukkitCollection;
import org.bukkit.Material;
import org.bukkit.inventory.meta.components.ToolComponent;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@SuppressWarnings("UnstableApiUsage")
public class ToolProperty implements Property<ToolComponent> {
    private final float defaultMiningSpeed; // Default 1
    private final int damagePerBlock; // Default 1
    private final List<ToolRuleInfo> toolRules;

    public ToolProperty(final float defaultMiningSpeed, final int damagePerBlock,
                        final @NonNull List<@NonNull ToolRuleInfo> toolRules) {
        this.defaultMiningSpeed = defaultMiningSpeed;
        this.damagePerBlock = damagePerBlock;
        this.toolRules = toolRules;
    }

    @Override
    public @NonNull ToolComponent build(final @NonNull ToolComponent component) {
        component.setDefaultMiningSpeed(defaultMiningSpeed);
        component.setDamagePerBlock(damagePerBlock);
        // TODO: Remove all rules?????
        for (final ToolComponent.ToolRule rule : component.getRules())
            component.removeRule(rule);
        for (final ToolRuleInfo rule : toolRules)
            rule.build(component);
        return component;
    }

    public static class Builder {
        private float defaultMiningSpeed = 1;
        private int damagePerBlock = 1;
        private final List<ToolRuleInfo> toolRules = new ArrayList<>();

        @Contract("_ -> this")
        public @NonNull Builder defaultMiningSpeed(final float speed) {
            defaultMiningSpeed = speed;
            return this;
        }

        @Contract("_ -> this")
        public @NonNull Builder damagePerBlock(final int damage) {
            damagePerBlock = damage;
            return this;
        }

        @Contract("_ -> this")
        public @NonNull Builder toolRule(final @NonNull ToolRuleInfo @NonNull ... rules) {
            Collections.addAll(toolRules, rules);
            return this;
        }

        @Contract("_ -> this")
        public @NonNull Builder toolRule(final @NonNull Collection<? extends @NonNull ToolRuleInfo> rules) {
            toolRules.addAll(rules);
            return this;
        }

        @Contract("_, _, _ -> this")
        public @NonNull Builder toolRule(final @NonNull BukkitCollection<@NonNull Material> blocks,
                                         final @Nullable Float speed, final @Nullable Boolean correctForDrops) {
            return toolRule(new ToolRuleInfo(blocks, speed, correctForDrops));
        }

        @Contract(value = "-> new", pure = true)
        public @NonNull ToolProperty build() {
            return new ToolProperty(defaultMiningSpeed, damagePerBlock, toolRules);
        }
    }

    public static class ToolRuleInfo {
        private final BukkitCollection<Material> blocks;
        private final Float speed;
        private final Boolean correctForDrops;

        public ToolRuleInfo(final @NonNull BukkitCollection<@NonNull Material> blocks, final @Nullable Float speed,
                            final @Nullable Boolean correctForDrops) {
            this.blocks = blocks;
            this.speed = speed;
            this.correctForDrops = correctForDrops;
        }

        public void build(final @NonNull ToolComponent component) {
            blocks.accept(collection -> component.addRule(collection, speed, correctForDrops),
                    tag -> component.addRule(tag, speed, correctForDrops),
                    material -> component.addRule(material, speed, correctForDrops)
            );
        }
    }
}
