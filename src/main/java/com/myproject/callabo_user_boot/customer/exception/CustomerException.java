package com.myproject.callabo_user_boot.customer.exception;

public enum CustomerException {
    BAD_AUTH(400, "ID/PW incorrect"),
    TOKEN_NOT_ENOUGH(401, "More Tokens required"),
    ACCESSTOKEN_TOO_SHORT(401, "Access Token too short"),
    REQUIRE_SIGH_IN(401, "Require sign in");

    private CustomerTaskException exception;

    CustomerException(int status, String msg) {
        exception = new CustomerTaskException(status, msg);
    }

    public CustomerTaskException get() {
        return exception;
    }
}
