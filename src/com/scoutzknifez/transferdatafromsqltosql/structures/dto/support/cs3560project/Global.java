package com.scoutzknifez.transferdatafromsqltosql.structures.dto.support.cs3560project;

import com.scoutzknifez.transferdatafromsqltosql.database.sql.SQLHelper;
import com.scoutzknifez.transferdatafromsqltosql.database.sql.Table;
import com.scoutzknifez.transferdatafromsqltosql.structures.SQLServerDetails;
import com.scoutzknifez.transferdatafromsqltosql.structures.dto.InventorySlot;
import com.scoutzknifez.transferdatafromsqltosql.structures.dto.Product;
import com.scoutzknifez.transferdatafromsqltosql.structures.dto.User;
import com.scoutzknifez.transferdatafromsqltosql.utility.MySQLServersInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Global {
    public static Random random = new Random();

    // List of active inventories
    public static List<Inventory> inventoryList = new ArrayList<>();

    // List of active products
    public static List<Product> productList = new ArrayList<>();

    // List of active users
    public static List<User> userList = new ArrayList<>();

    public static void initialize() {
        //System.out.println("[-----PRODUCTS-----]");
        List<?> list = SQLHelper.getFromTable(MySQLServersInfo.toServer, Table.PRODUCTS);
        list.forEach(product -> productList.add((Product) product));
        // Utils.printList(list);

        //System.out.println("[-----USERS-----]");
        list = SQLHelper.getFromTable(MySQLServersInfo.toServer, Table.USERS);
        list.forEach(user -> userList.add((User) user));
        // Utils.printList(list);
    }

    private static void constructInventories(List<InventorySlot> slots) {
        for (InventorySlot slot : slots) {
            getInventory(slot.getInventory().getId()).getInventory().put(slot.getProduct(), slot.getStock());
        }
    }

    public static Inventory getInventory(String id) {
        for (Inventory inventory : inventoryList) {
            if (inventory.getId().equals(id)) {
                return inventory;
            }
        }

        User user = getUser(id);
        if (user != null) {
            Inventory newInv = new Inventory(user);
            inventoryList.add(newInv);
            return newInv;
        }

        return null;
    }

    public static Product getProduct(String id) {
        for (Product product : productList) {
            if (product.getId().equals(id))
                return product;
        }

        throw new NullPointerException("Could not find product (" + id + ")");
    }

    public static User getUser(String id) {
        for (User user : userList) {
            if (user.getID().equals(id))
                return user;
        }

        return null;
    }
}
