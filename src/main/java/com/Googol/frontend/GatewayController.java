package com.Googol.frontend;

import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Googol.frontend.forms.GenericResponse;
import com.google.gson.Gson;

import googol.ClientCB;
import googol.PageDTO;
import googol.RMI;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class GatewayController {

  private static String gatewayHost;
  private static int gatewayPort;
  private static String gatewayName;

  // gateway;143.47.33.136;8080;googolGateway
  public GatewayController() {
    gatewayHost = "127.0.0.1";
    gatewayPort = 1109;
    gatewayName = "googolGateway";
  }

  @PostMapping(value = "/addUrl", produces = "application/json")
  public @ResponseBody void addUrl(@RequestParam("newUrl") String newUrl, HttpServletResponse res) throws IOException {
    Gson gson = new Gson();
    GenericResponse<Object> resBody = new GenericResponse<>();

    try {
      RMI gateway = getGatewayInstance();
      System.out.println("Adicionando URL...");
      gateway.adicionaURL(newUrl);
    } catch (Exception e) {
      System.out.println("Erro ao adicionar URL!");
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

  @RequestMapping(value = "/searchByUrl", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody void searchByUrl(HttpServletRequest req, HttpServletResponse res) throws IOException {

    Gson gson = new Gson();
    GenericResponse<List<PageDTO>> resBody = new GenericResponse<>();
    List<PageDTO> pages = new ArrayList<>();

    String url = req.getParameter("searchUrl");

    try {
      RMI gateway = getGatewayInstance();
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
      System.out.println("Erro: " + e.getMessage());
      e.printStackTrace();
      resBody.setStatusCode(500);
      resBody.setMessageError(e.getMessage());
      resBody.setSuccess(false);
    }

    if (resBody.isSuccess()) {
      resBody.setData(pages);
    }

    res.setStatus(resBody.getStatusCode());
    res.setCharacterEncoding("UTF-8");
    res.getWriter().print(gson.toJson(resBody));
    res.getWriter().flush();
  }

  @RequestMapping(value = "/searchByTerms", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody void pagesByTerms(HttpServletRequest req, HttpServletResponse res) throws IOException {
    Gson gson = new Gson();
    GenericResponse<List<PageDTO>> resBody = new GenericResponse<>();
    Set<String> termsSet = new HashSet<String>();
    List<PageDTO> pages = new ArrayList<>();

    String terms = req.getParameter("terms");
    Integer page = Integer.valueOf(req.getParameter("page"));

    String[] splitedTerms = terms.split(" ");
    for (String term : splitedTerms) {
      termsSet.add(term);
    }

    try {
      RMI gateway = getGatewayInstance();
      System.out.println("Buscou gateway!");
      pages.addAll(gateway.pesquisaPrincipal(termsSet, page));
      System.out.println("Buscou pagina");
      System.out.println("Pages: " + pages.size());
      // PageDTO p = new PageDTO(1, "https://www.aaaa.com", "EEEE", "Searchengine");
      // PageDTO p1 = new PageDTO(1, "https://www.temp.com", "Teste", "frase");
      // if (terms != null && !terms.isEmpty()) {
      // pages.add(p);
      // pages.add(p1);
      // }

    } catch (Exception e) {
      System.out.println("Erro ao buscar termos!");
      e.printStackTrace();
      resBody.setStatusCode(500);
      resBody.setMessageError(e.getMessage());
      resBody.setSuccess(false);
    }

    if (resBody.isSuccess()) {
      resBody.setData(pages);
    }

    res.setStatus(resBody.getStatusCode());
    res.setCharacterEncoding("UTF-8");
    res.getWriter().print(gson.toJson(resBody));
    res.getWriter().flush();
  }

  private static RMI getGatewayInstance() throws Exception {
    RMI gateway = (RMI) LocateRegistry.getRegistry(gatewayHost, gatewayPort).lookup(gatewayName);
    System.out.println("GATEWAY");
    RealTimeContent realTimeContent = new RealTimeContent();
    System.out.println("REALTIMECONTENT");
    gateway.subscribe((ClientCB) realTimeContent);
    System.out.println("SUBSCRIBED");
    return gateway;
  }

}
