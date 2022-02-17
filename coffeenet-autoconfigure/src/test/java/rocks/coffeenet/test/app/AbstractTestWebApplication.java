package rocks.coffeenet.test.app;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import rocks.coffeenet.platform.domain.profile.CoffeeNetProfile;

import java.util.Collections;
import java.util.Map;


/**
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@RestController
public class AbstractTestWebApplication {

    @GetMapping("/with-profile")
    public Map<String, Object> withProfile(CoffeeNetProfile profile) {

        return Collections.singletonMap("profile", profile);
    }
}
