package coffee.synyx.autoconfigure.discovery.service;

/**
 * Application Dto to represent the registered CoffeeNet application from service discovery.
 *
 * @author  David Schilling - schilling@synyx.de
 * @author  Tobias Schneider - schneider@synyx.de
 */
public final class CoffeeNetApp {

    private final String name;
    private final String url;

    public CoffeeNetApp(String name, String url) {

        this.name = name;
        this.url = url;
    }

    public String getName() {

        return name;
    }


    public String getUrl() {

        return url;
    }
}
