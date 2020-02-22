package rocks.coffeenet.autoconfigure.security.servlet;

import org.springframework.context.ApplicationContext;

import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;

import java.util.List;
import java.util.stream.Collectors;


/**
 * Load {@link CoffeeNetSecurityConfigurer} instances registered in the application context and apply them to
 * {@link WebSecurityConfigurerAdapter} instances. If you do not want this, use
 * {@link WebSecurityConfigurerAdapter#WebSecurityConfigurerAdapter(boolean)} with {@code true} to disable defaults.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
public class DelegatingSecurityConfigurer extends AbstractHttpConfigurer<DelegatingSecurityConfigurer, HttpSecurity> {

    @Override
    public void init(HttpSecurity http) throws Exception {

        ApplicationContext context = http.getSharedObject(ApplicationContext.class);

        List<CoffeeNetSecurityConfigurer> delegates = context
                .getBeansOfType(CoffeeNetSecurityConfigurer.class)
                .values()
                .stream()
                .sorted(AnnotationAwareOrderComparator.INSTANCE)
                .collect(Collectors.toList());

        for (CoffeeNetSecurityConfigurer configurer : delegates) {
            http.apply(configurer);
            configurer.init(http);
        }
    }
}
