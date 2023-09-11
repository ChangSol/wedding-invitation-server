package org.changsol.apps.members.dtos;

import org.changsol.members.domains.Member;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface PrincipalDtoMapper {

	PrincipalDtoMapper INSTANCE = Mappers.getMapper(PrincipalDtoMapper.class);

	PrincipalDto.Response toResponse(Member member);
}
