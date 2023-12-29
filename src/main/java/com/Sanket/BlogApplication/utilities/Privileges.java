package com.Sanket.BlogApplication.utilities;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Privileges {
    RESET_ANY_USER_PASSWORD(1L,"RESET_ANY_USER_PASSWORD"),
    ACCESS_ADMIN_PANEL(2L,"ACCESS_ADMIN_PANEL");
    private Long privilageId;
    private String PrivilageName;

}
