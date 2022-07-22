package skyblock.utils.discord.log.listener;

import io.github.cdimascio.dotenv.Dotenv;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import skyblock.database.SQLite;
import skyblock.utils.discord.log.core.Bot;

public class GuildAdd extends ListenerAdapter {

    private static final Dotenv dotenv = Bot.dotenv;

    @Override
    public void onGuildJoin(@NotNull GuildJoinEvent event) {
        Guild guild = event.getGuild();

        String guildId = guild.getId();

        // check if guild is already in database
        if (SQLite.checkGuild(guildId)) {
            TextChannel channel = Bot.jda.getTextChannelById(dotenv.get("LOG_CHANNEL"));

            EmbedBuilder embed = new EmbedBuilder();
            embed.setTitle("System");
            embed.setDescription("Guild already exists in database");
            embed.setColor(0x00ff00);
            embed.setFooter("Guild ID: " + guildId);

            if (channel != null) {
                channel.sendMessageEmbeds(embed.build()).queue();
            }
        } else {
            SQLite.addGuild(guildId);
        }
    }

}
