package dev.kscott.spelleggs.egg;

import dev.kscott.spelleggs.spell.Spell;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * The Egg class
 */
public class Egg {

    /**
     * The id of this Egg
     */
    private final @NonNull String id;

    /**
     * The ItemStack that represents this Egg
     */
    private final @NonNull ItemStack itemStack;

    /**
     * The Spell associated to this Egg
     */
    private final @NonNull Spell spell;

    /**
     * Constructs the Egg
     *
     * @param id {@link this#id}
     * @param itemStack {@link this#itemStack}
     * @param spell {@link this#spell}
     */
    public Egg(
            final @NonNull String id,
            final @NonNull ItemStack itemStack,
            final @NonNull Spell spell
    ) {
        this.id = id;
        this.itemStack = itemStack;
        this.spell = spell;
    }

    /**
     * @return {@link this#id}
     */
    public @NonNull String getId() {
        return id;
    }

    /**
     * @return {@link this#itemStack}
     */
    public @NonNull ItemStack getItemStack() {
        return itemStack;
    }

    /**
     * @return {@link this#spell}
     */
    public @NonNull Spell getSpell() {
        return spell;
    }
}
