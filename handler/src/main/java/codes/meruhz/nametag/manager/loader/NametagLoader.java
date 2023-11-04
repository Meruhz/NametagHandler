package codes.meruhz.nametag.manager.loader;

import codes.meruhz.nametag.api.NametagApi;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public interface NametagLoader {

    @NotNull NametagApi getNametagApi();

    void setNametagApi(@NotNull NametagApi nametagApi);

    default @NotNull String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }
}