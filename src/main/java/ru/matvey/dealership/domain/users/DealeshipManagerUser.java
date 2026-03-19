//package ru.matvey.dealership.domain.users;
//
//import java.util.UUID;
//
//public class DealeshipManagerUser implements User {
//    public DealeshipManagerUser(String username, String passwordHash, String fullName) {
//        this.id = UUID.randomUUID();
//        this.username = username;
//        this.password = passwordHash;
//        this.fullName = fullName;
//    }
//
//    private final UUID id;
//    private final String username;
//    private final String password;
//    private final String fullName;
//
//    @Override
//    public UUID getId() { return id; }
//    @Override
//    public String getUsername() { return username; }
//    @Override
//    public String getPassword() { return password; }
//    @Override
//    public String getFullName() { return fullName; }
//}