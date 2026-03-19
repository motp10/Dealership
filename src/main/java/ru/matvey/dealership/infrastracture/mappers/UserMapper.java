package ru.matvey.dealership.infrastracture.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.matvey.dealership.domain.users.*;
import ru.matvey.dealership.infrastracture.jpa.UserEntity;
import ru.matvey.dealership.infrastracture.mappers.IdEntityMapper.IdEntityMapper;

@Mapper(componentModel = "spring", uses = {IdEntityMapper.class})
public interface UserMapper {
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "removed", ignore = true)
    UserEntity toEntity(User domain);

    User toDomain(UserEntity entity);
}