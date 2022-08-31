package com.pysarenko.blog.controller.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.NoSuchElementException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

public class ExceptionHandlerControllerTest {

  private ExceptionHandlerController exceptionHandlerController;

  @BeforeEach
  void setUp() {
    exceptionHandlerController = new ExceptionHandlerController();
  }

  @Test
  void shouldHandleNoSuchElementException() {

    var response = exceptionHandlerController.handleNoSuchElementException(new NoSuchElementException());

    assertThat(response).isNotNull();
    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
  }
}
