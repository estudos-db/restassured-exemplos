package org.lucas.fakestore.products1;

import java.util.Objects;

class Rating {

    private String rate;
    private Integer count;

    public Rating(String rate, Integer count) {
        this.rate = rate;
        this.count = count;
    }

    public Rating() {
    }

    public String getRate() {
        return rate;
    }

    public Integer getCount() {
        return count;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Rating response = (Rating) o;
        return rate.equals(response.rate) && count.equals(response.count);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rate, count);
    }

    @Override
    public String toString() {
        return "Rating{" +
            "rate='" + rate + '\'' +
            ", count='" + count + '\'' +
            '}';
    }
}
