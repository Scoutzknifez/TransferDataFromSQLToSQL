package com.scoutzknifez.transferdatafromsqltosql.structures.dto;

import com.scoutzknifez.transferdatafromsqltosql.database.sql.SQLHelper;
import com.scoutzknifez.transferdatafromsqltosql.database.sql.Table;
import com.scoutzknifez.transferdatafromsqltosql.database.sql.conditions.Conditional;
import com.scoutzknifez.transferdatafromsqltosql.database.sql.insertion.Databasable;
import com.scoutzknifez.transferdatafromsqltosql.utility.MySQLServersInfo;
import com.scoutzknifez.transferdatafromsqltosql.utility.Utils;
import com.scoutzknifez.transferdatafromsqltosql.utility.exceptions.ParseFailureException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
// DTO
public class Product implements Databasable {
    private String id;
    private String productName;
    private double price;
    private String description;
    private String dimensions;
    private double weight;

    // Fields that are not REQUIRED to be filled in when object is created, also not stored with product
    private transient List<ProductImage> productImages;
    private transient List<ProductReview> productReviews;

    public Object[] fieldsToArray() {
        List<Object> fieldList = new ArrayList<>();

        fieldList.add(getId());
        fieldList.add(Utils.capitalize(getProductName()));
        fieldList.add(getPrice());
        fieldList.add(getDescription());
        fieldList.add(getDimensions());
        fieldList.add(getWeight());

        return fieldList.stream().toArray(Object[]::new);
    }

    /**
     * Gets the average rating of this product based off of current reviews
     * @return  The average rating
     */
    public double getAverageRating() {
        double avg = 0;
        for (ProductReview review : getProductReviews())
            avg += review.getRating();

        // Keeps the range from [0 - 5]
        return Math.max(0, Math.min(5, (avg / getProductReviews().size())));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String beforeConcatenation = "{\n\tID:\"" + getId() +
                "\",\n\tproductName:\"" + getProductName() +
                "\",\n\tprice:" + getPrice() +
                ",\n\tdescription:\"" + getDescription() +
                "\",\n\tdimensions:\"" + getDimensions() +
                "\",\n\tweight:" + getWeight();
        sb.append(beforeConcatenation);

        sb.append(",\n\tproductImages:[\n");
        String section;
        for (int i = 0; i < getProductImages().size(); i++) {
            section = "\t\t" + getProductImages().get(i) + ",\n";
            sb.append(section);
        }

        sb.append("\t],\n\tproductReviews:[\n");
        for (int i = 0; i < getProductReviews().size(); i++) {
            section = "\t\t" + getProductReviews().get(i) + ",\n";
            sb.append(section);
        }
        sb.append("\t]\n}");
        return sb.toString();
    }

    public static Product createInstance(ResultSet set) {
        try {
            String id = set.getString("id");
            String productName = set.getString("productName");
            double price = set.getDouble("price");
            String product_description = set.getString("product_description");
            String dimensions = set.getString("dimensions");
            double weight = set.getDouble("weight");

            // Fields that are fetched and set from different databases
            Conditional idCondition = new Conditional("id", id);
            List<ProductImage> images = (List<ProductImage>) SQLHelper.getFromTableWithConditions(MySQLServersInfo.fromServer, Table.PRODUCT_IMAGES, idCondition);
            List<ProductReview> reviews = (List<ProductReview>) SQLHelper.getFromTableWithConditions(MySQLServersInfo.fromServer, Table.PRODUCT_REVIEWS, idCondition);

            return new Product(id, productName, price, product_description, dimensions, weight, images, reviews);
        } catch (Exception e) {
            throw new ParseFailureException(set, Product.class);
        }
    }
}
