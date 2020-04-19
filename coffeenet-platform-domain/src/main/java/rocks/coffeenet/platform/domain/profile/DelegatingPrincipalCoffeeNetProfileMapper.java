package rocks.coffeenet.platform.domain.profile;

import java.security.Principal;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;


/**
 * An implementation of {@link PrincipalCoffeeNetProfileMapper}, which can delegate to multiple implementations of the
 * interface. The implementations are queried in order and the first non-{@code null} result will be returned.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public class DelegatingPrincipalCoffeeNetProfileMapper implements PrincipalCoffeeNetProfileMapper {

    private final List<PrincipalCoffeeNetProfileMapper> mappers;

    public DelegatingPrincipalCoffeeNetProfileMapper(List<PrincipalCoffeeNetProfileMapper> mappers) {

        if (mappers == null) {
            throw new IllegalArgumentException("list of profile mappers must not be null.");
        }

        if (mappers.isEmpty()) {
            throw new IllegalArgumentException("list of profile mappers must not be empty.");
        }

        this.mappers = Collections.unmodifiableList(new LinkedList<>(mappers));
    }

    @Override
    public CoffeeNetProfile map(Principal principal) {

        Class<? extends Principal> principalClass = principal.getClass();

        return mappers.stream()
            .filter(mapper -> mapper.supports(principalClass))
            .map(mapper -> mapper.map(principal))
            .findFirst()
            .orElse(null);
    }


    @Override
    public boolean supports(Class<? extends Principal> clazz) {

        return mappers.stream().anyMatch(mapper -> mapper.supports(clazz));
    }
}
