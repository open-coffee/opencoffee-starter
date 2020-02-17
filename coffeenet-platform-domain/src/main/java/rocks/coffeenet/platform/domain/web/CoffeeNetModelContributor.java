package rocks.coffeenet.platform.domain.web;

/**
 * Contributes additional properties to the global CoffeeNet view model.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@FunctionalInterface
public interface CoffeeNetModelContributor {

    /**
     * Contribute parameters.
     *
     * @param  builder
     */
    void contribute(CoffeeNetModel.Builder builder);
}
