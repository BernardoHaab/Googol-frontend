package com.Googol.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RouterController {

  @GetMapping("/add-url")
  public String saveProjectSubmission(Model model) {
    return "add-url";
  }

  @GetMapping("/search-terms")
  public String searchTerms(Model model) {
    return "search-terms";
  }

  @GetMapping("/search-url")
  public String searchPages(Model model) {

    return "search-url";
  }

  @GetMapping("/top-HackerNews")
  public String topHackerNews(Model model) {

    return "top-HackerNews";
  }

  @GetMapping("/erro")
  public String error(Model model) {

    return "erro";
  }

}
