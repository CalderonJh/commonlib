package dev.jhonc.lib.demolib.controller.rest;

import dev.jhonc.lib.demolib.exception.ValidationException;
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
