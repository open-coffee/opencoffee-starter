package rocks.coffeenet.build;

import de.marcphilipp.gradle.nexus.NexusPublishExtension;
import de.marcphilipp.gradle.nexus.NexusPublishPlugin;

import io.codearte.gradle.nexus.NexusStagingPlugin;

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


// This is taken from https://github.com/spring-projects/spring-boot/blob/master/buildSrc/src/main/java/org/springframework/boot/build/DeployedPlugin.java

/**
 * A plugin applied to a project that should be deployed.
 *
 * @author  Andy Wilkinson
 */
public class DeployedPlugin implements Plugin<Project> {

    /**
     * Name of the task that generates the deployed pom file.
     */
    public static final String GENERATE_POM_TASK_NAME = "generatePomFileForMavenPublication";

    /**
     * Property to determine if to apply publication to SonaType OSS repository.
     */
    public static final String SONATYPE_OSS_PROPERTY_NAME = "sonatype";

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

        if (project.hasProperty(SONATYPE_OSS_PROPERTY_NAME)) {
            applySonaTypeOssPublishing(project);
        }

        if (project.hasProperty(SIGN_GPG_PROPERTY_NAME) || project.hasProperty(SONATYPE_OSS_PROPERTY_NAME)) {
            applyGpgSigning(project, mavenPublication);
        }
        //@formatter:on
        //J+
    }


    private void applyGpgSigning(Project project, Publication publication) {

        project.getPlugins().apply(SigningPlugin.class);
        project.getExtensions().getByType(SigningExtension.class).sign(publication);
    }


    /**
     * Apply the publishing extensions for SonaType OSS repository.
     */
    private void applySonaTypeOssPublishing(Project project) {

        Project rootProject = project.getRootProject();

        if (!project.getRootProject().getPlugins().hasPlugin(NexusStagingPlugin.class)) {
            rootProject.getPlugins().apply(NexusStagingPlugin.class);
        }

        project.getPlugins().apply(NexusPublishPlugin.class);
        project.getExtensions().getByType(NexusPublishExtension.class).getRepositories().sonatype();
    }
}
