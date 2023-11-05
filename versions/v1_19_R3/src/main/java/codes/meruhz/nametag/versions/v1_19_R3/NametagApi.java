package codes.meruhz.nametag.versions.v1_19_R3;

import codes.meruhz.nametag.api.impl.AbstractNametagApi;
import codes.meruhz.nametag.api.utils.ComponentUtils;
import com.google.gson.JsonParser;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.protocol.game.ClientboundSetPlayerTeamPacket;
import net.minecraft.world.scores.PlayerTeam;
import net.minecraft.world.scores.Scoreboard;
import net.minecraft.world.scores.Team;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_19_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;
public class NametagApi extends AbstractNametagApi {
    @Override
    protected void setNametag(@NotNull UUID user, @NotNull ChatColor color, @NotNull BaseComponent @Nullable [] prefix, @NotNull BaseComponent @Nullable [] suffix, boolean visible, boolean collidable) {
        @Nullable Player player = Bukkit.getPlayer(user);

        if(player == null) {
            throw new NullPointerException("User '" + user + "' is not online");
        }

        @NotNull Scoreboard scoreboard = new Scoreboard();
        @Nullable PlayerTeam team = scoreboard.getPlayerTeam(player.getName());
        team = team == null ? scoreboard.addPlayerTeam(player.getName()) : team;

        team.setColor(ChatFormatting.valueOf(color.getName().toUpperCase()));
        team.setPlayerPrefix(prefix == null ? null : Component.Serializer.fromJson(JsonParser.parseString(ComponentUtils.serialize(prefix))));
        team.setPlayerSuffix(suffix == null ? null : Component.Serializer.fromJson(JsonParser.parseString(ComponentUtils.serialize(suffix))));
        team.setNameTagVisibility(visible ? Team.Visibility.ALWAYS : Team.Visibility.NEVER);
        team.setCollisionRule(collidable ? Team.CollisionRule.ALWAYS : Team.CollisionRule.NEVER);
        team.setDeathMessageVisibility(Team.Visibility.NEVER);

        if(!scoreboard.addPlayerToTeam(player.getName(), team)) {
            throw new RuntimeException("An error occurred while add player '" + player.getName() + "' to team '" + team.getName() + "'");
        }

        @NotNull ClientboundSetPlayerTeamPacket packet = ClientboundSetPlayerTeamPacket.createAddOrModifyPacket(team, true);
        Bukkit.getOnlinePlayers().forEach(target -> ((CraftPlayer) target).getHandle().connection.send(packet));
    }
}