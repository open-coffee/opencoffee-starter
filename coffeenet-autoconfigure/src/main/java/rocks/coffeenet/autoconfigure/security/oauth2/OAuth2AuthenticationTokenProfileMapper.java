package rocks.coffeenet.autoconfigure.security.oauth2;

import org.springframework.boot.context.properties.PropertyMapper;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;

import rocks.coffeenet.platform.domain.profile.CoffeeNetProfile;
import rocks.coffeenet.platform.domain.profile.DefaultCoffeeNetProfile;
import rocks.coffeenet.platform.domain.profile.PrincipalCoffeeNetProfileMapper;

import java.net.MalformedURLException;
import java.net.URL;

import java.nio.charset.StandardCharsets;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Principal;


/**
 * Convert an {@link OAuth2AuthenticationToken} into a {@link CoffeeNetProfile}, given that the authentication
 * represents an {@link OidcUser}.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
public class OAuth2AuthenticationTokenProfileMapper implements PrincipalCoffeeNetProfileMapper {

    @Override
    public CoffeeNetProfile map(Principal principal) {

        if (!(principal instanceof OAuth2AuthenticationToken)) {
            return null;
        }

        OAuth2AuthenticationToken token = (OAuth2AuthenticationToken) principal;

        if (!(token.getPrincipal() instanceof OidcUser)) {
            return null;
        }

        OidcUser oidcUser = (OidcUser) token.getPrincipal();

        // Prepare the profile
        String uniqueId = hashed(oidcUser.getSubject());
        String name = oidcUser.getName();

        DefaultCoffeeNetProfile.Builder builder = DefaultCoffeeNetProfile.withUniqueIdentifierAndName(uniqueId, name);

        PropertyMapper mapper = PropertyMapper.get();
        mapper.from(oidcUser::getFullName).whenNonNull().to(builder::withHumanReadableName);
        mapper.from(oidcUser::getProfile).whenNonNull().as(this::mapURL).whenNonNull().to(builder::withProfileURL);
        mapper.from(oidcUser::getPicture).whenNonNull().as(this::mapURL).whenNonNull().to(builder::withPictureURL);
        mapper.from(oidcUser::getEmail).whenNonNull().to(builder::withEmail);

        return builder.build();
    }


    @Override
    public boolean supports(Class<?> clazz) {

        return OAuth2AuthenticationToken.class.isAssignableFrom(clazz);
    }


    private String hashed(String s) {

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(s.getBytes(StandardCharsets.UTF_8));

            StringBuilder sb = new StringBuilder();

            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }

            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("Couldn't get an instance of the SHA-256 digest, this should never happen.", e);
        }
    }


    private URL mapURL(String urlString) {

        try {
            return new URL(urlString);
        } catch (MalformedURLException e) {
            return null;
        }
    }
}
