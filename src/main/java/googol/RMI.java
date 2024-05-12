package googol;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.AbstractMap.SimpleEntry;
import java.util.List;
import java.util.Set;

public interface RMI extends Remote {
  public void subscribe(ClientCB client) throws RemoteException;

  public List<PageDTO> pesquisaPrincipal(Set<String> terms, int page) throws RemoteException;

  public List<PageDTO> pesquisaConexoes(String url) throws RemoteException;

  public List<RegistryDTO> getBarrelsInfos() throws RemoteException;

  public List<SimpleEntry<String, Integer>> getTopSearch() throws RemoteException;

  public void adicionaURL(String url) throws RemoteException;

}