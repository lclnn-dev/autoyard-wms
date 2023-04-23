package com.autoyard.project.api.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class CellOfYardResponse {
    private UUID id;
    private String name;
    private boolean isFolder;
    private UUID parentId;
}
