package com.dot.tour_info_service_server.dto.request.place;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ModifyNameRequestDTO {
    @NotBlank(message = "name cannot be blank")
    private String name;
}
