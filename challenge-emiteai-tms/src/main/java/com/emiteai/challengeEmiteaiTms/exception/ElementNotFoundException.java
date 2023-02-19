package com.emiteai.challengeEmiteaiTms.exception;

public class ElementNotFoundException extends Exception {

  private static final long serialVersionUID = -7104228324480547193L;

  public ElementNotFoundException(Exception e) {
    super(e.getMessage());
  }

  public ElementNotFoundException(String entity, long id) {
    super(entity + " - not found - id:"+id);
  }
}
