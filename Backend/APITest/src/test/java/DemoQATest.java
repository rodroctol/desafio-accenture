import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response; // <-- ESTA LINHA RESOLVE O ERRO
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
    @DisplayName("1.1 e 1.2 - Criar User e Token")
    public void testeBase() {
        Map<String, String> payload = new HashMap<>();
        payload.put("userName", userName);
        payload.put("password", "Senha@123!");

        // Criar Usuário
        Response responseUser = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/Account/v1/User");

        responseUser.then().statusCode(201);
        userID = responseUser.path("userID");

        // Gerar Token
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
    @DisplayName("1.5 - Alugar Livro")
    public void alugarLivro() {
        // Log para depuração
        System.out.println("DEBUG: Alugando para o ID: " + userID);

        isbn1 = given().when().get("/BookStore/v1/Books").then().extract().path("books[0].isbn");

        String payload = "{\"userId\": \"" + userID + "\", \"collectionOfIsbns\": [{\"isbn\": \"" + isbn1 + "\"}]}";

        given()
                .header("Authorization", "Bearer " + token) // Verifique se o espaço após Bearer existe!
                .contentType(ContentType.JSON)
                .body(payload)
                .when()
                .post("/BookStore/v1/Books")
                .then()
                .log().ifValidationFails() // Isso vai mostrar o erro detalhado no terminal se falhar
                .statusCode(201);
    }

    @Test
    @Order(3)
    @DisplayName("3.1 - Validar Deleção")
    public void validarDelecao() {
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
                    .statusCode(anyOf(is(204), is(200))); // Aceita 204 ou 200
        }
    }
}