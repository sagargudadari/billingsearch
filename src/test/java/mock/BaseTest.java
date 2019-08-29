package mock;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.mockserver.integration.ClientAndServer;

import javax.xml.bind.JAXBException;
import java.io.IOException;

import static org.mockserver.integration.ClientAndServer.startClientAndServer;

public class BaseTest {

    private static ClientAndServer mockServer;


    @BeforeClass
    public static void setupMockServer() throws IOException, JAXBException {
        mockServer = startClientAndServer(8000);

        Expectations.createDefaultExpectations(mockServer);
    }

    @AfterClass
    public static void after() {
        mockServer.stop();
    }

}