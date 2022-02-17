package rocks.coffeenet.autoconfigure.security.reactive;

import org.springframework.beans.factory.config.BeanPostProcessor;

import org.springframework.security.config.web.server.ServerHttpSecurity;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * Load {@link ReactiveServerHttpSecurityCustomizer} instances registered in the application context and apply them to
 * {@link ServerHttpSecurity} instances.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
public class ReactiveServerHttpSecurityPostProcessor implements BeanPostProcessor {

    private final List<ReactiveServerHttpSecurityCustomizer> customizers;

    public ReactiveServerHttpSecurityPostProcessor(List<ReactiveServerHttpSecurityCustomizer> customizers) {

        if (customizers == null) {
            throw new IllegalArgumentException("list of customizers must not be null.");
        }

        if (customizers.isEmpty()) {
            throw new IllegalArgumentException("list of customizers must not be empty.");
        }

        this.customizers = Collections.unmodifiableList(new LinkedList<>(customizers));
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) {

        if (bean instanceof ServerHttpSecurity) {
            ServerHttpSecurity http = (ServerHttpSecurity) bean;
            customize(http);
        }

        return bean;
    }


    private void customize(ServerHttpSecurity http) {

        for (ReactiveServerHttpSecurityCustomizer customizer : customizers) {
            customizer.customize(http);
        }
    }
}
