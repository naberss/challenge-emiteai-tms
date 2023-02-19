package com.emiteai.challengeEmiteaiTms.exception;

public class GenericException extends Exception {

  private static final long serialVersionUID = -7104228324480547193L;

  public GenericException(Exception e) {
    super(e.getMessage());
  }

  public GenericException(String errorMessage) {
    super(errorMessage);
  }
}
