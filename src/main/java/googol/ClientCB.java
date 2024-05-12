package googol;

import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ClientCB extends Remote, Serializable {

  // void notify(String name) throws RemoteException;
  void notify(AdminInfoDTO adminInfo) throws RemoteException;

}
