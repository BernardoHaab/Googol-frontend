package com.Googol.frontend;

import java.util.LinkedList;
import java.util.List;

import googol.AdminInfoDTO;
import googol.RegistryDTO;

public class RealTimeContentDTO {
  private class SearchInfo {
    private String search;
    private int count;

    public SearchInfo(String search, int count) {
      this.search = search;
      this.count = count;
    }

    public String getSearch() {
      return search;
    }

    public int getCount() {
      return count;
    }
  }

  private List<SearchInfo> topSearch;
  private List<RegistryDTO> barrels;

  public RealTimeContentDTO(AdminInfoDTO adminInfo) {
    topSearch = new LinkedList<>();
    adminInfo.getTopSearch().forEach(search -> {
      topSearch.add(new SearchInfo(search.getKey(), search.getValue()));
    });
    barrels = adminInfo.getBarrels();
  }

  public RealTimeContentDTO(List<SearchInfo> topSearch, List<RegistryDTO> barrels) {
    this.topSearch = topSearch;
    this.barrels = barrels;
  }

  public List<SearchInfo> getTopSearch() {
    return topSearch;
  }

  public List<RegistryDTO> getBarrels() {
    return barrels;
  }

}
