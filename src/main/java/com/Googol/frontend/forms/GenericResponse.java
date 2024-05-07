package com.Googol.frontend.forms;

public class GenericResponse {
  private boolean success;
  private int codeError;
  private String messageError;

  public GenericResponse() {
    success = false;
    codeError = -1;
  }

  public boolean isSuccess() {
    return success;
  }

  public void setSuccess(boolean success) {
    this.success = success;
  }

  public int getCodeError() {
    return codeError;
  }

  public void setCodeError(int codeError) {
    this.codeError = codeError;
  }

  public String getMessageError() {
    return messageError;
  }

  public void setMessageError(String messageError) {
    this.messageError = messageError;
  }

}
