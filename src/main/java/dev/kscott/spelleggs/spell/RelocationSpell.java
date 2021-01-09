package dev.kscott.spelleggs.spell;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.data.BlockData;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.graalvm.compiler.lir.alloc.lsra.LinearScan;

import java.util.Collection;
import java.util.Random;

public class RelocationSpell extends Spell {

    final int localX = 7;
    final int localY = 3;
    final int localZ = 7;
    final int teleportationHeight = 20;

    final @NonNull Random random;

    public RelocationSpell() {
        super("relocation");
        random = new Random();
    }

    @Override
    public void onActivation(@NonNull Location location, @NonNull Player player) {
        final @NonNull World world = location.getWorld();

        final @NonNull Collection<LivingEntity> entities = location.getNearbyLivingEntities(localX, localY, localZ);

        final int minX = (int) location.getX()-localX;
        final int maxX = (int) location.getX()+localX;

        final int minY = (int) location.getY()-localY;
        final int maxY = (int) location.getY()+localY;

        final int minZ = (int) location.getZ()-localZ;
        final int maxZ = (int) location.getZ()+localZ;

        for (int x = minX; x <= maxX; x++) {
            for (int y = minY; y <= maxY; y++) {
                for (int z = minZ; z <= maxZ; z++) {

                    boolean teleport = new Random().nextInt(10) >= 3;

                    if (teleport) {
                        final @NonNull Block block =  world.getBlockAt(x, y, z);

                        final @NonNull BlockData blockData = block.getBlockData().clone();

                        block.setType(Material.AIR);

                        world.getBlockAt(x, y+teleportationHeight, z).setBlockData(blockData);
                    }
                }
            }
        }

        for (final LivingEntity entity : entities) {
            final @NonNull Location entityLocation = entity.getLocation();

            entityLocation.add(0, teleportationHeight, 0);

            entity.teleportAsync(entityLocation);
        }
    }
}