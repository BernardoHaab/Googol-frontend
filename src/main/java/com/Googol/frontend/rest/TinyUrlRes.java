package com.Googol.frontend.rest;

public class TinyUrlRes {

  private TinyUrlDTO data;
  private int code;
  private String[] errors;

  public TinyUrlRes() {
  }

  public TinyUrlDTO getData() {
    return data;
  }

  public void setData(TinyUrlDTO data) {
    this.data = data;
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String[] getErrors() {
    return errors;
  }

  public void setErrors(String[] errors) {
    this.errors = errors;
  }

}
