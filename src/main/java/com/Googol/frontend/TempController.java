package com.Googol.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TempController {

  @GetMapping("/add-url")
  public String saveProjectSubmission(Model model) {
    return "add-url";
  }

  @GetMapping("/search-terms")
  public String searchTerms(@RequestParam(name = "terms", required = false, defaultValue = "") String terms,
      @RequestParam(name = "page", required = false, defaultValue = "1") Integer page, Model model) {
    return "search-terms";
  }

  @GetMapping("/search-url")
  public String searchPages(Model model) {

    return "search-url";
  }

  @GetMapping("/top-HackerNews")
  public String topHackerNews(Model model) {

    // List<HackerNewsDTO> topItems = new ArrayList<>();

    // try {
    // topItems = HackerNews.getTopStories();
    // } catch (Exception e) {
    // e.printStackTrace();
    // }

    // model.addAttribute("topItems", topItems);

    return "top-HackerNews";
  }

  @GetMapping("/erro")
  public String error(Model model) {

    return "erro";
  }

}
