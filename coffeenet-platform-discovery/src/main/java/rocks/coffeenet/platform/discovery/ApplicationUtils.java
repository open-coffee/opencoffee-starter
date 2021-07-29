package rocks.coffeenet.platform.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cloud.client.ServiceInstance;

import org.springframework.util.StringUtils;

import rocks.coffeenet.platform.domain.app.CoffeeNetApplication;

import java.net.MalformedURLException;
import java.net.URL;

import java.util.Map;
import java.util.stream.Stream;


/**
 * Conversion utility functions from service sources to actual {@link CoffeeNetApplication} instances.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
class ApplicationUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUtils.class);

    private static final String LEGACY_ROLES_METADATA_KEY = "allowedAuthorities";

    private ApplicationUtils() {
    }

    public static CoffeeNetApplication fromServiceInstance(ServiceInstance instance) {

        try {
            String name = instance.getServiceId();
            URL url = instance.getUri().toURL();

            CoffeeNetApplication.Builder builder = CoffeeNetApplication.withNameAndApplicationUrl(name, url);

            // Add allowed authorities to the actual instance.
            Map<String, String> metadata = instance.getMetadata();
            String rolesString = metadata.get(LEGACY_ROLES_METADATA_KEY);

            Stream.of(StringUtils.commaDelimitedListToStringArray(rolesString))
                .map(String::trim)
                .forEach(builder::withAuthority);

            return builder.build();
        } catch (MalformedURLException e) {
            LOGGER.warn(String.format("Could not map from service instance %s", instance), e);

            return null;
        }
    }
}
