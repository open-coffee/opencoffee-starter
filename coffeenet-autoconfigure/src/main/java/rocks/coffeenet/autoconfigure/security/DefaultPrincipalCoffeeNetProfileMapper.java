package rocks.coffeenet.autoconfigure.security;

import org.springframework.util.StringUtils;

import rocks.coffeenet.platform.domain.profile.CoffeeNetProfile;
import rocks.coffeenet.platform.domain.profile.PrincipalCoffeeNetProfileMapper;

import java.security.Principal;


/**
 * Convert a {@link Principal} into a {@link CoffeeNetProfile}. This is meant as last resort fallback to provide any
 * profile, even when running with authentication that is not yet prepared to make use of the mappings.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
public class DefaultPrincipalCoffeeNetProfileMapper implements PrincipalCoffeeNetProfileMapper {

    @Override
    public CoffeeNetProfile map(Principal principal) {

        if (principal == null) {
            return null;
        }

        String name = principal.getName();

        if (StringUtils.isEmpty(name)) {
            return null;
        }

        // This point seems rather moot, but it tries to promote the idea, to not use
        // personal information (like username) to work with identifying users.
        String uniqueId = HashUtils.sha256hex(name);

        return CoffeeNetProfile.withUniqueIdentifierAndName(uniqueId, name)
            .withHumanReadableName(name)
            .build();
    }
}
