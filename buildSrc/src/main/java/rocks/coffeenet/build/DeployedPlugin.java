package rocks.coffeenet.build;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlatformPlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;


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
        //@formatter:on
        //J+
    }
}
