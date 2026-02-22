package ru.matvey.dealership.domain.users;

import java.util.UUID;

public interface IUser {
    UUID GetId();
    String Getusername();
    String GetPassword();
    String GetFullName();
}
