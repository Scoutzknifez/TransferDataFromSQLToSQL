package com.scoutzknifez.transferdatafromsqltosql.structures.dto;

import com.scoutzknifez.transferdatafromsqltosql.database.sql.insertion.Databasable;
import com.scoutzknifez.transferdatafromsqltosql.structures.dto.support.cs3560project.Global;
import com.scoutzknifez.transferdatafromsqltosql.structures.dto.support.cs3560project.Inventory;
import com.scoutzknifez.transferdatafromsqltosql.utility.exceptions.ParseFailureException;
import lombok.Getter;
import lombok.Setter;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
// DTO
public class InventorySlot implements Databasable {
    private Inventory inventory;
    private Product product;
    private int stock;

    public InventorySlot(Inventory inventory, Product product, int stock) {
        setInventory(inventory);
        setProduct(product);
        setStock(stock);

        getInventory().getInventory().put(getProduct(), getStock());
    }

    @Override
    public Object[] fieldsToArray() {
        List<Object> fieldList = new ArrayList<>();

        fieldList.add(getInventory().getId());
        fieldList.add(getProduct().getId());
        fieldList.add(getStock());

        return fieldList.stream().toArray(Object[]::new);
    }

    @Override
    public String toString() {
        return "{\n\tinventoryID:\"" + getInventory().getId() +
                "\",\n\tproductID:\"" + getProduct().getId() +
                "\",\n\tstock:" + getStock() + "\n}";
    }

    public static InventorySlot createInstance(ResultSet set) {
        try {
            Inventory inventory = Global.getInventory(set.getString("id"));
            Product product = Global.getProduct(set.getString("product_id"));
            int stock = set.getInt("stock");

            return new InventorySlot(inventory, product, stock);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParseFailureException(set, InventorySlot.class);
        }
    }
}
