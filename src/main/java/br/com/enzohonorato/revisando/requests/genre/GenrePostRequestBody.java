package br.com.enzohonorato.revisando.requests.genre;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenrePostRequestBody {
    @NotBlank(message = "The genre name cannot be blank")
    private String name;
}
