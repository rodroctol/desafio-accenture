import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.HashMap;
import java.util.Map;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DemoQATest {

    private static String baseUrl = "https://demoqa.com";
    private static String userID;
    private static String token;
    private static String userName = "user_bot_" + System.currentTimeMillis();
    private static String isbn1;

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = baseUrl;
    }

    @Test
    @Order(1)
    @DisplayName("1.1 e 1.2 - Create User and Token")
    public void testBase() {
        Map<String, String> payload = new HashMap<>();
        payload.put("userName", userName);
        payload.put("password", "Senha@123!");

        Response responseUser = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/Account/v1/User");

        responseUser.then().statusCode(201);
        userID = responseUser.path("userID");

        token = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/Account/v1/GenerateToken")
                .then()
                .statusCode(200)
                .extract().path("token");
    }

    @Test
    @Order(2)
    @DisplayName("1.5 - Rent Book")
    public void rentBook() {
        System.out.println("DEBUG: Renting for User ID: " + userID);

        isbn1 = given().when().get("/BookStore/v1/Books").then().extract().path("books[0].isbn");

        String payload = "{\"userId\": \"" + userID + "\", \"collectionOfIsbns\": [{\"isbn\": \"" + isbn1 + "\"}]}";

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .log().ifValidationFails()
                .statusCode(201);
    }

    @Test
    @Order(3)
    @DisplayName("3.1 - Validate Deletion")
    public void validateDeletion() {
        Map<String, String> deletePayload = new HashMap<>();
        deletePayload.put("isbn", isbn1);
        deletePayload.put("userId", userID);

        given()
                .header("Authorization", "Bearer " + token)
                .contentType(ContentType.JSON)
                .body(deletePayload)
                .when()
                .delete("/BookStore/v1/Book")
                .then()
                .statusCode(204);
    }

    @AfterAll
    public static void cleanup() {
        if (token != null && userID != null) {
            given()
                    .header("Authorization", "Bearer " + token)
                    .when()
                    .delete("/Account/v1/User/" + userID)
                    .then()
                    .statusCode(anyOf(is(204), is(200)));
        }
    }
}