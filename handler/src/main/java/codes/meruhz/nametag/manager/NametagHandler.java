package codes.meruhz.nametag.manager;

import codes.meruhz.nametag.api.NametagApi;
import codes.meruhz.nametag.manager.listener.NametagListener;
import codes.meruhz.nametag.manager.loader.NametagLoader;
import codes.meruhz.nametag.manager.loader.ProtocolVersion;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class NametagHandler extends JavaPlugin implements NametagLoader {

    private @NotNull NametagApi nametagApi;

    public NametagHandler() {
        if(!ProtocolVersion.isCompatible()) {
            throw new UnsupportedOperationException("Plugin NametagHandler does not support version " + this.getVersion());

        } else {
            @NotNull String classPath = "codes.meruhz.nametag.versions." + this.getVersion() + ".NametagApi";

            try {
                this.nametagApi = (NametagApi) Class.forName(classPath).newInstance();
                Bukkit.getServer().getConsoleSender().sendMessage("§8[§2NametagHandler§8] §7API version detected: §6" + this.getVersion() + " §8(" + ProtocolVersion.getVersion().getProtocol() + "§8)");

            } catch (ClassNotFoundException e) {
                throw new RuntimeException("NametagHandler API could not be found: " + classPath, e);
            } catch (InstantiationException | IllegalAccessException e) {
                throw new RuntimeException("Failed to initialize NametagHandler API as " + classPath, e);
            }
        }
    }

    @Override
    public @NotNull NametagApi getNametagApi() {
        return this.nametagApi;
    }

    @Override
    public void setNametagApi(@NotNull NametagApi nametagApi) {
        this.nametagApi = nametagApi;
    }

    @Override
    public void onDisable() {
        HandlerList.unregisterAll(this);
    }

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(new NametagListener(), this);
    }

    public static @NotNull NametagHandler handler() {
        return NametagHandler.getPlugin(NametagHandler.class);
    }

    public static void main(String[] args) {
        System.out.println("Hello World!");
    }
}
