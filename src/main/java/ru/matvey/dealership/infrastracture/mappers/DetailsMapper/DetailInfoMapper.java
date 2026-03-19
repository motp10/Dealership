package ru.matvey.dealership.infrastracture.mappers.DetailsMapper;

import org.mapstruct.Mapper;
import ru.matvey.dealership.domain.details.Detail;

@Mapper(componentModel = "spring")
public interface DetailInfoMapper {
    default String detailToString(Detail detail) {
        return detail.Info().Value();
    }
}
