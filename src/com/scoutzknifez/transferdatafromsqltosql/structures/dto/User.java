package com.scoutzknifez.transferdatafromsqltosql.structures.dto;

import com.scoutzknifez.transferdatafromsqltosql.database.sql.insertion.Databasable;
import com.scoutzknifez.transferdatafromsqltosql.structures.dto.support.cs3560project.AccountRank;
import com.scoutzknifez.transferdatafromsqltosql.structures.dto.support.cs3560project.Address;
import com.scoutzknifez.transferdatafromsqltosql.structures.dto.support.cs3560project.PhoneNumber;
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
public class User implements Databasable {
    private String ID;
    private String username;
    private String firstName;
    private String lastName;
    private PhoneNumber phoneNumber;
    private Address address;
    private String email;
    private String password;
    private AccountRank rank;

    public Object[] fieldsToArray() {
        List<Object> fieldList = new ArrayList<>();

        fieldList.add(getID());
        fieldList.add(getUsername());
        fieldList.add(getFirstName());
        fieldList.add(getLastName());
        fieldList.add(getPhoneNumber().toString());
        fieldList.add(getAddress().toString());
        fieldList.add(getEmail());
        fieldList.add(getPassword());
        fieldList.add(getRank().getOrdinal());

        return fieldList.toArray();
    }

    @Override
    public String toString() {
        return "{\n\tID:\"" + getID() +
                "\",\n\tusername:\"" + getUsername() +
                "\",\n\tfirstName:\"" + getFirstName() +
                "\",\n\tlastName:\"" + getLastName() +
                "\",\n\tphoneNumber:\"" + getPhoneNumber().toString() +
                "\",\n\taddress:\"" + getAddress().toString() +
                "\",\n\temail:\"" + getEmail() +
                "\",\n\tpassword:\"" + getPassword() +
                "\",\n\trank:" + getRank() +
                "\n}";
    }

    /**
     * createInstance method required for ANY structure stored in the database
     * Creates an instance of this object with a result set
     * @param set   The resultset to create an object with
     * @return      The object created
     */
    public static User createInstance(ResultSet set) {
        try {
            String id = set.getString("id");
            String username = set.getString("username");
            String firstName = set.getString("firstName");
            String lastName = set.getString("lastName");
            PhoneNumber phoneNumber = PhoneNumber.stringToPhoneNumber(set.getString("phoneNumber"));
            Address address = Address.stringToAddress(set.getString("address"));
            String email = set.getString("email");
            String user_password = set.getString("user_password");
            AccountRank user_rank = AccountRank.getRank(set.getInt("user_rank"));

            return new User(id, username, firstName, lastName, phoneNumber, address, email, user_password, user_rank);
        } catch (Exception e) {
            throw new ParseFailureException(set, User.class);
        }
    }
}
