package codes.meruhz.nametag.manager.loader;

import codes.meruhz.nametag.api.NametagApi;
import org.bukkit.Bukkit;
import org.jetbrains.annotations.NotNull;

public interface NametagLoader {

    /**
     * Get the nametag API instance.
     *
     * @return The NametagApi instance for managing nametags.
     */
    @NotNull NametagApi getNametagApi();

    /**
     * Set the nametag API instance.
     *
     * @param nametagApi The NametagApi instance to set for managing nametags.
     */
    void setNametagApi(@NotNull NametagApi nametagApi);

    /**
     * Get the version of the Minecraft server software.
     *
     * @return A string representing the Minecraft server software version.
     */
    default @NotNull String getVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];
    }
}
