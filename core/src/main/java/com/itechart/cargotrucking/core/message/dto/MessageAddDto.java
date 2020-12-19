package com.itechart.cargotrucking.core.message.dto;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class MessageAddDto {
    @NotBlank(message = "Message can't be blank")
    @Length(max = 1000, message = "Message length cannot be more than 1000 characters")
    private String message;

    @NotBlank
    private String author;

    @NotBlank
    private Boolean isEdited;
}
