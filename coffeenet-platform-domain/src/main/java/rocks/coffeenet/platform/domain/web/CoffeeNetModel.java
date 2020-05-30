package rocks.coffeenet.platform.domain.web;

import com.fasterxml.jackson.annotation.JsonAnyGetter;

import java.util.AbstractMap;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;


/**
 * This view model makes common attributes available to the view layer in web applications. It is made available under
 * the name {@link CoffeeNetModel#MODEL_NAME}.
 *
 * <p>Each attribute element can be singular or a hierarchical object like a POJO or nested Map.</p>
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class CoffeeNetModel extends AbstractMap<String, Object> {

    public static final String MODEL_NAME = "coffeenet";

    private final Map<String, Object> attributes;

    private CoffeeNetModel(Builder builder) {

        this.attributes = Collections.unmodifiableMap(new HashMap<>(builder.content));
    }

    @Override
    public Set<Entry<String, Object>> entrySet() {

        return attributes != null ? attributes.entrySet() : Collections.emptySet();
    }


    @JsonAnyGetter
    Map<String, Object> getAttributes() {

        return attributes != null ? attributes : Collections.emptyMap();
    }


    @Override
    public boolean equals(Object o) {

        if (this == o) {
            return true;
        }

        if (o == null || getClass() != o.getClass() || !super.equals(o)) {
            return false;
        }

        return Objects.equals(attributes, ((CoffeeNetModel) o).attributes);
    }


    @Override
    public int hashCode() {

        return Objects.hash(super.hashCode(), attributes);
    }

    /**
     * Builder for creating immutable {@link CoffeeNetModel} instances.
     */
    public static class Builder {

        private final Map<String, Object> content;

        public Builder() {

            this.content = new LinkedHashMap<>();
        }

        /**
         * Contribute a view model property using given {@code key} and {@code value}.
         *
         * @param  key  the details key.
         * @param  value  the details value.
         *
         * @return  the builder.
         */
        public Builder withDetail(String key, Object value) {

            this.content.put(key, value);

            return this;
        }


        /**
         * Contribute multiple view model properties given the contents of {@code details}.
         *
         * @param  details  a map with additional details.
         *
         * @return  the builder.
         */
        public Builder withDetails(Map<String, Object> details) {

            this.content.putAll(details);

            return this;
        }


        /**
         * Create a new {@link CoffeeNetModel} instance based on the state of this builder.
         *
         * @return  the final model.
         */
        public CoffeeNetModel build() {

            return new CoffeeNetModel(this);
        }
    }
}
