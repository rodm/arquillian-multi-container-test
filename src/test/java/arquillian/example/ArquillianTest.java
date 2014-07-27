package arquillian.example;

import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;

import static org.junit.Assert.assertEquals;

@RunWith(Arquillian.class)
public class ArquillianTest {

    @Deployment()
    public static WebArchive createDeployment() {
        File[] runtimeLibs = Maven.resolver().loadPomFromFile("pom.xml")
                .importRuntimeDependencies().resolve()
                .withTransitivity().asFile();

        WebArchive war = ShrinkWrap.create(WebArchive.class)
            .addAsLibraries(runtimeLibs)
            .addPackage(Package.getPackage("arquillian.example"))
            .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml");
        System.out.println(war.toString(true));
        return war;
    }

    @Inject
    private Example bean;

    @Test
    public void should_capitalize_text() {
        assertEquals("Example text", bean.capitalize("example text"));
    }
}
