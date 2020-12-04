package tech.hirschfeld.funnybot;

public class SettingsReader {
  
  private SettingsReader() {
  }

  public static String getToken() {
    return System.getenv("TOKEN");
  }

}
