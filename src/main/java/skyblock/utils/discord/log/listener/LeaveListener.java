package skyblock.utils.discord.log.listener;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.guild.GuildLeaveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import skyblock.database.SQLite;

public class LeaveListener extends ListenerAdapter {

    @Override
    public void onGuildLeave(@NotNull GuildLeaveEvent event) {
        System.out.println("Left guild: " + event.getGuild().getName());

        Guild guildId = event.getGuild();

        SQLite.deleteGuild(String.valueOf(guildId));

        System.out.println("Guild deleted from database");
    }
}
