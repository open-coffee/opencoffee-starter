package rocks.coffeenet.platform.domain.app;

import java.io.Serializable;

import java.net.URL;

import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;


/**
 * Simple default implementation of {@link CoffeeNetApplication}. Instances are created only via a builder pattern with
 * {@link CoffeeNetApplication.Builder}.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */

class DefaultCoffeeNetApplication implements CoffeeNetApplication, Serializable {

    private final String name;
    private final URL applicationUrl;

    private String humanReadableName;
    private URL iconUrl;

    private Set<String> authorities = Collections.emptySet();

    DefaultCoffeeNetApplication(String name, URL applicationUrl) {

        this.name = name;
        this.applicationUrl = applicationUrl;
    }

    @Override
    public String getName() {

        return name;
    }


    @Override
    public URL getApplicationURL() {

        return applicationUrl;
    }


    @Override
    public String getHumanReadableName() {

        return humanReadableName;
    }


    void setHumanReadableName(String humanReadableName) {

        this.humanReadableName = humanReadableName;
    }


    @Override
    public URL getIconURL() {

        return iconUrl;
    }


    void setIconUrl(URL iconUrl) {

        this.iconUrl = iconUrl;
    }


    @Override
    public Set<String> getAuthorities() {

        return authorities;
    }


    void setAuthorities(Set<String> authorities) {

        this.authorities = Collections.unmodifiableSet(new TreeSet<>(authorities));
    }
}
