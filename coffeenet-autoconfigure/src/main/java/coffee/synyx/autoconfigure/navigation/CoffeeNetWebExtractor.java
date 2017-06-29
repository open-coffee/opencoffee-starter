package coffee.synyx.autoconfigure.navigation;

import coffee.synyx.autoconfigure.discovery.service.AppQuery;
import coffee.synyx.autoconfigure.discovery.service.AppQuery.Builder;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetApp;
import coffee.synyx.autoconfigure.discovery.service.CoffeeNetAppService;
import coffee.synyx.autoconfigure.security.service.CoffeeNetCurrentUserService;
import coffee.synyx.autoconfigure.security.service.CoffeeNetUserDetails;

import java.util.Comparator;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static coffee.synyx.autoconfigure.navigation.CoffeeNetWebExtractor.CoffeeNetServices.APP_SERVICE;
import static coffee.synyx.autoconfigure.navigation.CoffeeNetWebExtractor.CoffeeNetServices.USER_SERVICE;

import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.18.0
 */
class CoffeeNetWebExtractor {

    /**
     * Service to register.
     */
    public enum CoffeeNetServices {

        APP_SERVICE,
        USER_SERVICE
    }

    private CoffeeNetWebProperties coffeeNetWebProperties;
    private Map<CoffeeNetServices, Object> services = new EnumMap<>(CoffeeNetServices.class);

    CoffeeNetWebExtractor(CoffeeNetWebProperties coffeeNetWebProperties) {

        this.coffeeNetWebProperties = coffeeNetWebProperties;
    }

    /**
     * Extracts a map of bundled {@link CoffeeNetApp}s by their category. At this time there are only 'apps' and
     * 'profile' as keys
     *
     * @return  map of {@link CoffeeNetApp}s
     */
    Optional<Map<String, List<CoffeeNetApp>>> extractApps() {

        Optional<CoffeeNetAppService> coffeeNetAppService = getCoffeeNetAppService();

        if (!coffeeNetAppService.isPresent()) {
            return Optional.empty();
        }

        Map<String, List<CoffeeNetApp>> preparedCoffeeNetApps = new HashMap<>();
        Optional<CoffeeNetCurrentUserService> userService = getCoffeeNetCurrentUserService();

        // create to retrieve CoffeeNet apps
        Builder queryBuilder = AppQuery.builder();

        // add user roles to query if there is a CoffeeNet user
        userService.ifPresent(coffeeNetCurrentUserService ->
                coffeeNetCurrentUserService.get()
                .ifPresent(userDetails -> queryBuilder.withRoles(userDetails.getAuthoritiesAsString())));

        Map<String, List<CoffeeNetApp>> filteredCoffeeNetApps = coffeeNetAppService.get()
                .getApps(queryBuilder.build());

        // extract profile application
        String profileServiceName = coffeeNetWebProperties.getProfileServiceName();
        List<CoffeeNetApp> profileApps = filteredCoffeeNetApps.get(profileServiceName);

        if (profileApps != null) {
            CoffeeNetApp profileApp = profileApps.get(0);
            filteredCoffeeNetApps.remove(profileServiceName);
            preparedCoffeeNetApps.put("profile", singletonList(profileApp));
        }

        // retrieve all CoffeeNetApps
        List<CoffeeNetApp> firstCoffeeNetApps = filteredCoffeeNetApps.entrySet()
                .stream()
                .map(entry -> entry.getValue().get(0))
                .sorted(Comparator.comparing(CoffeeNetApp::getName))
                .collect(toList());
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
    Optional<CoffeeNetWebUser> extractUser() {

        Optional<CoffeeNetWebUser> coffeeNetWebUser = Optional.empty();
        Optional<CoffeeNetCurrentUserService> userService = getCoffeeNetCurrentUserService();

        if (userService.isPresent()) {
            Optional<CoffeeNetUserDetails> coffeeNetUserDetails = userService.get().get();

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
    String extractLogoutPath() {

        return coffeeNetWebProperties.getLogoutPath();
    }


    /**
     * Registers a CoffeeNet service to retrieve information from.
     *
     * @param  serviceName  as identification
     * @param  service  service to register
     */
    void registerService(CoffeeNetServices serviceName, Object service) {

        services.put(serviceName, service);
    }


    private Optional<CoffeeNetAppService> getCoffeeNetAppService() {

        Object service = services.get(APP_SERVICE);

        if (service == null) {
            return Optional.empty();
        }

        return Optional.of((CoffeeNetAppService) service);
    }


    private Optional<CoffeeNetCurrentUserService> getCoffeeNetCurrentUserService() {

        Object service = services.get(USER_SERVICE);

        if (service == null) {
            return Optional.empty();
        }

        return Optional.of((CoffeeNetCurrentUserService) service);
    }
}
