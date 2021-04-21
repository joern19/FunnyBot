package tech.hirschfeld.funnybot.events.handler.guildspecific;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tech.hirschfeld.funnybot.GuildEventListener;

@GuildEventListener(guildId = "784463579814428672")
public class EinsServerEventListener extends ListenerAdapter {
    
    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        event.getChannel().sendMessage("Ich bin auf eins Server...").queue();
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        Role r = event.getGuild().getRoleById("784465288132427806");
        if (r != null) {
            event.getGuild().addRoleToMember(event.getMember(), r).queue();
        }
        event.getMember().modifyNickname("Eins Mensch!").queue();
    }

}
