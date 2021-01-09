package dev.kscott.spelleggs.egg;

import dev.kscott.spelleggs.spell.SpellRegistry;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * The EggRegistry
 */
public class EggRegistry {

    /**
     * The Map of Egg id to Egg
     */
    private final @NonNull Map<String, Egg> eggMap;

    /**
     * EggRegistry reference.
     */
    private final @NonNull SpellRegistry spellRegistry;

    /**
     * JavaPlugin reference.
     */
    private final @NonNull JavaPlugin plugin;

    /**
     * The NamespacedKey used for storing the egg id on entities and ItemStacks.
     */
    private final @NonNull NamespacedKey eggIdKey;

    /**
     * Constructs the EggRegistry
     */
    public EggRegistry(
            final @NonNull JavaPlugin plugin,
            final @NonNull SpellRegistry spellRegistry
    ) {
        this.plugin = plugin;
        this.eggMap = new HashMap<>();
        this.spellRegistry = spellRegistry;
        this.eggIdKey = new NamespacedKey(plugin, "egg_id");

        this.registerEggs();
    }

    /**
     * Registers default eggs.
     */
    private void registerEggs() {
        // Explode egg
        registerEgg(
                Egg.createEgg(
                        "explode",
                        Component.text("Explode Egg").color(NamedTextColor.RED),
                        Material.BLAZE_SPAWN_EGG,
                        spellRegistry.getSpell("explode"),
                        eggIdKey
                )
        );
    }

    /**
     * Registers an Egg within the map
     *
     * @param egg The Egg to register
     */
    public void registerEgg(final @NonNull Egg egg) {
        this.eggMap.put(egg.getId(), egg);
    }

    /**
     * Returns an Egg
     *
     * @param id ID of the Egg
     * @return The Egg. May be null if no Egg is registered with the given id.
     */
    public @Nullable Egg getEgg(final @NonNull String id) {
        return this.eggMap.get(id);
    }

    /**
     * @return A Collection of all registered eggs.
     */
    public @NonNull Collection<Egg> getEggs() {
        return this.eggMap.values();
    }

}
