package com.Googol.frontend.rest;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.Googol.frontend.forms.GenericResponse;
import com.google.gson.Gson;

import jakarta.servlet.http.HttpServletResponse;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Controller
public class ShortUrl {

  @Value("${tinyurl.api.url}")
  private String apiUrl;

  @Value("${tinyurl.api.key}")
  private String apiKey;

  @RequestMapping(value = "/shortUrl", method = RequestMethod.GET, produces = "application/json")
  public @ResponseBody void shortUrl(@RequestParam("longUrl") String longUrl, HttpServletResponse res)
      throws IOException {

    Gson gson = new Gson();
    GenericResponse<TinyUrlDTO> resBody = new GenericResponse<>();

    try {
      OkHttpClient client = new OkHttpClient().newBuilder()
          .build();
      MediaType mediaType = MediaType.parse("application/json");
      RequestBody body = RequestBody.create("{\r\n  \"url\": \"" + longUrl + "\"\r\n}", mediaType);
      Request request = new Request.Builder()
          .url("https://api.tinyurl.com/create")
          .method("POST", body)
          .addHeader("Content-Type", "application/json")
          .addHeader("Authorization", "Bearer V1V3ASWkfSHFwNRddUYlRJxa8WjEvcFkh9Ny9LXl4RYNkb8SkugG8qE53PtW")
          .build();
      Response response = client.newCall(request).execute();

      String responseString = response.body().string();
      int statusCode = response.code();

      if (statusCode == 200) {
        TinyUrlRes tinyUrl = gson.fromJson(responseString, TinyUrlRes.class);
        resBody.setSuccess(true);
        resBody.setStatusCode(200);
        resBody.setData(tinyUrl.getData());
      } else {
        resBody.setSuccess(false);
        resBody.setStatusCode(statusCode);
      }

    } catch (Exception e) {
      e.printStackTrace();
      resBody.setSuccess(false);
      resBody.setMessageError("Erro ao encurtar URL!");
      resBody.setMessageError(e.getMessage());
    }

    res.setStatus(resBody.getStatusCode());
    res.setCharacterEncoding("UTF-8");
    res.getWriter().print(gson.toJson(resBody));
    res.getWriter().flush();
  }

}
