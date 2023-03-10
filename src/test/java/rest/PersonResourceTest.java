package rest;

import entities.Person;
import facades.PersonFacade;
import utils.EMF_Creator;
import io.restassured.RestAssured;
import static io.restassured.RestAssured.given;
import io.restassured.parsing.Parser;
import java.net.URI;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.ws.rs.core.UriBuilder;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.grizzly.http.util.HttpStatus;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import static org.hamcrest.Matchers.equalTo;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PersonResourceTest {

    private static final int SERVER_PORT = 7777;
    private static final String SERVER_URL = "http://localhost/api";
    private static Person r1, r2;

    static final URI BASE_URI = UriBuilder.fromUri(SERVER_URL).port(SERVER_PORT).build();
    private static HttpServer httpServer;
    private static EntityManagerFactory emf;

    static HttpServer startServer() {
        ResourceConfig rc = ResourceConfig.forApplication(new ApplicationConfig());
        return GrizzlyHttpServerFactory.createHttpServer(BASE_URI, rc);
    }

    @BeforeAll
    public static void setUpClass() {
        EMF_Creator.startREST_TestWithDB();
        emf = EMF_Creator.createEntityManagerFactoryForTest();

        httpServer = startServer();
        RestAssured.baseURI = SERVER_URL;
        RestAssured.port = SERVER_PORT;
        RestAssured.defaultParser = Parser.JSON;
    }

    @AfterAll
    public static void closeTestServer() {
        EMF_Creator.endREST_TestWithDB();
        httpServer.shutdownNow();
    }

    @BeforeEach
    public void setUp() {

    }

    @Test
    public void testServerIsUp() {
        System.out.println("Testing is server UP");
        given().when().get("/persons").then().statusCode(200);
    }

    @Test
    public void testPersonByPhone() throws Exception {
        given()
            .contentType("application/json")
            .get("/persons/phone/12345678").then()
            .assertThat()
            .statusCode(HttpStatus.OK_200.getStatusCode())
            .body("lastName", equalTo("lastName"));
    }

    @Test
    public void testPersonByHobby() throws Exception {
        given()
            .contentType("application/json")
            .expect()
            .body("get(0).address.street", equalTo("Street"))
            .when()
            .get("/persons/hobby/swimming");
    }

    // Test add person
    @Test
    public void testAddPerson() throws Exception {
        given()
            .contentType("application/json")
            .body("{\"firstName\":\"firstName\",\"lastName\":\"lastName\",\"email\":\"email\"}")
            .when()
            .post("/persons/add")
            .then()
            .assertThat()
            .statusCode(HttpStatus.OK_200.getStatusCode())
            .body("firstName", equalTo("firstName"));
    }
}
