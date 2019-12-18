package client;

import java.net.HttpURLConnection;
import java.net.URL;


public class ClientRunnable implements Runnable {

  //highload test
  @Override
  public void run() {
    HttpURLConnection connection = null;
    try {
      URL url = new URL("http://localhost:8080/hi");
      connection = (HttpURLConnection) url.openConnection();
      connection.setRequestMethod("GET");
      connection.getResponseCode();
    } catch (Exception e) {
      System.out.println("---------------------");
      e.printStackTrace();
    } finally {
      if (connection != null) {
        connection.disconnect();
      }
    }
  }
}
