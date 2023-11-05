package codes.meruhz.nametag.manager.listener;

import codes.meruhz.nametag.api.data.Nametag;
import codes.meruhz.nametag.api.utils.ComponentUtils;
import codes.meruhz.nametag.manager.NametagHandler;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

public class NametagListener implements Listener {

    @EventHandler(priority = EventPriority.LOW)
    private void join(@NotNull PlayerJoinEvent e) {
        // When a player joins, update the nametags for all online players
        NametagHandler.handler().getNametagApi().update(NametagHandler.handler());
    }

    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
    private void chat(@NotNull AsyncPlayerChatEvent e) {
        @NotNull Player player = e.getPlayer();
        @NotNull Nametag nametag = NametagHandler.handler().getNametagApi().getNametag(player.getUniqueId());

        // Customize the chat format using the player's nametag
        e.setFormat((nametag.getPrefix() == null ? "" : ComponentUtils.getText(nametag.getPrefix())) + (nametag.getColor() + player.getName()) + (nametag.getSuffix() == null ? "" : ComponentUtils.getText(nametag.getSuffix())) + "§f: " + e.getMessage());
    }
}
