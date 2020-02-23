package rocks.coffeenet.platform.domain.web;

import nl.jqno.equalsverifier.EqualsVerifier;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
class CoffeeNetModelTests {

    @Test
    @DisplayName("should honor the equals/hashCode contract")
    void honorsEqualsHashcode() {

        EqualsVerifier.forClass(CoffeeNetModel.class)
            .usingGetClass()
            .verify();
    }


    @Test
    @DisplayName(".Builder should produce ONLY the specified model map")
    void builderProducesExactlyTheExpectedMap() {

        HashMap<String, Object> expected = new HashMap<>();
        expected.put("key1", "value1");
        expected.put("key2", "value2");

        CoffeeNetModel model = new CoffeeNetModel.Builder()
                .withDetail("key1", "value1")
                .withDetail("key2", "value2")
                .build();

        assertThat(model).containsExactlyEntriesOf(expected);

        CoffeeNetModel model2 = new CoffeeNetModel.Builder()
                .withDetails(expected)
                .build();

        assertThat(model2).containsExactlyEntriesOf(expected);
    }
}
