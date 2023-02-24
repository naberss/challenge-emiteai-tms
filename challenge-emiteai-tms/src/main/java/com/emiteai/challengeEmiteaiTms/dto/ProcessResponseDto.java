package com.emiteai.challengeEmiteaiTms.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.StringJoiner;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ProcessResponseDto {

    private boolean success;
    private String errorCode;
    private String message;

    public static ProcessResponseDto buildUndetailedResponse(boolean sucess, String message, String errorCode) {
        return ProcessResponseDto.builder()
                .success(sucess)
                .message(message/*"Nenhum dos itens possui cadastro, solicitação ignorada"*/)
                .errorCode(errorCode)
                .build();
    }

    public static ProcessResponseDto buildDetailedResponse(boolean sucess, String message, String errorCode, List<Long> ids) {

        StringJoiner joiner = new StringJoiner(",");
        for (Long id : ids) {
            joiner.add(id.toString());
        }

        return ProcessResponseDto.builder()
                .success(sucess)
                .message(new StringBuilder()
                            .append(message)
                            .append(" -> Ids dos produtos nao registrados nesta requisição: ")
                            .append(joiner.toString())
                            .toString())
                .errorCode(errorCode)
                .build();
    }
}
