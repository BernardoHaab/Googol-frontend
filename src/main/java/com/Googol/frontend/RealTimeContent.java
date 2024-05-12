package com.Googol.frontend;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

import com.google.gson.Gson;

import googol.AdminInfoDTO;
import googol.ClientCB;

public class RealTimeContent extends UnicastRemoteObject implements ClientCB {

  public RealTimeContent() throws RemoteException {
    super();
  }

  @Override
  public void notify(AdminInfoDTO adminInfo) throws RemoteException {

    adminInfo.getTopSearch().forEach(entry -> {
      System.out.println(entry.getKey() + " " + entry.getValue());
    });

    Gson gson = new Gson();

    RealTimeContentDTO realTimeContentDTO = new RealTimeContentDTO(adminInfo);

    String res = gson.toJson(realTimeContentDTO);
    System.out.println(res);

    // ToDo: send res to frontend

  }

}
