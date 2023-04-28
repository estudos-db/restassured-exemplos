package org.lucas.fakestore.products1;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.jupiter.api.Assertions.assertEquals;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

class ProductTest {

    @Test
    void buscarTodosProdutosComSucesso() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("https://fakestoreapi.com/products")
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", greaterThan(10));
    }

    @Test
    void buscarTresProdutosComSucesso() {
        given()
            .accept(ContentType.JSON)
            .queryParam("limit", "3")
        .when()
            .get("https://fakestoreapi.com/products")
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", is(3));
    }

    @Test
    void buscarProdutosEmOrdemDecrescenteComSucesso() {
        given()
            .accept(ContentType.JSON)
            .queryParam("sort", "desc")
        .when()
            .get("https://fakestoreapi.com/products")
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", is(20))
            .body("[0].id", is(20))
            .body("[0].title", is("DANVOUY Womens T Shirt Casual Cotton Short"));
    }

    @Test
    void buscarProdutoComSucesso() {
        ProductResponse produtoEsperado = new ProductResponse("1",
            "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
            109.95, "men's clothing",
            "Your perfect pack for everyday use and walks in the forest. "
                + "Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
            "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg", new Rating("3.9", 120));

        ProductResponse response = given()
            .accept(ContentType.JSON)
            .pathParam("numProduto", "1")
        .when()
            .get("https://fakestoreapi.com/products/{numProduto}")
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .extract()
            .as(ProductResponse.class);

        assertEquals(produtoEsperado, response);
    }

    @Test
    void buscarTodasCategoriasComSucesso() {
        given()
            .accept(ContentType.JSON)
        .when()
            .get("https://fakestoreapi.com/products/categories")
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", is(4));
    }

    @Test
    void buscarTodosProdutosDaCategoriaElectronicsComSucesso() {
        given()
            .accept(ContentType.JSON)
            .pathParam("nomeCategoria", "electronics")
        .when()
            .get("https://fakestoreapi.com/products/category/{nomeCategoria}")
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .body("size()", is(6));
    }

    @Test
    void cadastrarProdutoComSucesso() {
        ProductRequest novoProduto = new ProductRequest("My new product", 100.0, "My category",
            "My new product description", "My new product image");

        given()
            .accept(ContentType.JSON)
            .body(novoProduto)
        .when()
            .post("https://fakestoreapi.com/products")
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .assertThat()
            .body("id", notNullValue());
    }

    @Test
    void alterarProdutoComSucesso() {
        Integer idProdutoAlterado = 1;
        ProductRequest produtoAlterado = new ProductRequest("My new product", 100.0, "My category",
            "My new product description", "My new product image");

        given()
            .accept(ContentType.JSON)
            .pathParam("numProduto", idProdutoAlterado)
            .body(produtoAlterado)
        .when()
            .put("https://fakestoreapi.com/products/{numProduto}")
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .assertThat()
            .body("id", is(idProdutoAlterado));
    }

    @Test
    void excluirProdutoComSucesso() {
        ProductResponse produtoExcluido = new ProductResponse("1",
            "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
            109.95, "men's clothing",
            "Your perfect pack for everyday use and walks in the forest. "
                + "Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
            "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg", new Rating("3.9", 120));

        ProductResponse response = given()
            .accept(ContentType.JSON)
            .pathParam("numProduto", produtoExcluido.getId())
            .body(produtoExcluido)
        .when()
            .delete("https://fakestoreapi.com/products/{numProduto}")
        .then()
            .assertThat()
            .statusCode(200)
            .contentType(ContentType.JSON)
            .extract()
            .as(ProductResponse.class);

        assertEquals(produtoExcluido, response);
    }
}
