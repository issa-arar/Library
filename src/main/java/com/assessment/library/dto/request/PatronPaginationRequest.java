package com.assessment.library.dto.request;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.Min;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class PatronPaginationRequest {
    
    @Nullable()
    @Min(value = 0, message = "Page number must be greater than or equal to 0")
    private int pageNumber;
    
    @Nullable()
    @Min(value = 1, message = "size must be greater than or equal to 1")
    private int size;

    private String name;

}
