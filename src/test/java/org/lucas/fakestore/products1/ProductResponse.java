package org.lucas.fakestore.products1;

import java.util.Objects;

class ProductResponse {

    private String id;
    private String title;
    private Double price;
    private String category;
    private String description;
    private String image;
    private Rating rating;

    public ProductResponse(String id, String title, Double price, String category, String description, String image,
        Rating rating) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.category = category;
        this.description = description;
        this.image = image;
        this.rating = rating;
    }

    public ProductResponse() {
    }

    public String getId() {
        return id;
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

    public Rating getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        ProductResponse that = (ProductResponse) o;
        return id.equals(that.id) && title.equals(that.title) && price.equals(that.price) && category.equals(
            that.category)
            && description.equals(that.description) && image.equals(that.image) && rating.equals(that.rating);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, price, category, description, image, rating);
    }
}
