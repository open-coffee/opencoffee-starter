package coffee.synyx.autoconfigure.web;

import coffee.synyx.autoconfigure.discovery.service.AppQuery;
import coffee.synyx.autoconfigure.discovery.service.AppQuery.Builder;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.security.service.CoffeeNetCurrentUserService;
import coffee.synyx.autoconfigure.security.service.CoffeeNetUserDetails;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.17.0
 */
public class CoffeeNetWebExtractor {

    static final String APP_SERVICE_NAME = "appServiceName";
    static final String USER_SERVICE_NAME = "userServiceName";

    private CoffeeNetWebProperties coffeeNetWebProperties;

    private Map<String, Object> services = new HashMap<>();

    public CoffeeNetWebExtractor(CoffeeNetWebProperties coffeeNetWebProperties) {

        this.coffeeNetWebProperties = coffeeNetWebProperties;
    }

    /**
     * Extracts a map of bundled {@link CoffeeNetApp}s by their category. At this time there are only 'apps' and
     * 'profile' as keys
     *
     * @return  map of {@link CoffeeNetApp}s
     */
    public Optional<Map<String, List<CoffeeNetApp>>> extractApps() {

        CoffeeNetAppService coffeeNetAppService = (CoffeeNetAppService) services.get(APP_SERVICE_NAME);

        if (coffeeNetAppService == null) {
            return Optional.empty();
        }

        Map<String, List<CoffeeNetApp>> preparedCoffeeNetApps = new HashMap<>();
        CoffeeNetCurrentUserService userService = (CoffeeNetCurrentUserService) services.get(USER_SERVICE_NAME);

        // create to retrieve CoffeeNetApps
        Builder queryBuilder = AppQuery.builder();

        if (userService != null) {
            userService.get().ifPresent(userDetails -> queryBuilder.withRoles(userDetails.getAuthoritiesAsString()));
        }

        Map<String, List<CoffeeNetApp>> filteredCoffeeNetApps = coffeeNetAppService.getApps(queryBuilder.build());

        // extract profile application
        String profileServiceName = coffeeNetWebProperties.getProfileServiceName();
        List<CoffeeNetApp> profileApps = filteredCoffeeNetApps.get(profileServiceName);

        if (profileApps != null) {
            CoffeeNetApp profileApp = profileApps.get(0);
            filteredCoffeeNetApps.remove(profileServiceName);
            preparedCoffeeNetApps.put("profile", singletonList(profileApp));
        }

        // retrieve all CoffeeNetApps
        List<CoffeeNetApp> firstCoffeeNetApps = filteredCoffeeNetApps.entrySet().stream().map(entry ->
                    entry.getValue()
                    .get(0)).sorted(Comparator.comparing(CoffeeNetApp::getName)).collect(toList());
        preparedCoffeeNetApps.put("apps", firstCoffeeNetApps);

        return Optional.of(preparedCoffeeNetApps);
    }


    /**
     * Extracts the {@link CoffeeNetWebUser} and returns an {@link Optional}. That is filled with the
     * {@link CoffeeNetWebUser} when the {@link CoffeeNetCurrentUserService} is present and a authenticated user is
     * available.Otherwise it will return an empty {@link Optional}
     *
     * @return  an {@code Optional<CoffeeNetWebUser>} if {@link CoffeeNetCurrentUserService} is available and user is
     *          logged in, otherwise empty {@link Optional}
     */
    public Optional<CoffeeNetWebUser> extractUser() {

        Optional<CoffeeNetWebUser> coffeeNetWebUser = Optional.empty();
        CoffeeNetCurrentUserService userService = (CoffeeNetCurrentUserService) services.get(USER_SERVICE_NAME);

        if (userService != null) {
            Optional<CoffeeNetUserDetails> coffeeNetUserDetails = userService.get();

            if (coffeeNetUserDetails.isPresent()) {
                String username = coffeeNetUserDetails.get().getUsername();
                String email = coffeeNetUserDetails.get().getEmail();
                coffeeNetWebUser = Optional.of(new CoffeeNetWebUser(username, email));
            }
        }

        return coffeeNetWebUser;
    }


    /**
     * Extracts the {@code logout path} for the navigation bar logout button.
     *
     * @return  logout path
     */
    public String extractLogoutPath() {

        return coffeeNetWebProperties.getLogoutPath();
    }


    public void addService(String serviceName, Object service) {

        services.put(serviceName, service);
    }
}
