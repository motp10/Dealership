package ru.matvey.dealership.domain.users;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ClientUser implements IUser {
    private final UUID id;
    private final String username;
    private final String password;
    private final String fullName;

    public ClientUser(String username, String password, String fullName) {
        id = UUID.randomUUID();
        this.username = username;
        this.password = password;
        this.fullName = fullName;
    }

    @Override
    public UUID GetId() {
        return null;
    }

    @Override
    public String Getusername() {
        return username;
    }

    @Override
    public String GetPassword() {
        return password;
    }

    @Override
    public String GetFullName() {
        return fullName;
    }
}
