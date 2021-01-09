package dev.kscott.spelleggs.spell;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.HashMap;
import java.util.Map;

public class SpellRegistry {

    private final @NonNull Map<String, Spell> spellMap;

    public SpellRegistry() {
        this.spellMap = new HashMap<>();

        this.registerSpell(new ExplodeSpell());
        this.registerSpell(new HealSpell());
        this.registerSpell(new RelocationSpell());
    }

    /**
     * Registers an Spell within the map
     *
     * @param spell The Spell to register
     */
    public void registerSpell(final @NonNull Spell spell) {
        this.spellMap.put(spell.getId(), spell);
    }

    /**
     * Returns an Spell
     *
     * @param id ID of the Spell
     * @return The Spell. May be null if no Egg is registered with the given id.
     */
    public @Nullable Spell getSpell(final @NonNull String id) {
        return this.spellMap.get(id);
    }

}
