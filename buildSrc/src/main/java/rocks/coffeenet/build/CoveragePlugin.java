package rocks.coffeenet.build;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.plugins.JavaBasePlugin;
import org.gradle.api.tasks.testing.Test;

import org.gradle.language.base.plugins.LifecycleBasePlugin;

import org.gradle.testing.jacoco.plugins.JacocoPlugin;
import org.gradle.testing.jacoco.tasks.JacocoReport;


/**
 * Enable code-coverage reporting as used by tools like SonarQube.
 *
 * <p>This plugin enables code coverage reporting on Java projects. It by default enables reporting via the preferred
 * channel of XML based reports, while disabling the other HTML based one. It also hooks the reporting into the
 * dependency cycle, so a {@code check} task will generate the reports.</p>
 *
 * @author  Florian 'punycode' Krupicka - zh@punyco.de
 * @see  2.0.0
 */
public class CoveragePlugin implements Plugin<Project> {

    @Override
    public void apply(Project project) {

        //J-
        //@formatter:off
        project.getPlugins().withType(JavaBasePlugin.class, (plugin) -> {
            project.getPlugins().apply(JacocoPlugin.class);

            Task checkTaskName = project.getTasks().getByName(LifecycleBasePlugin.CHECK_TASK_NAME);

            project.getTasks().withType(JacocoReport.class).all((jacoco) -> {
                jacoco.reports((report) -> {
                    report.getXml().getRequired().set(true);
                    report.getHtml().getRequired().set(false);
                });
                project.getTasks().withType(Test.class).all((test) -> jacoco.dependsOn(test));
                checkTaskName.dependsOn(jacoco);
            });
        });
        //@formatter:on
        //J+
    }
}
