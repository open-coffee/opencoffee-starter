package rocks.coffeenet.legacy.autoconfigure.security.config;

import org.slf4j.Logger;

import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;
import org.springframework.boot.autoconfigure.security.oauth2.resource.PrincipalExtractor;

import rocks.coffeenet.legacy.autoconfigure.security.service.CoffeeNetUserDetails;
import rocks.coffeenet.legacy.autoconfigure.security.service.HumanCoffeeNetUser;
import rocks.coffeenet.legacy.autoconfigure.security.service.MachineCoffeeNetUser;

import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * Extracts an instance of {@link CoffeeNetUserDetails} out of the response of the user endpoint of the auth-server. It
 * is used by
 * {@link org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices#getPrincipal(Map)}.
 *
 * @author  Yannic Klem - klem@synyx.de
 */
public class CoffeeNetPrincipalExtractor implements PrincipalExtractor {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private static final String PRINCIPAL_NAME_KEY = "name";
    private static final String PRINCIPAL_KEY = "principal";
    private static final String USERNAME_KEY = "username";
    private static final String MAIL_KEY = "mail";
    private static final String UNKNOWN_VALUE = "UNKNOWN";
    private static final String ERROR_MSG_TEMPLATE = "Could not extract %s. Using default %s";

    private final AuthoritiesExtractor authoritiesExtractor;

    public CoffeeNetPrincipalExtractor(AuthoritiesExtractor authoritiesExtractor) {

        this.authoritiesExtractor = authoritiesExtractor;
    }

    /**
     * Extracts an instance of {@link CoffeeNetUserDetails} out of the provided map.
     *
     * @param  map  The map that contains all information of the user endpoint of the auth-server.
     *
     * @return  An instance of {@link CoffeeNetUserDetails}. Returns an instance {@link HumanCoffeeNetUser} that
     *          returns {@link CoffeeNetPrincipalExtractor#UNKNOWN_VALUE} for every field.
     */
    @Override
    public Object extractPrincipal(Map<String, Object> map) {

        if ((boolean) map.getOrDefault("clientOnly", false)) {
            return extractClientDetails(map);
        } else {
            return extractUserDetails(map);
        }
    }


    private CoffeeNetUserDetails extractClientDetails(Map<String, Object> map) {

        if (map.containsKey(PRINCIPAL_NAME_KEY) && map.get(PRINCIPAL_NAME_KEY) instanceof String) {
            return new MachineCoffeeNetUser((String) map.get(PRINCIPAL_NAME_KEY),
                    authoritiesExtractor.extractAuthorities(map));
        }

        LOGGER.warn(ERROR_MSG_TEMPLATE, PRINCIPAL_NAME_KEY, UNKNOWN_VALUE);

        return new MachineCoffeeNetUser(UNKNOWN_VALUE, authoritiesExtractor.extractAuthorities(map));
    }


    private CoffeeNetUserDetails extractUserDetails(Map<String, Object> map) {

        HumanCoffeeNetUser humanCoffeeUser;

        if (map.containsKey(PRINCIPAL_KEY) && map.get(PRINCIPAL_KEY) instanceof Map) {
            Map principal = (Map) map.get(PRINCIPAL_KEY);

            humanCoffeeUser = new HumanCoffeeNetUser(extractUsername(principal), extractMail(principal),
                    this.authoritiesExtractor.extractAuthorities(map));
        } else {
            humanCoffeeUser = new HumanCoffeeNetUser(UNKNOWN_VALUE, UNKNOWN_VALUE,
                    authoritiesExtractor.extractAuthorities(map));
            LOGGER.warn(ERROR_MSG_TEMPLATE, PRINCIPAL_KEY, humanCoffeeUser);
        }

        return humanCoffeeUser;
    }


    private static String extractUsername(Map principal) {

        String username;

        if (principal.containsKey(USERNAME_KEY) && principal.get(USERNAME_KEY) instanceof String) {
            username = (String) principal.get(USERNAME_KEY);
        } else {
            LOGGER.warn(ERROR_MSG_TEMPLATE, USERNAME_KEY, UNKNOWN_VALUE);
            username = UNKNOWN_VALUE;
        }

        return username;
    }


    private static String extractMail(Map principal) {

        String mail;

        if (principal.containsKey(MAIL_KEY) && principal.get(MAIL_KEY) instanceof String) {
            mail = (String) principal.get(MAIL_KEY);
        } else {
            LOGGER.warn(ERROR_MSG_TEMPLATE, MAIL_KEY, UNKNOWN_VALUE);
            mail = UNKNOWN_VALUE;
        }

        return mail;
    }
}
