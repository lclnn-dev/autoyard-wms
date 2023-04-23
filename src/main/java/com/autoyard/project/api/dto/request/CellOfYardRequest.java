package com.autoyard.project.api.dto.request;

import com.autoyard.project.validation.constraint.FolderParentConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;

import java.util.UUID;

@Getter
@FolderParentConstraint
public class CellOfYardRequest {

    @NotBlank
    @Size(max = 25)
    private String name;

    @NotNull
    private boolean folder;

    private UUID parentId;
}
