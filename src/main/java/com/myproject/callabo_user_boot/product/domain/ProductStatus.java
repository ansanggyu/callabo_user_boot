package com.myproject.callabo_user_boot.product.domain;

// DTO에서 상품의 STATUS를 문자열로 반환하기 위해 ENUM으로 선언
public enum ProductStatus {
    AVAILABLE("판매중"),
    DISCONTINUED("판매중지"),
    OUT_OF_STOCK("품절");

    private final String description;

    ProductStatus(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public static ProductStatus fromDbValue(String dbValue) {
        switch (dbValue) {
            case "1": return AVAILABLE;
            case "2": return DISCONTINUED;
            case "3": return OUT_OF_STOCK;
            default: throw new IllegalArgumentException("상품상태값 `productStatus`이 이상합니다 " + dbValue);
        }
    }
}
