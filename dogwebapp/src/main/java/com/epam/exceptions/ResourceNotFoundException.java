package com.epam.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

  public ResourceNotFoundException() {
    super("Resource not found");
  }

  public ResourceNotFoundException(Throwable cause) {
    super(cause);
  }
}
