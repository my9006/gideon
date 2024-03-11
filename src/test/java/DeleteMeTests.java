import com.github.fge.jsonschema.messages.JsonSchemaValidationBundle;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.http.ContentType;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

public class DeleteMeTests {

    private WireMockServer wireMockServer;
@BeforeTest
    public void setUp() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();


    }

    public void mock(final JSONObject body) {
        // Configure WireMock to stub the request and define the JSON response
        WireMock.configureFor(8080);
        WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/your/endpoint"))
                .willReturn(WireMock.aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody(body.toString())));
    }

    @Test
    public void deleteMe() {

        final JSONObject body = new JSONObject().put("a", "true");
        mock(body);
        Response response = given()
                .contentType(ContentType.JSON)
                .get("/your/endpoint");


        response.then().assertThat().body(
                matchesJsonSchemaInClasspath("schemas/test.json"));

    }

}
