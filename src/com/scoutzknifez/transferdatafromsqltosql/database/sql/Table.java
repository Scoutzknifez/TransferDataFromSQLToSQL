package com.scoutzknifez.transferdatafromsqltosql.database.sql;

import com.scoutzknifez.transferdatafromsqltosql.structures.dto.*;
import com.scoutzknifez.transferdatafromsqltosql.database.sql.insertion.Databasable;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * The enumerators in this class file MUST be named the same as
 * the table names inside of the database.  They are CASE SENSITIVE.
 *
 * The {@link Class} parameter for each parameter must implement {@link Databasable}
 * and must have a static method called "createInstance" with a ResultSet as a parameter.
 */
@Getter
@AllArgsConstructor
public enum Table {
    INVENTORIES(InventorySlot.class, "inventories"),
    PRODUCTS(Product.class, "products"),
    PRODUCT_IMAGES(ProductImage.class, "product_images"),
    PRODUCT_REVIEWS(ProductReview.class, "product_reviews"),
    USERS(User.class, "users"),
    WEATHER_FOR_TIME(WeatherForTime.class, "WEATHER_FOR_TIME");

    private Class constructorClass;
    private String tableCasing;
}
