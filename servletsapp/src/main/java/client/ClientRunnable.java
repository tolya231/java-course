package client;

import java.net.HttpURLConnection;
import java.net.URL;


public class ClientRunnable implements Runnable {

  //highload test
  @Override
  public void run() {
    HttpURLConnection connection = null;
    try {
      URL url = new URL("http://localhost:8087/servlets_app_war/hi");
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.getResponseCode();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }
}
