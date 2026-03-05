package ru.matvey.dealership.domain.users;

import java.util.UUID;

public class WarehouseAdminUser implements IUser {
    private final UUID id;
    private final String username;
    private final String passwordHash;
    private final String fullName;

    public WarehouseAdminUser(UUID id, String username, String passwordHash, String fullName) {
        this.id = id;
        this.username = username;
        this.passwordHash = passwordHash;
        this.fullName = fullName;
    }

    @Override
    public UUID GetId() {
        return null;
    }

    @Override
    public String Getusername() {
        return "";
    }

    @Override
    public String GetPassword() {
        return "";
    }

    @Override
    public String GetFullName() {
        return "";
    }
}
