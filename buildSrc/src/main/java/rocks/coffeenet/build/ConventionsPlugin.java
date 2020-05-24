package rocks.coffeenet.build;

import org.gradle.api.JavaVersion;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.plugins.JavaPlugin;
import org.gradle.api.plugins.JavaPluginConvention;
import org.gradle.api.plugins.JavaPluginExtension;
import org.gradle.api.publish.PublishingExtension;
import org.gradle.api.publish.maven.MavenPom;
import org.gradle.api.publish.maven.MavenPomDeveloperSpec;
import org.gradle.api.publish.maven.MavenPomIssueManagement;
import org.gradle.api.publish.maven.MavenPomLicenseSpec;
import org.gradle.api.publish.maven.MavenPomOrganization;
import org.gradle.api.publish.maven.MavenPomScm;
import org.gradle.api.publish.maven.MavenPublication;
import org.gradle.api.publish.maven.plugins.MavenPublishPlugin;
import org.gradle.api.tasks.bundling.Jar;
import org.gradle.api.tasks.compile.JavaCompile;
import org.gradle.api.tasks.javadoc.Javadoc;
import org.gradle.api.tasks.testing.Test;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * Apply common conventions to projects that are part of CoffeeNet. It is inspired by the {@code ConventionsPlugin} in
 * Spring Boot.
 *
 * <p>Currently the following conventions are applied:</p>
 *
 * <ul>
 *   <li>Use Java 1.8 compatibility settings on all Java artifacts.</li>
 *   <li>Use UTF-8 encoding on all Java sources and documentation.</li>
 *   <li>Enable retaining parameter names during compilation.</li>
 *   <li>Use JUnit Jupiter as the test framework on all Java test tasks.</li>
 *   <li>Configure published Maven artifact POMs to contain project information.</li>
 *   <li>Publish Javadoc and sources along with the implementation JARs.</li>
 * </ul>
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @see  <a
 *       href="https://github.com/spring-projects/spring-boot/blob/master/buildSrc/src/main/java/org/springframework/boot/build/ConventionsPlugin.java#L115">
 *       Spring Boot - ConventionsPlugin</a>
 * @since  2.0.0
 */
public class ConventionsPlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {

        applyJavaConventions(project);
        applyMavenConventions(project);
    }


    private void applyJavaConventions(Project project) {

        //J-
        //@formatter:off
        project.getPlugins().withType(JavaBasePlugin.class, (java) -> {
            // Set source and target compatibility
            JavaPluginConvention convention = project.getConvention().getPlugin(JavaPluginConvention.class);
            convention.setSourceCompatibility(JavaVersion.VERSION_1_8);
            convention.setTargetCompatibility(JavaVersion.VERSION_1_8);

            // Set source encoding and keeping parameter names for compiled classes
            project.getTasks().withType(JavaCompile.class, (compile) -> {
                compile.getOptions().setEncoding("UTF-8");
                List<String> args = compile.getOptions().getCompilerArgs();
                if (!args.contains("-parameters")) {
                    args.add("-parameters");
                }
            });
            // Set corresponding settings for javadoc tasks
            project.getTasks().withType(Javadoc.class, (javadoc) ->
                javadoc.getOptions()
                    .source(convention.getSourceCompatibility().toString())
                    .encoding("UTF-8"));
            // Enable JUnit Jupiter for tests
            project.getTasks().withType(Test.class, (test) -> {
                test.useJUnitPlatform();
                test.setMaxHeapSize("1024M");
            });
            // Configure JAR manifest generation
            project.getTasks().withType(Jar.class, (jar) -> {
                project.afterEvaluate((evaluated) -> {
                    jar.manifest((manifest) -> {
                        Map<String, Object> attributes = new TreeMap<>();
                        attributes.put("Automatic-Module-Name", project.getName().replace("-", "."));
                        attributes.put("Build-Jdk-Spec", convention.getSourceCompatibility());
                        if (project.getDescription() != null) {
                            attributes.put("Implementation-Title", project.getDescription());
                        }
                        attributes.put("Implementation-Version", project.getVersion());
                        attributes.put("Implementation-Vendor", "CoffeeNet");
                        manifest.attributes(attributes);
                    });
                });
            });
        });
        //@formatter:on
        //J+
    }


    private void applyMavenConventions(Project project) {

        //J-
        //@formatter:off
        project.getPlugins().withType(MavenPublishPlugin.class, (mavenPublish) -> {
            PublishingExtension publishing = project.getExtensions().getByType(PublishingExtension.class);
            publishing.getPublications().withType(MavenPublication.class).all((publication) -> {
                customizePom(publication.getPom(), project);
            });
        });
        project.getPlugins().withType(JavaPlugin.class).all((javaPlugin) -> {
            JavaPluginExtension extension = project.getExtensions().getByType(JavaPluginExtension.class);
            extension.withJavadocJar();
            extension.withSourcesJar();
        });
        //@formatter:on
        //J+
    }


    private void customizePom(MavenPom pom, Project project) {

        pom.getUrl().set("https://coffeenet.rocks");
        pom.getName().set(project.provider(() -> String.format("%s:%s", project.getGroup(), project.getName())));
        pom.getDescription().set(project.provider(project::getDescription));
        pom.organization(this::customizeOrganization);
        pom.licenses(this::customizeLicenses);
        pom.developers(this::customizeDevelopers);
        pom.scm(this::customizeScm);
        pom.issueManagement(this::customizeIssueManagement);
    }


    private void customizeOrganization(MavenPomOrganization organization) {

        organization.getName().set("CoffeeNet");
        organization.getUrl().set("https://coffeenet.rocks");
    }


    private void customizeLicenses(MavenPomLicenseSpec licenses) {

        licenses.license(license -> {
            license.getName().set("Apache License, Version 2.0");
            license.getUrl().set("http://www.apache.org/licenses/LICENSE-2.0");
        });
    }


    private void customizeDevelopers(MavenPomDeveloperSpec developers) {

        developers.developer((developer) -> {
            developer.getName().set("CoffeeNet Contributers");
            developer.getUrl().set("https://github.com/orgs/coffeenet/people");
            developer.getOrganization().set("CoffeeNet");
            developer.getOrganizationUrl().set("https://github.com/coffeenet");
        });
    }


    private void customizeScm(MavenPomScm scm) {

        scm.getConnection().set("scm:git:git://github.com/coffeenet/coffeenet-starter.git");
        scm.getDeveloperConnection().set("scm:git:ssh://git@github.com/coffeenet/coffeenet-starter.git");
        scm.getUrl().set("https://github.com/coffeenet/coffeenet-starter");
    }


    private void customizeIssueManagement(MavenPomIssueManagement issueManagement) {

        issueManagement.getSystem().set("GitHub");
        issueManagement.getUrl().set("https://github.com/coffeenet/coffeenet-starter/issues");
    }
}
