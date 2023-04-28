package org.lucas.fakestore.products2;

public class RatingFixture {

    public static Rating build() {
        return Rating.builder()
            .rate("3.9")
            .count(120)
            .build();
    }
}
