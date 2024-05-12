package googol;

import java.io.Serializable;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;

public class AdminInfoDTO implements Serializable {

  private List<SimpleEntry<String, Integer>> topSearch;
  private List<RegistryDTO> barrels;

  public AdminInfoDTO(List<SimpleEntry<String, Integer>> topSearch, List<RegistryDTO> barrels) {
    this.topSearch = topSearch;
    this.barrels = barrels;
  }

  public List<SimpleEntry<String, Integer>> getTopSearch() {
    return topSearch;
  }

  public List<RegistryDTO> getBarrels() {
    return barrels;
  }

}
