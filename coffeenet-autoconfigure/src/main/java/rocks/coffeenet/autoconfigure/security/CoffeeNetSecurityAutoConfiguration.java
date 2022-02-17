package rocks.coffeenet.autoconfigure.security;

import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.DefaultAuthenticationEventPublisher;


/**
 * {@link EnableAutoConfiguration Auto-configuration} for Spring Security in CoffeeNet applications.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
@Configuration(proxyBeanMethods = false)
@ConditionalOnClass(DefaultAuthenticationEventPublisher.class)
@AutoConfigureBefore(SecurityAutoConfiguration.class)
public class CoffeeNetSecurityAutoConfiguration {
}
