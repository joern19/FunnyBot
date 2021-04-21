package tech.hirschfeld.funnybot.events.utils;

import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class GuildSpecificListenerAdapter {
    
    private final String guildId;
    private final ListenerAdapter la;

    public GuildSpecificListenerAdapter(String guildId, ListenerAdapter la) {
        this.guildId = guildId;
        this.la = la;
    }

    public String getGuildId() {
        return guildId;
    }

    public ListenerAdapter getListenerAdapter() {
        return la;
    }

}
