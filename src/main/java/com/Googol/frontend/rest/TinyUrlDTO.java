package com.Googol.frontend.rest;

public class TinyUrlDTO {
  private String url;
  private String tiny_url;

  public TinyUrlDTO(String url, String tiny_url) {
    this.url = url;
    this.tiny_url = tiny_url;
  }

  public String getUrl() {
    return url;
  }

  public String getTinyUrl() {
    return tiny_url;
  }
}