package org.lucas.fakestore.products2;

import static lombok.AccessLevel.PRIVATE;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;
import lombok.extern.jackson.Jacksonized;

@Value
@Builder
@Jacksonized
@AllArgsConstructor(access = PRIVATE)
class ProductRequest {

    String title;
    Double price;
    String category;
    String description;
    String image;
}
