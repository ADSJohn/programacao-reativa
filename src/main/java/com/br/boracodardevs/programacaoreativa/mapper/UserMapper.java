package com.br.boracodardevs.programacaoreativa.mapper;

import com.br.boracodardevs.programacaoreativa.entity.User;
import com.br.boracodardevs.programacaoreativa.model.request.UserRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import static org.mapstruct.NullValueCheckStrategy.ALWAYS;
import static org.mapstruct.NullValuePropertyMappingStrategy.IGNORE;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = IGNORE, nullValueCheckStrategy = ALWAYS)
public interface UserMapper {

	@Mapping(target = "id", ignore = true)
	User toEntity(final UserRequest request);
}