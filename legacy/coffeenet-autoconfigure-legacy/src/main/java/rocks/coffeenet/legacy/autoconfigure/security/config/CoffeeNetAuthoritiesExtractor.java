package rocks.coffeenet.legacy.autoconfigure.security.config;

import org.slf4j.Logger;

import org.springframework.boot.autoconfigure.security.oauth2.resource.AuthoritiesExtractor;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import static org.slf4j.LoggerFactory.getLogger;

import static org.springframework.util.StringUtils.collectionToCommaDelimitedString;

import static java.lang.invoke.MethodHandles.lookup;


/**
 * Extracts a list of {@link GrantedAuthority authorities} out of the response of the user endpoint of the auth-server.
 * It is used by {@link org.springframework.boot.autoconfigure.security.oauth2.resource.UserInfoTokenServices}.
 *
 * @author  Yannic Klem - klem@synyx.de
 */
public class CoffeeNetAuthoritiesExtractor implements AuthoritiesExtractor {

    private static final Logger LOGGER = getLogger(lookup().lookupClass());

    private static final String AUTHORITIES = "authorities";
    private static final String PRINCIPAL = "principal";
    private static final String DEFAULT_AUTHORITY = "ROLE_UNKNOWN";
    private static final String ERROR_MSG = "Could not extract authorities. Using default authority: "
        + DEFAULT_AUTHORITY;

    /**
     * Extracts a list of {@link GrantedAuthority authorities} out of the provided map.
     *
     * @param  map  The map that contains all information of the user endpoint of the auth-server.
     *
     * @return  A list of all {@link GrantedAuthority authorities} that could get extracted out of the passed map.
     *          Returns a {@link CoffeeNetAuthoritiesExtractor#DEFAULT_AUTHORITY default authority} if no authorities
     *          could get extracted.
     */
    @Override
    public List<GrantedAuthority> extractAuthorities(Map<String, Object> map) {

        List<GrantedAuthority> authoritiesList;

        if (map.containsKey(PRINCIPAL) && map.get(PRINCIPAL) instanceof Map) {
            authoritiesList = extractAuthoritiesOutOfPrincipal((Map) map.get(PRINCIPAL));
        } else {
            LOGGER.warn(ERROR_MSG);

            authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(DEFAULT_AUTHORITY);
        }

        return authoritiesList;
    }


    private static List<GrantedAuthority> extractAuthoritiesOutOfPrincipal(Map principal) {

        List<GrantedAuthority> authoritiesList;

        if (principal.containsKey(AUTHORITIES) && principal.get(AUTHORITIES) instanceof Collection) {
            String authorities = collectionToCommaDelimitedString((Collection) principal.get(AUTHORITIES));

            authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(authorities);
        } else {
            LOGGER.warn(ERROR_MSG);

            authoritiesList = AuthorityUtils.commaSeparatedStringToAuthorityList(DEFAULT_AUTHORITY);
        }

        return authoritiesList;
    }
}
