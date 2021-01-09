package dev.kscott.spelleggs.spell;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class ExplodeSpell extends Spell {

    public ExplodeSpell() {
        super("explode");
    }

    @Override
    public void onActivation(@NonNull Location location, @NonNull Player player) {
        location.createExplosion(10);
    }
}
