package com.nc1.testapp.common.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewsResponseDto {
    @NotEmpty
    @JsonProperty("id")
    private long id;

    @NotEmpty
    @JsonProperty("headline")
    private String headline;

    @NotEmpty
    @JsonProperty("description")
    private String description;

    @NotEmpty
    @JsonProperty("publication_time")
    private LocalDateTime publicationTime;
}
