package com.Googol.frontend.forms;

public class SearchTerms {
  private String terms;
  private int page;

  public SearchTerms() {
  }

  public String getTerms() {
    return terms;
  }

  public int getPage() {
    return page;
  }

  public void setTerms(String terms) {
    this.terms = terms;
  }

  public void setPage(int page) {
    this.page = page;
  }
}
