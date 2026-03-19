package ru.matvey.dealership.infrastracture.mappers.IdEntityMapper;

import org.mapstruct.Mapper;
import ru.matvey.dealership.infrastracture.jpa.CarEntity;
import ru.matvey.dealership.infrastracture.jpa.DetailEntity;
import ru.matvey.dealership.infrastracture.jpa.UserEntity;

import java.util.UUID;

@Mapper(componentModel = "spring")
public interface IdEntityMapper {
    default CarEntity IdToEntity(UUID id) {
        if (id == null) return null;
        CarEntity entity = new CarEntity();
        entity.setId(id);
        return entity;
    }

    default UUID EntityToId(CarEntity entity) {
        return entity.getId();
    }

    default UserEntity UserIdToEntity(UUID id) {
        if (id == null) return null;
        UserEntity entity = new UserEntity();
        entity.setId(id);
        return entity;
    }

    default UUID UserEntityToId(UserEntity entity) {
        return entity.getId();
    }

    default DetailEntity DetailIdToEntity(UUID id) {
        if (id == null) return null;
        DetailEntity entity = new DetailEntity();
        entity.setId(id);
        return entity;
    }

    default UUID DetailEntityToId(DetailEntity entity) {
        return entity.getId();
    }
}
