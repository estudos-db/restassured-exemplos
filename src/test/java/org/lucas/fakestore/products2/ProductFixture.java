package org.lucas.fakestore.products2;

public class ProductFixture {

    public static ProductRequest buildRequest() {
        return ProductRequest.builder()
            .title("My new product")
            .price(100.0)
            .category("My category")
            .description("My new product description.")
            .image("My new product image")
            .build();
    }

    public static ProductResponse buildResponse() {
        return ProductResponse.builder()
            .id(1)
            .title("Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops")
            .price(109.95)
            .category("men's clothing")
            .description("Your perfect pack for everyday use and walks in the forest. "
                + "Stash your laptop (up to 15 inches) in the padded sleeve, your everyday")
            .image("https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg")
            .rating(RatingFixture.build())
            .build();
    }
}
