package ru.matvey.dealership.domain.users;

public enum UserRole {
    CLIENT("CLIENT"),
    DEALERSHIPMANAGER("DEALERSHIPMANAGER"),
    SYSTEMADMIN("SYSTEMADMIN"),
    WAREHOUSEADMIN("WAREHOUSEADMIN");
    private final String value;

    UserRole(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public static UserRole fromString(String text) {
        for (UserRole role : UserRole.values()) {
            if (role.value.equalsIgnoreCase(text)) {
                return role;
            }
        }
        throw new IllegalArgumentException("unknown role" + text);
    }
}