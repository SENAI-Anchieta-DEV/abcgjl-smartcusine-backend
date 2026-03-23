package com.senai.abcgjl_smartcusine_backend.interfaces.documentation;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ErrorResponse", description = "Estrutura padrão de erro da API")
public class ErrorResponseSchema {

    @Schema(example = "400")
    public int status;

    @Schema(example = "Dados inválidos enviados pelo usuário.")
    public String title;

    @Schema(example = "Campo nome é obrigatório")
    public String detail;

    @Schema(example = "/usuarios")
    public String instance;
}