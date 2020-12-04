package tech.hirschfeld.funnybot;

import java.util.logging.Level;
import java.util.logging.Logger;

import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class EventListener extends ListenerAdapter {
    
    private static Logger logger = Logger.getLogger(EventListener.class.getName());

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        logger.log(Level.ALL, "Someone tried to talk to me in a Private Channel");
        event.getMessage().addReaction("U+1F44E").queue();
        event.getChannel().sendMessage("`Please do not disturb me!`").queue(m -> m.addReaction("U+1F634").queue());
    }

    @Override
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        if (!event.getGuild().getId().equals("784463579814428672")) {
            return;
        }
        Role r = event.getGuild().getRoleById("784465288132427806");
        if (r != null) {
            event.getGuild().addRoleToMember(event.getMember(), r).queue();
        }
        event.getMember().modifyNickname("Eins Mensch!").queue();
    }

}
