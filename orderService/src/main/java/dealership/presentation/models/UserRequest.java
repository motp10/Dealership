package dealership.presentation.models;

public record UserRequest (
        String name,
        String password,
        String FullName,
        String Role
){}
