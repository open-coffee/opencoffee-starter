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
                .including("rocks.coffeenet.legacy.autoconfigure.**")
                .withSlicing("module", "rocks.coffeenet.legacy.autoconfigure.(*).**")
                .allow(oneOf("discovery", "security"));

        assertThat(graph, is(violationFree()));
    }
}
