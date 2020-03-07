package rocks.coffeenet.platform.domain.profile;

import java.security.Principal;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
public interface TestPrincipals {

    class ExamplePrincipal implements Principal {

        private final String name;

        public ExamplePrincipal(String name) {

            this.name = name;
        }

        @Override
        public String getName() {

            return name;
        }
    }

    class DifferentExamplePrincipal implements Principal {

        private final String name;

        public DifferentExamplePrincipal(String name) {

            this.name = name;
        }

        @Override
        public String getName() {

            return name;
        }
    }
}
