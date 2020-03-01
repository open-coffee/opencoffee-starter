package rocks.coffeenet.platform.domain.app;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@DisplayName("DefaultCoffeeNetApplication")
class DefaultCoffeeNetApplicationTests {

    @Test
    @DisplayName("should construct a valid object with only the required arguments")
    void simpleConstructionWithOnlyRequiredArguments() throws MalformedURLException {

        // Given & When
        CoffeeNetApplication application = DefaultCoffeeNetApplication
                .withNameAndApplicationUrl("application", new URL("http://example.com"))
                .build();

        // Then
        assertThat(application).extracting(CoffeeNetApplication::getName)
            .isNotNull()
            .isEqualTo("application");
        assertThat(application).extracting(CoffeeNetApplication::getApplicationURL)
            .isNotNull()
            .isEqualTo(new URL("http://example.com"));
    }


    @Test
    @DisplayName("should construct a valid object with all arguments provided by builder")
    void complexConstruction() throws MalformedURLException {

        // Given & When
        CoffeeNetApplication application = DefaultCoffeeNetApplication
                .withNameAndApplicationUrl("application", new URL("http://example.com"))
                .withHumanReadableName("The Application")
                .withIconUrl(new URL("http://example.com/appicon.png"))
                .build();

        // Then
        assertThat(application).extracting(CoffeeNetApplication::getName)
            .isNotNull()
            .isEqualTo("application");
        assertThat(application).extracting(CoffeeNetApplication::getApplicationURL)
            .isNotNull()
            .isEqualTo(new URL("http://example.com"));
        assertThat(application).extracting(CoffeeNetApplication::getHumanReadableName)
            .isNotNull()
            .isEqualTo("The Application");
        assertThat(application).extracting(CoffeeNetApplication::getIconURL)
            .isNotNull()
            .isEqualTo(new URL("http://example.com/appicon.png"));
    }
}
