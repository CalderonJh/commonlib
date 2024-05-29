package dev.jhonc.lib.common.service;

import dev.jhonc.lib.common.config.Properties;
import java.util.Arrays;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Service to get messages from the messages.properties file
 */
@Service
@Slf4j
@PropertySource("classpath:messages.properties")
public class Messages {
  private Properties configProperties;
  private Environment environment;

  /**
   * Get a message from the messages.properties file
   * @param key The key of the message
   * @return The message or the key if the message is not found
   */
  public String show(String key) {
    String msg = environment.getProperty(key);
    log.warn("Property 1: {}", msg);
    log.warn(configProperties.getTester());
    return msg == null ? key : msg;
  }

  /**
   * Get a message from the messages.properties file and format it with the given values
   * @param key The key of the message
   * @param values The values to format the message
   * @return The formatted message or the key if the message is not found
   */
  public String show(String key, Object... values) {
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
