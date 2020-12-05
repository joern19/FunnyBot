package tech.hirschfeld.funnybot.events.handler;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.reflections.Reflections;

import net.dv8tion.jda.api.events.guild.GenericGuildEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import tech.hirschfeld.funnybot.events.utils.GuildEventListener;
import tech.hirschfeld.funnybot.events.utils.GuildSpecificListenerAdapter;

public class EventListener extends ListenerAdapter {

    private static Logger logger = Logger.getLogger(EventListener.class.getName());
    private final GuildSpecificListenerAdapter[] guildSpecificListener;

    public EventListener() {
        //prepare the specific Guild handler.
        Reflections reflections = new Reflections("tech.hirschfeld.funnybot.events.handler.guildspecific");
        Set<Class<?>> annotatedClasses = reflections.getTypesAnnotatedWith(GuildEventListener.class);

        ArrayList<GuildSpecificListenerAdapter> annotatedGuildSpecificListeners = new ArrayList<>(annotatedClasses.size());

        for (Class<?> c : annotatedClasses) {
            if (c.getSuperclass() != ListenerAdapter.class) {
                throw new RuntimeException("The Class '" + c.getName() + "' is annoted with 'GuildEventListener' but does not extend from 'ListenerAdapter'");
            }
            Class<? extends ListenerAdapter> eventListener = (Class<? extends EventListener>) c;
            
            String id = eventListener.getDeclaredAnnotation(GuildEventListener.class).guildId();
            logger.log(Level.INFO, "Registered Guild specific EventListener: {}", id);
            
            try {
                Constructor<? extends ListenerAdapter> con = eventListener.getDeclaredConstructor();
                annotatedGuildSpecificListeners.add(new GuildSpecificListenerAdapter(id, (ListenerAdapter) con.newInstance()));
            } catch (Exception e) {
                logger.severe("Could not Construct '" + eventListener.getName() + "'!");
                e.printStackTrace();
            }
        }
        guildSpecificListener = annotatedGuildSpecificListeners.toArray(new GuildSpecificListenerAdapter[annotatedGuildSpecificListeners.size()]);
    }

    /**
     * Run the Guild specific functions inside the "tech.hirschfeld.funnybot.events.handler.guildspecific" package.
     */
    @Override
    public void onGenericGuild(GenericGuildEvent event) {
        String id = event.getGuild().getId();
        for (GuildSpecificListenerAdapter la : guildSpecificListener) {
            if (!id.equals(la.getGuildId())) {
                continue;
            }
            la.getListenerAdapter().onEvent(event);
            return;
        }
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }
        logger.log(Level.ALL, "Someone tried to talk to me in a Private Channel");
        event.getMessage().addReaction("U+1F44E").queue();
        event.getChannel().sendMessage("`Please do not disturb me!`").queue(m -> m.addReaction("U+1F634").queue());
    }

}
