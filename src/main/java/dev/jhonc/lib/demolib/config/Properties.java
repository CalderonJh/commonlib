package dev.jhonc.lib.demolib.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "test.lib")
public class Properties {
	private String tester;
}
