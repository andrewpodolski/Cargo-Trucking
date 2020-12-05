package com.itechart.cargotrucking.core.message.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class MessageUpdateDto {
    @NotBlank
    private Long id;

    @NotBlank(message = "Message body can't be blank")
    private String message;

    @NotBlank(message = "Message author can't be blank")
    private String author;

    @NotBlank(message = "Message date can;t be blank")
    private LocalDateTime date;

    private Boolean isEdited;
}
