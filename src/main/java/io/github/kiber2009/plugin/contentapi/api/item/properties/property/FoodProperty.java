package io.github.kiber2009.plugin.contentapi.api.item.properties.property;

import org.bukkit.inventory.meta.components.FoodComponent;
import org.jetbrains.annotations.Contract;
import org.jspecify.annotations.NonNull;

@SuppressWarnings("UnstableApiUsage")
public class FoodProperty implements Property<FoodComponent> {
    private final int nutrition;
    private final int saturation;
    private final boolean canAlwaysEat;

    public FoodProperty(final int nutrition, final int saturation, final boolean canAlwaysEat) {
        this.nutrition = nutrition;
        this.saturation = saturation;
        this.canAlwaysEat = canAlwaysEat;
    }

    @Override
    @Contract("_ -> param1")
    public @NonNull FoodComponent build(final @NonNull FoodComponent component) {
        component.setNutrition(nutrition);
        component.setSaturation(saturation);
        component.setCanAlwaysEat(canAlwaysEat);
        return component;
    }
}
