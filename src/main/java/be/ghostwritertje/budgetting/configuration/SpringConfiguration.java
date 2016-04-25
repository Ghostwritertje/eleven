package be.ghostwritertje.budgetting.configuration;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Created by jorandeboever
 * on 22/04/16.
 */
@Configuration
@ComponentScan(basePackages = {"be.ghostwritertje.budgetting.services", "be.ghostwritertje.budgetting.dao"})
public class SpringConfiguration {
}
