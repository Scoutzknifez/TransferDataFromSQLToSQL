package com.scoutzknifez.transferdatafromsqltosql.structures;


import com.scoutzknifez.transferdatafromsqltosql.utility.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Location {
    double latitude;
    double longitude;

    public Location(String latitude, String longitude) {
        setLatitude(Utils.getDouble(latitude));
        setLongitude(Utils.getDouble(longitude));
    }
}
