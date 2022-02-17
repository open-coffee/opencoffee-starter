package rocks.coffeenet.platform.domain.web;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.entry;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
class CoffeeNetModelContributorTests {

    private CoffeeNetModel.Builder builder;

    @BeforeEach
    void setup() {

        builder = new CoffeeNetModel.Builder();
    }


    @Test
    @DisplayName("should add singular detail from contributer")
    void singularDetail() {

        CoffeeNetModelContributor c = (b) -> b.withDetail("single", "value");

        c.contribute(builder);

        CoffeeNetModel model = builder.build();
        assertThat(model).containsExactly(entry("single", "value"));
    }


    @Test
    @DisplayName("should add complex detail from contributer")
    void complexDetail() {

        Map<String, Object> complex = Collections.singletonMap("key", "value");
        CoffeeNetModelContributor c = (b) -> b.withDetail("complex", complex);

        c.contribute(builder);

        CoffeeNetModel model = builder.build();
        assertThat(model).contains(entry("complex", complex));
    }


    @Test
    @DisplayName("should add details from multiple contributers")
    void multipleContributors() {

        CoffeeNetModelContributor c1 = (b) -> b.withDetail("key1", "value1");
        CoffeeNetModelContributor c2 = (b) -> b.withDetail("key2", "value2");

        c1.contribute(builder);
        c2.contribute(builder);

        CoffeeNetModel model = builder.build();
        assertThat(model).contains(entry("key2", "value2"), entry("key1", "value1"));
    }


    @Test
    @DisplayName("should add multiple details from contributer")
    void multipleDetails() {

        HashMap<String, Object> map = new HashMap<>();
        map.put("key1", "value1");
        map.put("key2", "value2");

        CoffeeNetModelContributor c = (b) -> b.withDetails(map);

        c.contribute(builder);

        CoffeeNetModel model = builder.build();
        assertThat(model).containsExactly(entry("key1", "value1"), entry("key2", "value2"));
    }
}
