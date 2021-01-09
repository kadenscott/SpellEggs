package dev.kscott.spelleggs.spell;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Random;

public class RelocationSpell extends Spell {

    final int localX = 10;
    final int localY = 5;
    final int localZ = 10;

    final @NonNull Random random;

    public RelocationSpell() {
        super("relocation");
        random = new Random();
    }

    @Override
    public void onActivation(@NonNull Location location, @NonNull Player player) {
        final @NonNull World world = location.getWorld();

        for (int x = (int) location.getX()-localX; x <= (int) location.getX()+localX; x++ ){
            for (int y = (int) location.getY()-localY; y <= (int) location.getX()+localY; y++ ){
                for (int z = (int) location.getZ()-localZ; z <= (int) location.getX()+localZ; z++ ){
                    final @NonNull Block block =  world.getBlockAt(x, y, z);
                    block.setType(Material.AIR);
                }
            }
        }
    }
}
