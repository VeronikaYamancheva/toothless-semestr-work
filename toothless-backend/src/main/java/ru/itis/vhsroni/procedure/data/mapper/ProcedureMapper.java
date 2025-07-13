package ru.itis.vhsroni.procedure.data.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.itis.vhsroni.procedure.data.dto.response.ProcedureDetailedResponse;
import ru.itis.vhsroni.procedure.data.dto.response.ProcedureShortResponse;
import ru.itis.vhsroni.procedure.data.entity.Procedure;

@Mapper(componentModel = "spring")
public interface ProcedureMapper {

    ProcedureShortResponse toShortResponse(Procedure entity);

    @Mapping(target = "specializationId", source = "specialization.specializationId")
    ProcedureDetailedResponse toDetailedResponse(Procedure entity);



}
