package com.emiteai.challengeEmiteaiTms.exception;

public class FailedOrderException extends Exception {

  private static final long serialVersionUID = -7104228324480547193L;

  public FailedOrderException(Exception e) {
    super(e.getMessage());
  }

  public FailedOrderException(String entity, long id) {
    super(entity + " - not found - id:"+id);
  }
}
