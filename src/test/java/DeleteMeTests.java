import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import lombok.SneakyThrows;
import net.jimblackler.jsonschemafriend.Validator;
import org.json.JSONArray;
import org.json.JSONObject;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;


import net.jimblackler.jsonschemafriend.SchemaStore;
import net.jimblackler.jsonschemafriend.Schema;
import org.testng.reporters.jq.Main;

public class DeleteMeTests {

    private WireMockServer wireMockServer;

    @BeforeTest
    public void setUp() {
        wireMockServer = new WireMockServer(8080);
        wireMockServer.start();


    }

    public void mock(final Object body) {
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

        final JSONObject body = new JSONObject()
                .put("a", new JSONArray("[1600, \"Pennsylvania\", \"Street\", \"Armenia\"]"))
                .put("my_initial_value", 10);

        final JSONArray array = new JSONArray().put(1600)
                .put("Pennsylvania")
                .put("Street")
                .put("RUS");
        mock(body);
        Response response = given()
                .contentType(ContentType.JSON)
                .get("/your/endpoint");

        response.then().assertThat().body(
                matchesJsonSchemaInClasspath("schemas/test.json"));

    }

    @SneakyThrows
    @Test
    public void deleteMe_2() {
//        mock(asd);

//        Response response = given()
//                .contentType(ContentType.JSON)
//                .get("/your/endpoint");


        SchemaStore schemaStore = new SchemaStore();
        Schema schema = schemaStore.loadSchema(Main.class.getResourceAsStream("/schemas/tuple.json"));

        final JSONObject qwe = new JSONObject()
                .put("street_address", "Komits")
                .put("city", "Yerevan");

        final JSONObject ref = new JSONObject().put("a", "str");

        final JSONArray child = new JSONArray("[1,2,3]");

        final JSONArray array = new JSONArray()
                .put(1600)
                .put("Komitas")
                .put("Avenue")
                .put("AM");

        Validator validator = new Validator();
        validator.validate(schema, array.toList());


    }

}
