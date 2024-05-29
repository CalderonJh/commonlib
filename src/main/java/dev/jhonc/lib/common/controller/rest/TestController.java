package dev.jhonc.lib.common.controller.rest;

import dev.jhonc.lib.common.exception.ValidationException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
public class TestController {

  @RequestMapping("/test")
  public String hello() {
    throw new ValidationException("Test Exception");
  }
}
