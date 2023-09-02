package br.com.enzohonorato.revisando.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class ValidationExceptionDetails extends ExceptionDetails {
    private final List<FieldMessage> fieldsMessage;
}
