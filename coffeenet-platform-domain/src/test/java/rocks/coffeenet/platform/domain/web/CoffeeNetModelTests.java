package rocks.coffeenet.platform.domain.web;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
class CoffeeNetModelTests {

    @Test
    @DisplayName("should honor equals/hashcode contract")
    void honorsEqualsHashcode() {

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("key1", "value1");
        map1.put("key2", "value2");

        CoffeeNetModel model1 = new CoffeeNetModel.Builder().withDetails(map1).build();

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("key1", "value1");
        map2.put("key2", "value2");

        CoffeeNetModel model2 = new CoffeeNetModel.Builder().withDetails(map2).build();

        assertThat(model1).isEqualTo(model2);
        assertThat(model2).isEqualTo(model1);
        assertThat(model1.hashCode()).isEqualTo(model2.hashCode());
        assertThat(model2.hashCode()).isEqualTo(model1.hashCode());
    }


    @Test
    @DisplayName("should honor equals contract on inequality")
    void honorsEqualsInequality() {

        HashMap<String, Object> map1 = new HashMap<>();
        map1.put("key1", "value1");
        map1.put("key2", "valueFoo");

        CoffeeNetModel model1 = new CoffeeNetModel.Builder().withDetails(map1).build();

        HashMap<String, Object> map2 = new HashMap<>();
        map2.put("key1", "value1");
        map2.put("key2", "valueBar");

        CoffeeNetModel model2 = new CoffeeNetModel.Builder().withDetails(map2).build();

        assertThat(model1).isNotEqualTo(model2);
        assertThat(model2).isNotEqualTo(model1);
    }
}
