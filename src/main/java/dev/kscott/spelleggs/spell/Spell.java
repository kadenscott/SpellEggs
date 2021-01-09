package dev.kscott.spelleggs.spell;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class Spell {

    /**
     * The id of this Spell
     */
    protected final @NonNull String id;

    /**
     * Constructs the spell
     *
     * @param id {@link this#id}
     */
    public Spell(
            final @NonNull String id) {
        this.id = id;
    }

    /**
     * This method is called when the Spell is activated
     *
     * @param location Location of the activation
     * @param player   The player who activated the Spell
     */
    public abstract void onActivation(
            final @NonNull Location location,
            final @NonNull Player player
    );

    public @NonNull String getId() {
        return id;
    }
}
