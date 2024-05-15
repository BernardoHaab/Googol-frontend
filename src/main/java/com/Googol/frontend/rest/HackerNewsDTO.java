package com.Googol.frontend.rest;

public class HackerNewsDTO {
  private String title;
  private String url;
  private int position;

  public HackerNewsDTO() {
  }

  public HackerNewsDTO(String title, String url) {
    this.title = title;
    this.url = url;
  }

  public HackerNewsDTO(String title, String url, int position) {
    this.title = title;
    this.url = url;
    this.position = position;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getUrl() {
    return url;
  }

  public void setUrl(String url) {
    this.url = url;
  }

  public int getPosition() {
    return position;
  }

  public void setPosition(int id) {
    this.position = id;
  }

}
