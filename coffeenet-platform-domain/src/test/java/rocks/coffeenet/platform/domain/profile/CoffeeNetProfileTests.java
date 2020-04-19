package rocks.coffeenet.platform.domain.profile;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@DisplayName("CoffeeNetProfile")
class CoffeeNetProfileTests {

    @Test
    @DisplayName("should construct a valid object with only the required arguments")
    void simpleConstructionWithOnlyRequiredArguments() {

        // Given & When
        CoffeeNetProfile profile = CoffeeNetProfile
                .withUniqueIdentifierAndName("the-id", "coffeenet")
                .build();

        // Then
        assertThat(profile).extracting(CoffeeNetProfile::getUniqueIdentifier)
            .isNotNull()
            .isEqualTo("the-id");
        assertThat(profile).extracting(CoffeeNetProfile::getName)
            .isNotNull()
            .isEqualTo("coffeenet");
    }


    @Test
    @DisplayName("should construct a valid object with all arguments provided by builder")
    void complexConstruction() throws MalformedURLException {

        // Given & When
        CoffeeNetProfile profile = CoffeeNetProfile
                .withUniqueIdentifierAndName("the-id", "coffeenet")
                .withHumanReadableName("CoffeeNet User")
                .withEmail("coffeenet@example.com").withPictureURL(new URL("http://example.com/~coffeenet/me.jpg"))
                .withProfileURL(new URL("http://example.com/~coffeenet/"))
                .build();

        // Then
        assertThat(profile).extracting(CoffeeNetProfile::getUniqueIdentifier)
            .isNotNull()
            .isEqualTo("the-id");
        assertThat(profile).extracting(CoffeeNetProfile::getName)
            .isNotNull()
            .isEqualTo("coffeenet");
        assertThat(profile).extracting(CoffeeNetProfile::getHumanReadableName)
            .isNotNull()
            .isEqualTo("CoffeeNet User");
        assertThat(profile).extracting(CoffeeNetProfile::getEmail)
            .isNotNull()
            .isEqualTo("coffeenet@example.com");
        assertThat(profile).extracting(CoffeeNetProfile::getPictureURL)
            .isNotNull()
            .isEqualTo(new URL("http://example.com/~coffeenet/me.jpg"));
        assertThat(profile).extracting(CoffeeNetProfile::getProfileURL)
            .isNotNull()
            .isEqualTo(new URL("http://example.com/~coffeenet/"));
    }
}
