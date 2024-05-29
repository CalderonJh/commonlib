package dev.jhonc.lib.demolib.service;

import dev.jhonc.lib.demolib.config.Properties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@Slf4j
@PropertySource("classpath:messages.properties")
public class MessagesService {
  private Properties configProperties;
  private Environment environment;

  public String msg(String key) {
    String msg = environment.getProperty(key);
    log.warn("Property 1: {}", msg);
    log.warn(configProperties.getTester());
    return msg == null ? key : msg;
  }

  public String msg(String key, Object... values) {
    String msg = environment.getProperty(key);
    try {
      return msg == null ? key : String.format(msg, values);
    } catch (Exception e) {
      log.error("Error formatting message with key '{}', Message: {}, Values:{}", key, msg, Arrays.toString(values));
      return key;
    }
  }

  @Autowired
  public void setProperties(Properties properties) {
    this.configProperties = properties;
  }

  @Autowired
  public void setEnvironment(Environment environment) {
    this.environment = environment;
  }
}
