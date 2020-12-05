package tech.hirschfeld.funnybot;

import java.util.logging.Logger;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import tech.hirschfeld.funnybot.events.handler.EventListener;

/**
 * Hello world!
 *
 */
public class App {

  public static JDA getJDA(String token) throws LoginException {
    var builder = JDABuilder.createDefault(token);
    return builder.build();
  }

  public static void main(String[] args) throws LoginException {
    Logger logger = Logger.getLogger(App.class.getName());

    JDA jda = getJDA(SettingsReader.getToken());
    try {
      jda.awaitReady();
    } catch (InterruptedException ex) {
      logger.severe("Failed to connect.");
      Thread.currentThread().interrupt();
      return;
    } 
    jda.addEventListener(new EventListener());

    jda.getPresence().setActivity(Activity.listening("dir")); //Hört dir zu.

    logger.info("Logged in as " + jda.getSelfUser().getName());
  }
}
