package rocks.coffeenet.legacy.autoconfigure.navigation;

import rocks.coffeenet.legacy.autoconfigure.discovery.service.CoffeeNetApp;


/**
 * Represents {@link CoffeeNetApp} information.
 *
 * @author  Tobias Schneider - schneider@synyx.de
 * @since  0.25.0
 */
public final class CoffeeNetNavigationAppInformation {

    private final String groupId;
    private final String artifactId;
    private final String version;
    private final String parentVersion;
    private final String parentArtifactId;
    private final String parentGroupId;

    public CoffeeNetNavigationAppInformation(String groupId, String artifactId, String version, String parentVersion,
        String parentArtifactId, String parentGroupId) {

        this.groupId = groupId;
        this.artifactId = artifactId;
        this.version = version;
        this.parentVersion = parentVersion;
        this.parentArtifactId = parentArtifactId;
        this.parentGroupId = parentGroupId;
    }

    public String getGroupId() {

        return groupId;
    }


    public String getArtifactId() {

        return artifactId;
    }


    public String getVersion() {

        return version;
    }


    public String getParentVersion() {

        return parentVersion;
    }


    public String getParentArtifactId() {

        return parentArtifactId;
    }


    public String getParentGroupId() {

        return parentGroupId;
    }
}
