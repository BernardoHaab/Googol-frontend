package com.Googol.frontend;

import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Googol.frontend.forms.GenericResponse;
import com.google.gson.Gson;

import googol.PageDTO;
import googol.RMI;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class GatewayController {

  private static String gatewayHost;
  private static int gatewayPort;
  private static String gatewayName;

  public GatewayController() {
    gatewayHost = "127.0.0.1";
    gatewayPort = 1109;
    gatewayName = "googolGateway";
  }

  @PostMapping(value = "/addUrl", produces = "application/json")
  public @ResponseBody String addUrl(@RequestParam("newUrl") String newUrl) {

    RMI gateway = getGatewayInstance();
    GenericResponse res = new GenericResponse();

    System.out.println("newUrl: " + newUrl);

    try {
      gateway.adicionaURL(newUrl);
      res.setSuccess(true);

    } catch (Exception e) {
      System.out.println("Erro ao adicionar URL!");
      e.printStackTrace();
      res.setCodeError(500);
      res.setMessageError(e.getMessage());
      // return "redirect:/erro";
    }

    return new Gson().toJson(res);
  }

  @RequestMapping(value = "/searchByUrl", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody String searchByUrl(HttpServletRequest req) {

    String url = req.getParameter("searchUrl");

    System.out.println("Url: " + url);

    RMI gateway = getGatewayInstance();

    List<PageDTO> pages = new ArrayList<>();

    try {
      pages.addAll(gateway.pesquisaConexoes(url));
      // PageDTO p = new PageDTO(1, "https://www.google.com", "Google",
      // "Searchengine");
      // PageDTO p1 = new PageDTO(1, "https://www.temp.com", "Teste", "frase");
      // if (url != null && !url.isEmpty()) {
      // pages.add(p);
      // pages.add(p1);
      // }

    } catch (Exception e) {
      System.out.println("Erro ao buscar termos!");
      e.printStackTrace();
      return "redirect:/erro";
    }
    Gson gson = new Gson();
    // String jsonArray = gson.toJson(pages);
    return gson.toJson(pages);
  }

  @RequestMapping(value = "/searchByTerms", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody String pagesByTerms(HttpServletRequest req) {

    String terms = req.getParameter("terms");
    Integer page = Integer.valueOf(req.getParameter("page"));

    System.out.println("Terms: " + terms);

    RMI gateway = getGatewayInstance();

    Set<String> termsSet = new HashSet<String>();
    List<PageDTO> pages = new ArrayList<>();

    String[] splitedTerms = terms.split(" ");
    for (String term : splitedTerms) {
      termsSet.add(term);
    }

    try {
      pages.addAll(gateway.pesquisaPrincipal(termsSet, page));
      // PageDTO p = new PageDTO(1, "https://www.aaaa.com", "EEEE", "Searchengine");
      // PageDTO p1 = new PageDTO(1, "https://www.temp.com", "Teste", "frase");
      // if (terms != null && !terms.isEmpty()) {
      // pages.add(p);
      // pages.add(p1);
      // }

    } catch (Exception e) {
      System.out.println("Erro ao buscar termos!");
      e.printStackTrace();
      return "redirect:/erro";
    }
    Gson gson = new Gson();
    return gson.toJson(pages);
  }

  private static RMI getGatewayInstance() {
    try {
      return (RMI) LocateRegistry.getRegistry(gatewayHost, gatewayPort).lookup(gatewayName);
    } catch (Exception e) {
      System.out.println("Erro ao conectar ao servidor!");
      System.out.println("Tentando novamente...");
      try {
        TimeUnit.SECONDS.sleep(1);
      } catch (InterruptedException e1) {
        e1.printStackTrace();
      }
      return getGatewayInstance();
    }
  }

}
