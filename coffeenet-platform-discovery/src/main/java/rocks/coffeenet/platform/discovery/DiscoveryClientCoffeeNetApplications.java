package rocks.coffeenet.platform.discovery;

import org.springframework.cloud.client.discovery.DiscoveryClient;

import rocks.coffeenet.platform.domain.app.CoffeeNetApplication;
import rocks.coffeenet.platform.domain.app.CoffeeNetApplications;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * An implementation of {@link CoffeeNetApplications} based on a Spring Cloud {@link DiscoveryClient} implementation.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class DiscoveryClientCoffeeNetApplications implements CoffeeNetApplications {

    private final DiscoveryClient discoveryClient;

    public DiscoveryClientCoffeeNetApplications(DiscoveryClient discoveryClient) {

        this.discoveryClient = Objects.requireNonNull(discoveryClient, "Must supply a discovery client.");
    }

    @Override
    public List<CoffeeNetApplication> getApplications() {

        // Retrieve the known service names
        return discoveryClient.getServices().stream()
            // Get lists of instances associated with those names
            .map(discoveryClient::getInstances)
            // Extract any instance from the list
            .map(instances -> instances.stream().findAny())
            // Map to instances of CoffeeNetApplication
            .map(o -> o.map(ApplicationUtils::fromServiceInstance))
            // Unwrap and remove potentially empty applications
            .filter(Optional::isPresent)
            .map(Optional::get)
            // Sort by name in case-insensitive order
            .sorted(Comparator.comparing(CoffeeNetApplication::getName, String.CASE_INSENSITIVE_ORDER))
            // return the list
            .collect(Collectors.toList());
    }
}
