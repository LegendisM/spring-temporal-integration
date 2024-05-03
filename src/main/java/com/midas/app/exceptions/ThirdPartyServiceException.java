package com.midas.app.exceptions;

import java.util.List;
import org.springframework.http.HttpStatus;

public class ThirdPartyServiceException extends ApiException {

  static final String MESSAGE = "ThirdParty Service Trouble";

  public ThirdPartyServiceException() {
    super(HttpStatus.FORBIDDEN, MESSAGE);
  }

  public ThirdPartyServiceException(String message) {
    super(HttpStatus.FORBIDDEN, MESSAGE);

    super.setMessage(message);
  }

  public ThirdPartyServiceException(String message, List<String> errors) {
    super(HttpStatus.FORBIDDEN, MESSAGE);

    super.setMessage(message);
    super.setErrors(errors);
  }

  public ThirdPartyServiceException(String message, String error) {
    super(HttpStatus.FORBIDDEN, MESSAGE);

    super.setMessage(message);
    super.setErrors(error);
  }
}
