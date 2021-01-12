package dev.kscott.spelleggs.menu;

import com.github.stefvanschie.inventoryframework.gui.GuiItem;
import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import dev.kscott.spelleggs.egg.Egg;
import dev.kscott.spelleggs.egg.EggRegistry;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.NonNull;

public class MenuManager {

    private final @NonNull EggRegistry eggRegistry;

    public MenuManager(
            final @NonNull EggRegistry eggRegistry
    ) {
        this.eggRegistry = eggRegistry;
    }


    /**
     * Opens the Egg menu for a Player
     *
     * @param player player to show menu to
     */
    public void openMenu(final @NonNull Player player) {
        ChestGui gui = new ChestGui(5, "popy lol");

        gui.setOnGlobalClick(event -> event.setCancelled(true));

        final @NonNull ItemStack bgItemStack = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        final @NonNull ItemMeta itemMeta = bgItemStack.getItemMeta();
        itemMeta.setDisplayName("");
        bgItemStack.setItemMeta(itemMeta);

        final @NonNull StaticPane bgPane = new StaticPane(0, 0, 9, 5);
        bgPane.fillWith(bgItemStack);

        final @NonNull StaticPane eggPane = new StaticPane(0, 0, 9, 5);

        int x = 0;
        int y = 0;

        for (final Egg egg : eggRegistry.getEggs()) {
            if (x > 9) {
                x = 0;
                y++;
            }

            final @NonNull ItemStack eggItemStack = egg.getItemStack();

            eggPane.addItem(new GuiItem(eggItemStack, clickEvent -> {
                final @NonNull Player whoClicked = (Player) clickEvent.getWhoClicked();
                whoClicked.getInventory().addItem(eggItemStack);
            }), x, y);

            x++;
        }

        gui.addPane(eggPane);
        gui.addPane(bgPane);

        gui.show(player);
    }

}
