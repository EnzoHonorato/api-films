package br.com.enzohonorato.revisando.requests.genre;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenrePutRequestBody {
    private Long id;
    @NotBlank(message = "The genre name cannot be blank")
    private String name;
}
