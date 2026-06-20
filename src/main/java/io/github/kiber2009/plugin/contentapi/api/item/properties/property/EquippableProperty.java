package io.github.kiber2009.plugin.contentapi.api.item.properties.property;

import io.github.kiber2009.plugin.contentapi.api.collections.bukkit.BukkitCollection;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.meta.components.EquippableComponent;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

@SuppressWarnings("UnstableApiUsage")
public class EquippableProperty implements Property<EquippableComponent> {
    private final EquipmentSlot slot;
    private final Sound equipSound;
    private final NamespacedKey model;
    private final NamespacedKey cameraOverlay;
    private final BukkitCollection<EntityType> allowedEntities;
    private final boolean dispensable;
    private final boolean swappable;
    private final boolean damageOnHurt;
    private final boolean equipOnInteract;

    public EquippableProperty(final @NonNull EquipmentSlot slot, final @Nullable Sound equipSound,
                              final @Nullable NamespacedKey model, final @Nullable NamespacedKey cameraOverlay,
                              final @Nullable BukkitCollection<@NonNull EntityType> allowedEntities,
                              final boolean dispensable, final boolean swappable, final boolean damageOnHurt,
                              final boolean equipOnInteract) {
        this.slot = slot;
        this.equipSound = equipSound;
        this.model = model;
        this.cameraOverlay = cameraOverlay;
        this.allowedEntities = allowedEntities;
        this.dispensable = dispensable;
        this.swappable = swappable;
        this.damageOnHurt = damageOnHurt;
        this.equipOnInteract = equipOnInteract;
    }

    @Override
    @Contract("_ -> param1")
    public @NonNull EquippableComponent build(final @NonNull EquippableComponent component) {
        component.setSlot(slot);
        component.setEquipSound(equipSound);
        component.setModel(model);
        component.setCameraOverlay(cameraOverlay);
        if (allowedEntities != null)
            allowedEntities.accept(component::setAllowedEntities, component::setAllowedEntities,
                    component::setAllowedEntities);
        else
            component.setAllowedEntities((EntityType) null);
        component.setDispensable(dispensable);
        component.setSwappable(swappable);
        component.setDamageOnHurt(damageOnHurt);
        component.setEquipOnInteract(equipOnInteract);
        return component;
    }

    public static class Builder {
        private final EquipmentSlot slot;
        private Sound equipSound;
        private NamespacedKey model;
        private NamespacedKey cameraOverlay;
        private BukkitCollection<EntityType> allowedEntities;
        private boolean dispensable;
        private boolean swappable;
        private boolean damageOnHurt;
        private boolean equipOnInteract;

        public Builder(final @NonNull EquipmentSlot slot) {
            this.slot = slot;
        }

        @Contract("_ -> this")
        public @NonNull Builder equipSound(final @Nullable Sound sound) {
            equipSound = sound;
            return this;
        }

        @Contract("_ -> this")
        public @NonNull Builder model(final @Nullable NamespacedKey model) {
            this.model = model;
            return this;
        }

        @Contract("_ -> this")
        public @NonNull Builder cameraOverlay(final @Nullable NamespacedKey overlay) {
            cameraOverlay = overlay;
            return this;
        }

        @Contract("_ -> this")
        public @NonNull Builder allowedEntities(final @Nullable BukkitCollection<@NonNull EntityType> entities) {
            allowedEntities = entities;
            return this;
        }

        @Contract("_ -> this")
        public @NonNull Builder dispensable(final boolean dispensable) {
            this.dispensable = dispensable;
            return this;
        }

        @Contract("-> this")
        public @NonNull Builder dispensable() {
            return dispensable(true);
        }

        @Contract("_ -> this")
        public @NonNull Builder swappable(final boolean swappable) {
            this.swappable = swappable;
            return this;
        }

        @Contract("-> this")
        public @NonNull Builder swappable() {
            return swappable(true);
        }

        @Contract("_ -> this")
        public @NonNull Builder damageOnHurt(final boolean damage) {
            damageOnHurt = damage;
            return this;
        }

        @Contract("-> this")
        public @NonNull Builder damageOnHurt() {
            return damageOnHurt(true);
        }

        @Contract("_ -> this")
        public @NonNull Builder equipOnInteract(final boolean equip) {
            equipOnInteract = equip;
            return this;
        }

        @Contract("-> this")
        public @NonNull Builder equipOnInteract() {
            return equipOnInteract(true);
        }

        @Contract(value = "-> new", pure = true)
        public EquippableProperty build() {
            return new EquippableProperty(slot, equipSound, model, cameraOverlay, allowedEntities, dispensable,
                    swappable, damageOnHurt, equipOnInteract);
        }
    }
}
