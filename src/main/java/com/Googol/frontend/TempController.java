package com.Googol.frontend;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class TempController {

  @GetMapping("/add-url")
  public String saveProjectSubmission(Model model) {

    // RMI gateway = getGatewayInstance();

    // try {
    // gateway.adicionaURL(form.getUrl());
    // } catch (Exception e) {
    // System.out.println("Erro ao adicionar URL!");
    // e.printStackTrace();
    // return "redirect:/erro";
    // }

    // System.err.println("URL: " + form.getUrl());

    // model.addAttribute("form", form);

    return "add-url";
    // return "add-url";
  }

  @GetMapping("/search-terms")
  public String searchTerms(@RequestParam(name = "terms", required = false, defaultValue = "") String terms,
      @RequestParam(name = "page", required = false, defaultValue = "1") Integer page, Model model) {

    // System.out.println("Terms: " + terms);
    // System.out.println("Page: " + page);

    // model.addAttribute("terms", terms);
    // model.addAttribute("page", page);

    // // RMI gateway = getGatewayInstance();

    // Set<String> termsSet = new HashSet<String>();

    // String[] splitedTerms = terms.split(" ");
    // for (String term : splitedTerms) {
    // termsSet.add(term);
    // }

    // try {
    // // List<PageDTO> pages = gateway.pesquisaPrincipal(termsSet, 1);
    // PageDTO p = new PageDTO(1, "https://www.google.com", "Google",
    // "Searchengine");
    // List<PageDTO> pages = new ArrayList<>();
    // if (terms != null && !terms.isEmpty()) {
    // pages = List.of(p);
    // }
    // model.addAttribute("pages", pages);
    // } catch (Exception e) {
    // System.out.println("Erro ao buscar termos!");
    // e.printStackTrace();
    // return "redirect:/erro";
    // }

    return "search-terms";
  }

  @GetMapping("/search-url")
  public String searchPages(Model model) {

    return "search-url";
  }

  @GetMapping("/erro")
  public String error(Model model) {

    return "erro";
  }

}
