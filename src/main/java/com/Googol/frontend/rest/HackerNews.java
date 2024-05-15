package com.Googol.frontend.rest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Googol.frontend.forms.GenericResponse;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletResponse;

@Controller
public class HackerNews {

  @RequestMapping(value = "/getTopHackerNew", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody void getTopHackerNew(@RequestParam("terms") String terms,
      @RequestParam(value = "size", defaultValue = "10") String size, HttpServletResponse res)
      throws IOException {

    Gson gson = new Gson();
    GenericResponse<List<HackerNewsDTO>> resBody = new GenericResponse<>();

    try {
      List<HackerNewsDTO> topStories = getTopStories(Integer.parseInt(size));

      System.out.println("Top stories: " + topStories.size());

      List<HackerNewsDTO> filteredStories = topStories.stream().parallel().filter((item) -> {
        Document doc;
        try {
          doc = Jsoup.connect(item.getUrl()).get();
          boolean containsAll = true;

          for (String term : terms.split(" ")) {
            if (!doc.text().toLowerCase().contains(term.toLowerCase())) {
              containsAll = false;
              break;
            }
          }

          return containsAll;
        } catch (IOException e) {
          System.out.println("Erro ao buscar url do item " + item.getPosition() + " do HackerNew!");
          System.out.println(item.getUrl());
          return false;
        }
      }).toList();

      resBody.setData(filteredStories);

      resBody.setStatusCode(200);
      resBody.setSuccess(true);
      // resBody.setData(page);
    } catch (Exception e) {
      System.out.println("Erro ao buscar top stories do HackerNew!");
      e.printStackTrace();
      resBody.setStatusCode(500);
      resBody.setMessageError(e.getMessage());
      resBody.setSuccess(false);
    }

    res.setStatus(resBody.getStatusCode());
    res.setCharacterEncoding("UTF-8");
    res.getWriter().print(gson.toJson(resBody));
    res.getWriter().flush();
  }

  public static List<HackerNewsDTO> getTopStories(int size) throws IOException {
    URL url = new URL("https://hacker-news.firebaseio.com/v0/topstories.json");
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");

    List<Integer> topIds = new ArrayList<>();
    List<HackerNewsDTO> topItems = new ArrayList<>();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
      reader.lines().findFirst().ifPresent(line -> {
        String[] itemsString = line.substring(1, line.length() - 1).split(",");

        for (int i = 0; i < itemsString.length; i++) {
          topIds.add(Integer.parseInt(itemsString[i]));
        }
      });
    } catch (Exception e) {
      System.out.println("Erro ao buscar top stories do HackerNew!");
    }

    for (int i = 0; i < size; i++) {
      HackerNewsDTO item = getHNItem(topIds.get(i));
      item.setPosition(i + 1);
      topItems.add(item);
    }

    topItems.stream().filter((item) -> {

      Document doc;
      try {
        doc = Jsoup.connect(item.getUrl()).get();
        return doc.text().contains("");
      } catch (IOException e) {
        System.out.println("Erro ao buscar url do item " + item.getPosition() + " do HackerNew!");
        System.out.println(item.getUrl());
        return false;
      }

    }).toList();

    return topItems;
  }

  private static HackerNewsDTO getHNItem(int id) throws IOException {
    // System.out.println("Getting item " + id + "...");
    URL url = new URL("https://hacker-news.firebaseio.com/v0/item/" + id + ".json");
    HttpURLConnection con = (HttpURLConnection) url.openConnection();
    con.setRequestMethod("GET");

    StringBuilder result = new StringBuilder();

    try (BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

      for (String line; (line = reader.readLine()) != null;) {
        result.append(line);
      }
    }

    String[] itemsString = result.toString().substring(1, result.toString().length() - 1).split(",");

    // System.out.println("Item " + id + ": " + Arrays.toString(itemsString));
    String itemUrl = List.of(itemsString).stream().filter((item) -> item.contains("url"))
        .map((item) -> item.split("\":\"")[1].replaceAll("\"", "").trim()).findFirst().orElse("");

    String itemTitle = List.of(itemsString).stream().filter((item) -> item.contains("title"))
        .map((item) -> item.split("\":\"")[1].replaceAll("\"", "").trim()).findFirst().orElse("");

    // System.out.println("\nTitle: " + itemTitle);

    HackerNewsDTO hackerNewsDTO = new HackerNewsDTO(itemTitle, itemUrl);

    return new HackerNewsDTO(itemTitle, itemUrl);
  }

}
