package org.lucas.fakestore.products1;

import java.util.Objects;

class ProductRequest {

    private String title;
    private Double price;
    private String category;
    private String description;
    private String image;

    public ProductRequest(String title, Double price, String category, String description, String image) {
        this.title = title;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
    }

    public ProductRequest() {
    }

    public String getTitle() {
        return title;
    }

    public Double getPrice() {
        return price;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductRequest that = (ProductRequest) o;
        return title.equals(that.title) && price.equals(that.price) && category.equals(
            that.category)
            && description.equals(that.description) && image.equals(that.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, price, category, description, image);
    }
}
