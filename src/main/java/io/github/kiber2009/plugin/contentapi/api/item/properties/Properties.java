package io.github.kiber2009.plugin.contentapi.api.item.properties;

import io.github.kiber2009.plugin.contentapi.api.item.CustomItem;
import io.github.kiber2009.plugin.contentapi.api.item.properties.property.*;
import io.github.kiber2009.plugin.contentapi.api.persistence.ContentApiPersistentDataType;
import net.kyori.adventure.text.Component;
import org.bukkit.JukeboxSong;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.Tag;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.damage.DamageType;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemRarity;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.components.JukeboxPlayableComponent;
import org.bukkit.tag.DamageTypeTags;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import java.util.*;

public class Properties {
    private static final Material DEFAULT_MATERIAL = Material.NETHER_BRICK;

    private final Material material;
    private Integer maxStackSize = 64;
    private Component name;
    private List<? extends Component> lore = List.of();
    private NamespacedKey model;
    private ItemRarity rarity = ItemRarity.COMMON;
    private boolean glider;
    private NamespacedKey tooltipStyle;
    private boolean hideTooltip;
    private NamespacedKey jukeboxPlayable;
    private FoodProperty food;
    private Tag<DamageType> damageResistant;
    private boolean enchantmentGlintOverride;
    private boolean unbreakable;
    private Integer enchantable;
    private final Set<ItemFlag> itemFlags = new HashSet<>();
    private ItemStack useRemainder;
    private UseCooldownProperty useCooldown;
    private CustomModelDataProperty customModelData;
    private final List<EnchantmentInfo> enchantments = new ArrayList<>();
    private final List<AttributeModifierInfo> attributeModifiers = new ArrayList<>();
    private EquippableProperty equippable;
    private ToolProperty tool;

    public Properties(final @NonNull Material material) {
        this.material = material;
    }

    public Properties() {
        this(DEFAULT_MATERIAL);
    }

    @Contract("_ -> this")
    public @NonNull Properties maxStackSize(final int max) {
        maxStackSize = max;
        return this;
    }

    @Contract("_ -> this")
    public @NonNull Properties name(final @Nullable Component name) {
        this.name = name;
        return this;
    }

    @Contract("_ -> this")
    public @NonNull Properties lore(final @Nullable List<? extends Component> lore) {
        this.lore = lore;
        return this;
    }

    @Contract("_ -> this")
    public @NonNull Properties model(final @Nullable NamespacedKey model) {
        this.model = model;
        return this;
    }

    @Contract("_ -> this")
    public @NonNull Properties rarity(final @Nullable ItemRarity rarity) {
        this.rarity = rarity;
        return this;
    }

    @Contract("_ -> this")
    public @NonNull Properties glider(final boolean glider) {
        this.glider = glider;
        return this;
    }

    @Contract(" -> this")
    public @NonNull Properties glider() {
        return glider(true);
    }

    @Contract("_ -> this")
    public @NonNull Properties tooltipStyle(final @Nullable NamespacedKey tooltipStyle) {
        this.tooltipStyle = tooltipStyle;
        return this;
    }

    @Contract("_ -> this")
    public @NonNull Properties hideTooltip(final boolean hideTooltip) {
        this.hideTooltip = hideTooltip;
        return this;
    }

    @Contract("-> this")
    public @NonNull Properties hideTooltip() {
        return hideTooltip(true);
    }

    @Contract("_ -> this")
    public @NonNull Properties jukeboxPlayable(final @Nullable NamespacedKey song) {
        jukeboxPlayable = song;
        return this;
    }

    @Contract("_ -> this")
    public @NonNull Properties jukeboxPlayable(final @NonNull JukeboxSong song) {
        return jukeboxPlayable(song.getKey());
    }

    @Contract("_ -> this")
    public @NonNull Properties food(final @Nullable FoodProperty food) {
        this.food = food;
        return this;
    }

