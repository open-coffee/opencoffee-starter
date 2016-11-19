package coffee.synyx.autoconfigure.discovery.service;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;

import java.util.HashSet;
import java.util.List;

import static coffee.synyx.autoconfigure.CoffeeNetConfigurationProperties.DEVELOPMENT;

import static java.util.Arrays.asList;
import static java.util.Collections.emptyList;
import static java.util.Collections.singletonList;


/**
 * This implementation provides all registered CoffeeNet applications by the eureka service discovery.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
@ConditionalOnProperty(prefix = "coffeenet", name = "profile", havingValue = DEVELOPMENT, matchIfMissing = true)
public class MockAppService implements AppService {

    @Override
    public List<App> getApps() {

        return asList(new App("Homepage", "https://synyx.de", new HashSet<>(emptyList())),
                new App("Blog", "https://blog.synyx.de", new HashSet<>(emptyList())),
                new App("Host Tagger", "https://hosttagger.synyx.coffee/", new HashSet<>(singletonList("ROLE_ADMIN"))));
    }
}
