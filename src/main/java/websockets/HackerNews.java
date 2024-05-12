package websockets;

import java.io.*;
import java.net.*;

public class HackerNews {
    public static String fetchHTML(String readingUrl) throws Exception{
        StringBuilder result = new StringBuilder();
        URL url = new URL(readingUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
            for (String line; (line = reader.readLine()) != null;) {
                result.append(line);
            }
        }
        return result.toString();
    }

    public static void main(String[] args) throws Exception{
            System.out.println(fetchHTML("https://hacker-news.firebaseio.com/v0/topstories.json?print=pretty"));
    }
}