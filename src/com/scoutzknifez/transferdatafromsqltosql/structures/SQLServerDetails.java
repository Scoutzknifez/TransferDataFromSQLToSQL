package com.scoutzknifez.transferdatafromsqltosql.structures;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SQLServerDetails {
    private String username;
    private String password;
    private String databaseUrl;
}
