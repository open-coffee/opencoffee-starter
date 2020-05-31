package rocks.coffeenet.platform.discovery;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.cloud.client.ServiceInstance;

import rocks.coffeenet.platform.domain.app.CoffeeNetApplication;

import java.net.MalformedURLException;
import java.net.URL;


/**
 * Conversion utility functions from service sources to actual {@link CoffeeNetApplication} instances.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
class ApplicationUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationUtils.class);

    private ApplicationUtils() {
    }

    public static CoffeeNetApplication fromServiceInstance(ServiceInstance instance) {

        try {
            String name = instance.getServiceId();
            URL url = instance.getUri().toURL();

            CoffeeNetApplication.Builder builder = CoffeeNetApplication.withNameAndApplicationUrl(name, url);
            instance.getMetadata();

            return builder.build();
        } catch (MalformedURLException e) {
            LOGGER.warn(String.format("Could not map from service instance {}", instance), e);

            return null;
        }
    }
}
