package org.lucas.fakestore.products2;

import static com.google.common.collect.Comparators.isInOrder;
import static io.restassured.RestAssured.given;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;
import static java.util.Collections.reverseOrder;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.everyItem;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import java.util.Collection;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class ProductTest {

    private static final String BASE_URI = "https://fakestoreapi.com";
    private static final String PRODUCTS_URI = "products";
    private static final String CATEGORIES_URI = "categories";
    private static final String CATEGORY_URI = "category";

    @BeforeAll
    public static void setup() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.basePath = PRODUCTS_URI;
    }

    @Test
    @DisplayName("Deve buscar todos produtos com sucesso")
    void buscarTodosProdutosComSucesso() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get()
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body(matchesJsonSchemaInClasspath("schemas/productsSchema.json"))
            .body("size()", greaterThan(10));
    }

    @Test
    @DisplayName("Deve buscar apenas três produtos com sucesso")
    void buscarTresProdutosComSucesso() {
        given()
            .accept(ContentType.JSON)
            .queryParam("limit", "3")
        .when()
            .get()
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body(matchesJsonSchemaInClasspath("schemas/productsSchema.json"))
            .body("size()", is(3));
    }

    @Test
    @DisplayName("Deve buscar todos produtos em ordem decrescente com sucesso")
    void buscarProdutosEmOrdemDecrescenteComSucesso() {
        Collection<Integer> ids = given()
            .accept(ContentType.JSON)
            .queryParam("sort", "desc")
        .when()
            .get()
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body(matchesJsonSchemaInClasspath("schemas/productsSchema.json"))
            .extract()
            .jsonPath()
            .getList("id");

        assertTrue(isInOrder(ids, reverseOrder()));
    }

    @Test
    @DisplayName("Deve buscar um produto com sucesso")
    void buscarProdutoComSucesso() {
        ProductResponse produtoEsperado = ProductFixture.buildResponse();

        ProductResponse response = given()
            .accept(ContentType.JSON)
            .pathParam("numProduto", "1")
        .when()
            .get("/{numProduto}")
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .extract()
            .as(ProductResponse.class);

        assertEquals(produtoEsperado, response);
    }

    @Test
    @DisplayName("Deve buscar todas categorias com sucesso")
    void buscarTodasCategoriasComSucesso() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get(CATEGORIES_URI)
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body(matchesJsonSchemaInClasspath("schemas/categoriesSchema.json"))
            .body("size()", is(4));
    }

    @Test
    @DisplayName("Deve buscar todos produtos da categoria electronics com sucesso")
    void buscarTodosProdutosDaCategoriaElectronicsComSucesso() {
        String categoriaBuscada = "electronics";

        Collection<String> categoriasProdutos = given()
            .accept(ContentType.JSON)
            .pathParam("nomeCategoria", categoriaBuscada)
        .when()
            .get(CATEGORY_URI + "/{nomeCategoria}")
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body(matchesJsonSchemaInClasspath("schemas/productsSchema.json"))
            .extract()
            .jsonPath()
            .getList("category");

        assertThat(categoriasProdutos, everyItem(is("electronics")));
    }

    @Test
    @DisplayName("Deve cadastrar um produto com sucesso")
    void cadastrarProdutoComSucesso() {
        ProductRequest novoProduto = ProductFixture.buildRequest();

        given()
            .accept(ContentType.JSON)
            .body(novoProduto)
        .when()
            .post()
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .assertThat()
            .body("id", notNullValue());
    }

    @Test
    @DisplayName("Deve alterar um produto com sucesso")
    void alterarProdutoComSucesso() {
        Integer idProdutoAlterado = 1;
        ProductRequest produtoAlterado = ProductFixture.buildRequest();

        given()
            .accept(ContentType.JSON)
            .pathParam("numProduto", idProdutoAlterado)
            .body(produtoAlterado)
        .when()
            .put("/{numProduto}")
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .assertThat()
            .body("id", is(idProdutoAlterado));
    }

    @Test
    @DisplayName("Deve excluir um produto com sucesso")
    void excluirProdutoComSucesso() {
        ProductResponse produtoExcluido = ProductFixture.buildResponse();

        ProductResponse response = given()
            .accept(ContentType.JSON)
            .pathParam("numProduto", produtoExcluido.getId())
            .body(produtoExcluido)
        .when()
            .delete("/{numProduto}")
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .extract()
            .as(ProductResponse.class);

        assertEquals(produtoExcluido, response);
    }
}
