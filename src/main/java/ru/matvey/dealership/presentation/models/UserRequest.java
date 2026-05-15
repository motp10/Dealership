package ru.matvey.dealership.presentation.models;

import java.util.UUID;

public record UserRequest (
        String name,
        String password,
        String FullName,
        String Role
){}
