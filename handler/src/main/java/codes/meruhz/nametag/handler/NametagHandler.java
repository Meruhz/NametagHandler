package codes.meruhz.nametag.handler;

import codes.meruhz.nametag.api.INametagApi;
import codes.meruhz.nametag.api.data.Nametag;
import codes.meruhz.nametag.api.utils.ComponentUtils;
import codes.meruhz.nametag.api.utils.ProtocolVersion;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public final class NametagHandler extends JavaPlugin {

    private final @NotNull Central central;

    public NametagHandler() {
        if(!ProtocolVersion.isCompatible()) {
            throw new UnsupportedOperationException("unsupported version: " + this.getVersion());

        } else {
            @NotNull String classPath = "codes.meruhz.nametag.versions." + this.getVersion() + ".NametagApi";

            try {
                this.central = new Central((INametagApi) Class.forName(classPath).newInstance());
                Bukkit.getServer().getConsoleSender().sendMessage("§8[§2NametagHandler§8] §7API version detected: §6" + this.getVersion() + " §8(" + ProtocolVersion.getVersion().getProtocol() + "§8)");

            } catch (ClassNotFoundException e) {
                throw new RuntimeException("invalid API: " + classPath, e);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("cannot load API: " + classPath, e);
            }
        }
    }

    public @NotNull Central getCentral() {
        return this.central;
    }

    public @NotNull String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }

    public static @NotNull NametagHandler nametagHandler() {
        return NametagHandler.getPlugin(NametagHandler.class);
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this.getCentral(), this);
    }

    public static final class Central implements Listener {

        private @NotNull INametagApi INametagApi;

        public Central(@NotNull INametagApi INametagApi) {
            this.INametagApi = INametagApi;
        }

        public @NotNull INametagApi getNametagApi() {
            return this.INametagApi;
        }

        public void setNametagApi(@NotNull INametagApi INametagApi) {
            this.INametagApi = INametagApi;
        }

        @EventHandler(priority = EventPriority.LOW)
        private void join(@NotNull PlayerJoinEvent e) {
            // When a player joins, update the nametags for all online players
            this.getNametagApi().update(NametagHandler.nametagHandler());
        }

        @EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
        private void chat(@NotNull AsyncPlayerChatEvent e) {
            @NotNull Player player = e.getPlayer();
            @NotNull Nametag nametag = this.getNametagApi().getNametag(player.getUniqueId());

            // Customize the chat format using the player's nametag
            e.setFormat((nametag.getPrefix() == null ? "" : ComponentUtils.getText(nametag.getPrefix())) + (nametag.getColor() + player.getName()) + (nametag.getSuffix() == null ? "" : ComponentUtils.getText(nametag.getSuffix())) + "§f: " + e.getMessage());
        }
    }
}
