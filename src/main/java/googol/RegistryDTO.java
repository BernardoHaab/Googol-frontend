package googol;

import java.io.Serializable;

public class RegistryDTO implements Serializable {

  private String hostAddress;
  private boolean isActive;
  private Long avgResponseTime;

  public RegistryDTO(String hostAddress, boolean isActive, Long avgResponseTime) {
    this.hostAddress = hostAddress;
    this.isActive = isActive;
    this.avgResponseTime = avgResponseTime;
  }

  public String getHostAddress() {
    return hostAddress;
  }

  public boolean isActive() {
    return isActive;
  }

  public Long getAvgResponseTime() {
    return avgResponseTime;
  }

}
