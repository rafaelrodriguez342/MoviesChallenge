package com.rafaellroca.moviedb.models;

public class Filter {
    private Category category;
    private Type type;

    public Filter(Category category, Type type) {
        this.category = category;
        this.type = type;
    }

    public Category getCategory() {
        return category;
    }

    public Type getType() {
        return type;
    }


   public enum Category {
        POPULAR, TOP_RATED, UPCOMING
    }

    public enum Type {
        MOVIE, TV
    }

    @Override
    public String toString() {
        return type.name() + category.name();
    }
}
