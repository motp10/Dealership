package ru.matvey.dealership.domain.users;

import java.util.UUID;

public class DealeshipManagerUser implements IUser {
    private final UUID id;
    private final String username;
    private final String passwordHash;
    private final String fullName;

    public DealeshipManagerUser(String username, String passwordHash, String fullName) {
        this.id = UUID.randomUUID();
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
