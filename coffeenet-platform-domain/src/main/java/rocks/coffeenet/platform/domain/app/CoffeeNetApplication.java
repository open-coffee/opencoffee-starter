package rocks.coffeenet.platform.domain.app;

import java.net.URL;


/**
 * General interface describing an user-facing application in the CoffeeNet platform.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
public interface CoffeeNetApplication {

    /**
     * The technical name of this CoffeeNet application. This MUST NOT return {@code null}.
     *
     * @return  a technical name.
     */
    String getName();


    /**
     * The human readable name for this CoffeeNet application. This MAY return {@code null}.
     *
     * <p>It is meant for UI display purposes and can be prepared for I18N according to external factors like browser
     * language headers or authenticated user.</p>
     *
     * @return  a human readable name, if present.
     */
    String getHumanReadableName();


    /**
     * The base URL of this CoffeeNet application. This MUST NOT return {@code null}.
     *
     * @return  an URL to access this application.
     */
    URL getApplicationURL();


    /**
     * The icon URL of this CoffeeNet application. This MAY return {@code null}.
     *
     * @return  an URL to an icon for this application, if present.
     */
    URL getIconURL();
}
