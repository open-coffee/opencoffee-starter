package rocks.coffeenet.platform.domain.web;

/**
 * Contributes additional properties to the global CoffeeNet view model.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 */
@FunctionalInterface
public interface CoffeeNetModelContributor {

    /**
     * Contribute details to the final {@link CoffeeNetModel} via a builder pattern.
     *
     * @param  builder  a builder to add details to the model.
     */
    void contribute(CoffeeNetModel.Builder builder);
}
