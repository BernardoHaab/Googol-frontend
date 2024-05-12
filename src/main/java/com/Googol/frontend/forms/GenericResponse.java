package com.Googol.frontend.forms;

import java.io.Serializable;

public class GenericResponse<T> implements Serializable {
  private boolean success;
  private int statusCode;
  private String messageError;
  private T data;

  public GenericResponse() {
    success = true;
    statusCode = 200;
  }

  public GenericResponse(T data) {
    success = true;
    statusCode = 200;
    this.data = data;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public int getStatusCode() {
    return statusCode;
  }

  public void setStatusCode(int statusCode) {
    this.statusCode = statusCode;
  }

  public String getMessageError() {
    return messageError;
  }

  public void setMessageError(String messageError) {
    this.messageError = messageError;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }

}
