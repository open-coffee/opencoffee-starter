package rocks.coffeenet.build;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlatformPlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.publish.Publication;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;

import org.gradle.plugins.signing.SigningExtension;
import org.gradle.plugins.signing.SigningPlugin;

import java.util.Optional;


/**
 * A plugin applied to a project that should be deployed.
 *
 * <p>It in general creates the necessary publications for Maven based publication.</p>
 *
 * @author  Andy Wilkinson
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @see  <a
 *       href="https://github.com/spring-projects/spring-boot/blob/master/buildSrc/src/main/java/org/springframework/boot/build/DeployedPlugin.java">
 *       Spring Boot</a>
 */
public class DeployedPlugin implements Plugin<Project> {

    /**
     * Project property to determine whether to sign the deployed artifacts with GPG. This is automatically enabled
     * when also publishing to SonaType OSS repository.
     */
    public static final String SIGN_GPG_PROPERTY_NAME = "signGpg";

    @Override
    public void apply(Project project) {

        project.getPlugins().apply(MavenPublishPlugin.class);

        // Create a new Maven publication
        PublishingExtension publishing = project.getExtensions().getByType(PublishingExtension.class);
        MavenPublication mavenPublication = publishing.getPublications().create("maven", MavenPublication.class);
        mavenPublication.suppressAllPomMetadataWarnings();

        // Publish all Java artifacts and the platform BOMs

        //J-
        //@formatter:off
        project.getPlugins().withType(JavaPlugin.class).all((plugin) ->
            project.getComponents().matching((component) -> component.getName().equals("java"))
                .all(mavenPublication::from));
        project.getPlugins().withType(JavaPlatformPlugin.class).all((plugin) ->
            project.getComponents().matching((component) -> component.getName().equals("javaPlatform"))
                .all(mavenPublication::from));

        if (project.hasProperty(SIGN_GPG_PROPERTY_NAME)) {
            applyGpgSigning(project, mavenPublication);
        }
        //@formatter:on
        //J+
    }


    private void applyGpgSigning(Project project, Publication publication) {

        project.getPlugins().apply(SigningPlugin.class);

        SigningExtension signing = project.getExtensions().getByType(SigningExtension.class);
        signing.sign(publication);

        // Support signing from in-memory keys via environment variables
        Optional<String> privateKey = nonEmptyEnvironment("GPG_PRIVATE_KEY");
        Optional<String> passphrase = nonEmptyEnvironment("GPG_PASSPHRASE");

        if (privateKey.isPresent() && passphrase.isPresent()) {
            signing.useInMemoryPgpKeys(privateKey.get(), passphrase.get());
        }
    }


    // Helpers
    private static Optional<String> nonEmptyEnvironment(String key) {

        String value = System.getenv(key);

        return value != null ? "".equals(value.trim()) ? Optional.empty() : Optional.of(value) : Optional.empty();
    }
}
