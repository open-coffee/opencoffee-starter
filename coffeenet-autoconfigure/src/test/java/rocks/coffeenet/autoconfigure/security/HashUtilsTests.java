package rocks.coffeenet.autoconfigure.security;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@DisplayName("HashUtils")
class HashUtilsTests {

    @Test
    @DisplayName("#sha256hex should produce SHA256 hex output")
    void sha256hex() {

        // We are using MessageDigest underneath. Just make sure, nobody changes the
        // used digest from SHA256 to something else and starts breaking something ;)

        assertThat(HashUtils.sha256hex("example")).isEqualTo(
            "50d858e0985ecc7f60418aaf0cc5ab587f42c2570a884095a9e8ccacd0f6545c");

        // and for good measure the canary against https://xkcd.com/221/
        assertThat(HashUtils.sha256hex("otherexample")).isEqualTo(
            "8420c453912f0485bb70543c3f6835ecbdcab97e801ffbbbcfced311c93f5bdf");
    }
}
