package com.van.services.constant;

public final class AppConstants {
    public static final String SPRING_PROFILE_TEST = "test";
    public static final String SPRING_PROFILE_DEVELOPMENT = "dev";
    public static final String SPRING_PROFILE_PRODUCTION = "prod";
    public static final String SPRING_PROFILE_CLOUD = "cloud";
    public static final String BASE_URL = "/api";
    public static final String ITEM_URL = "/items";
    public static final String SHOPPING_CART_URL = "/shopping-carts";
    public static final String ID_URL_PARAM = "/{id}";
    public static final String ID_URL_PARAM_NAME = "id";

    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String AUTHORIZATION_SUF_HEADER = "Bearer ";

    private AppConstants() {
    }
}