    @Contract("_ -> this")
    public @NonNull Properties damageResistant(final @Nullable Tag<DamageType> damage) {
        damageResistant = damage;
        return this;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Contract("-> this")
    public @NonNull Properties fireResistant() {
        return damageResistant(DamageTypeTags.IS_FIRE);
    }

    @Contract("_ -> this")
    public @NonNull Properties enchantmentGlintOverride(final boolean override) {
        enchantmentGlintOverride = override;
        return this;
    }

    @Contract("-> this")
    public @NonNull Properties enchantmentGlintOverride() {
        return enchantmentGlintOverride(true);
    }

    @Contract("_ -> this")
    public @NonNull Properties unbreakable(final boolean unbreakable) {
        this.unbreakable = unbreakable;
        return this;
    }

    @Contract("-> this")
    public @NonNull Properties unbreakable() {
        return unbreakable(true);
    }

    @Contract("_ -> this")
    public @NonNull Properties enchantable(final @Nullable Integer enchantable) {
        this.enchantable = enchantable;
        return this;
    }

    @Contract("_ -> this")
    public @NonNull Properties itemFlags(final @NonNull ItemFlag @NonNull ... flags) {
        Collections.addAll(itemFlags, flags);
        return this;
    }

    @Contract("_ -> this")
    public @NonNull Properties useRemainder(final @Nullable ItemStack remainder) {
        useRemainder = remainder;
        return this;
    }

    @Contract("_ -> this")
    public @NonNull Properties useCooldown(final @Nullable UseCooldownProperty cooldown) {
        useCooldown = cooldown;
        return this;
    }

    @Contract("_ -> this")
    public @NonNull Properties customModelData(final @Nullable CustomModelDataProperty customModelData) {
        this.customModelData = customModelData;
        return this;
    }

    @Contract("_ -> this")
    public @NonNull Properties enchant(final @NonNull EnchantmentInfo @NonNull ... enchantments) {
        Collections.addAll(this.enchantments, enchantments);
        return this;
    }

    @Contract("_ -> this")
    public @NonNull Properties enchant(final @NonNull Collection<@NonNull EnchantmentInfo> enchantments) {
        this.enchantments.addAll(enchantments);
        return this;
    }

    @Contract("_, _, _ -> this")
    public @NonNull Properties enchant(final @NonNull Enchantment enchant, final int level,
                                       final boolean ignoreLevelRestriction) {
        return enchant(new EnchantmentInfo(enchant, level, ignoreLevelRestriction));
    }

    @Contract("_ -> this")
    public @NonNull Properties attributeModifier(final @NonNull AttributeModifierInfo @NonNull ... modifiers) {
        Collections.addAll(attributeModifiers, modifiers);
        return this;
    }

    @Contract("_ -> this")
    public @NonNull Properties attributeModifier(final @NonNull Collection<@NonNull AttributeModifierInfo> modifiers) {
        attributeModifiers.addAll(modifiers);
        return this;
    }

    @Contract("_, _ -> this")
    public @NonNull Properties attributeModifier(final @NonNull Attribute attribute,
                                                 final @NonNull AttributeModifier modifier) {
        return attributeModifier(new AttributeModifierInfo(attribute, modifier));
    }

    @Contract("_ -> this")
    public @NonNull Properties equippable(final @Nullable EquippableProperty equippable) {
        this.equippable = equippable;
        return this;
    }

    @Contract("_ -> this")
    public @NonNull Properties tool(final @Nullable ToolProperty tool) {
        this.tool = tool;
        return this;
    }

    @SuppressWarnings("UnstableApiUsage")
    @Contract(value = "_, _ -> new", pure = true)
    public @NonNull ItemStack getItemStack(final @NonNull NamespacedKey id, final int amount) {
        final ItemStack stack = ItemStack.of(material);

        stack.editMeta(meta -> {
            meta.setMaxStackSize(maxStackSize);
            meta.itemName(Objects.requireNonNullElse(name,
                    Component.translatable("item." + id.getNamespace() + '.' + id.getKey())));
            meta.lore(lore);
            meta.setItemModel(Objects.requireNonNullElse(model, id));
            meta.setRarity(rarity);
            meta.setGlider(glider);
            meta.setTooltipStyle(tooltipStyle);
            meta.setHideTooltip(hideTooltip);
            if (jukeboxPlayable != null) {
                final JukeboxPlayableComponent jukeboxPlayable = meta.getJukeboxPlayable();
                jukeboxPlayable.setSongKey(this.jukeboxPlayable);
                meta.setJukeboxPlayable(jukeboxPlayable);
            } else
                meta.setJukeboxPlayable(null);
            meta.setFood(food != null ? food.build(meta.getFood()) : null);
            meta.setDamageResistant(damageResistant);
            meta.setEnchantmentGlintOverride(enchantmentGlintOverride);
            meta.setUnbreakable(unbreakable);
            meta.setEnchantable(enchantable);
            meta.addItemFlags(itemFlags.toArray(new ItemFlag[0]));
            meta.setUseRemainder(useRemainder);
            meta.setUseCooldown(useCooldown != null ? useCooldown.build(meta.getUseCooldown()) : null);
            meta.setCustomModelDataComponent(customModelData != null ?
                    customModelData.build(meta.getCustomModelDataComponent()) : null);
            for (final EnchantmentInfo enchantment : enchantments)
                enchantment.build(meta);
            for (final AttributeModifierInfo modifier : attributeModifiers)
                modifier.build(meta);
            meta.setEquippable(equippable != null ? equippable.build(meta.getEquippable()) : null);
            meta.setTool(tool != null ? tool.build(meta.getTool()) : null);
        });

        stack.editPersistentDataContainer(container ->
                container.set(CustomItem.CUSTOM_ITEM_TAG, ContentApiPersistentDataType.NAMESPACED_KEY, id));

        stack.setAmount(amount);

        return stack;
    }

    public static class EnchantmentInfo {
        private final Enchantment enchant;
        private final int level;
        private final boolean ignoreLevelRestriction;

        public EnchantmentInfo(final Enchantment enchant, final int level, final boolean ignoreLevelRestriction) {
            this.enchant = enchant;
            this.level = level;
            this.ignoreLevelRestriction = ignoreLevelRestriction;
        }

        public void build(final @NonNull ItemMeta meta) {
            meta.addEnchant(enchant, level, ignoreLevelRestriction);
        }
    }

    public static class AttributeModifierInfo {
        private final Attribute attribute;
        private final AttributeModifier modifier;

        public AttributeModifierInfo(final @NonNull Attribute attribute, final @NonNull AttributeModifier modifier) {
            this.attribute = attribute;
            this.modifier = modifier;
        }

        public void build(final @NonNull ItemMeta meta) {
            meta.addAttributeModifier(attribute, modifier);
        }
    }
}
