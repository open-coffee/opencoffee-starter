import de.schauderhaft.degraph.check.ConstraintBuilder;

import org.junit.Test;

import static de.schauderhaft.degraph.check.JCheck.classpath;
import static de.schauderhaft.degraph.check.JCheck.violationFree;
import static de.schauderhaft.degraph.check.JLayer.oneOf;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.core.Is.is;


/**
 * @author  Tobias Schneider - schneider@synyx.de
 */
public class DependencyCheckTest {

    @Test
    public void checkCorrectSlicing() {

        ConstraintBuilder graph = classpath().noJars()
            .printTo("violations.graphml")
            .including("coffee.synyx.autoconfigure.**")
            .excluding("coffee.synyx.autoconfigure.**.CoffeeNetDiscoveryConfiguration")
            .excluding("coffee.synyx.autoconfigure.**.IntegrationCoffeeNetServiceDiscoveryConfiguration")
            .excluding("coffee.synyx.autoconfigure.**.CoffeeNetAppsEndpoint")
            .excluding("coffee.synyx.autoconfigure.**.CoffeeNetAppsEndpointTest*")
            .withSlicing("module", "coffee.synyx.autoconfigure.(*).**")
            .allow(oneOf("discovery", "logging", "security"));

        assertThat(graph, is(violationFree()));
    }
}
