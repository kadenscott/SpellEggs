package dev.kscott.spelleggs.menu;

import com.github.stefvanschie.inventoryframework.gui.type.ChestGui;
import com.github.stefvanschie.inventoryframework.gui.type.util.Gui;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public class MenuManager {

    /**
     * Opens the Egg menu for a Player
     * @param player player to show menu to
     */
    public void openMenu(final @NonNull Player player) {
        Gui gui = new ChestGui(5, "popy lol");
        gui.show(player);
    }

}
