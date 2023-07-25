package fr.pmu.controllers.dto;

import jakarta.validation.constraints.NotBlank;

public class StarterRequestDTO {
    @NotBlank
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
