package com.example.srt.dto.storage.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(description = "srt 요청 DTO")
public class ResponseStorageListDTO {

    @Schema(description = "보관함 리스트")
    private List<ResponseStorageDTO> storageList;
}
