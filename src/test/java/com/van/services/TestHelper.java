package com.van.services;

import com.van.services.model.Item;

public class TestHelper {
    public static final String TEST_ID = "453543ewt";
    public static final String TEST_TOKEN = "{\"sid\": \"" + TEST_ID + "\"}";

    public static final String TEST_ITEM_ID = "54436";
    public static final String TEST_ITEM_TITLE = "Test_title";
    public static final Item TEST_ITEM_VAL = Item.builder().title(TEST_ITEM_TITLE).build();

    public static final String TEST_ITEM_ID2 = "54436sdfdg";
    public static final String TEST_ITEM_TITLE2 = "Test_title2";

    public static final Double TEST_VAL = 3.5;
    private TestHelper() {
    }
}
