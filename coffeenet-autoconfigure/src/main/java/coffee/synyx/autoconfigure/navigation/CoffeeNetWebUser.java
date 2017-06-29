package coffee.synyx.autoconfigure.navigation;

import org.apache.commons.codec.binary.Hex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.lang.String.format;
import static java.lang.invoke.MethodHandles.lookup;


/**
 * Represents a CoffeeNet user.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.15.0
 */
public final class CoffeeNetWebUser {

    private static final Logger LOGGER = LoggerFactory.getLogger(lookup().lookupClass());

    private final String username;
    private final String email;

    public CoffeeNetWebUser(String username, String email) {

        this.username = username;
        this.email = email;
    }

    public String getUsername() {

        return username;
    }


    public String getEmail() {

        return email;
    }


    public String getAvatar() {

        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            final byte[] emailAsBytes = email.getBytes("UTF-8");
            final String md5HashOfEmail = Hex.encodeHexString(md5.digest(emailAsBytes));

            return format("https://gravatar.com/avatar/%s?size=64", md5HashOfEmail);
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            LOGGER.debug("/> Fallback to default avatar", e);
        }

        return "/img/default_avatar.jpg";
    }
}
