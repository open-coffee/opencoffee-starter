package rocks.coffeenet.build;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.tasks.SourceSet;
import org.gradle.api.tasks.SourceSetContainer;


/**
 * Enable declaring optional dependencies in a Gradle/Maven compatible way. It is achieved by making use of the Gradle
 * concept of build variants, that translates into {@code optional} dependencies in Maven.
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @since  2.0.0
 */
public class OptionalVariantPlugin implements Plugin<Project> {

    public static String OPTIONAL_VARIANT_NAME = "optional";

    @Override
    public void apply(Project project) {

        //J-
        //@formatter:off
        project.getPlugins().withType(JavaPlugin.class, (plugin) -> {
            project.getExtensions().configure(JavaPluginExtension.class, (java) -> {
                java.registerFeature(OPTIONAL_VARIANT_NAME, feature -> {
                    SourceSetContainer sourceSets = project.getConvention().getPlugin(JavaPluginConvention.class).getSourceSets();
                    SourceSet mainSourceSet = sourceSets.getByName(SourceSet.MAIN_SOURCE_SET_NAME);
                    feature.usingSourceSet(mainSourceSet);
                });
            });
        });
        //@formatter:off
        //J+
    }
}
