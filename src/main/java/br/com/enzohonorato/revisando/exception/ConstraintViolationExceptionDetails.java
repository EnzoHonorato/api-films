package br.com.enzohonorato.revisando.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
public class ConstraintViolationExceptionDetails extends ExceptionDetails {
    private final List<FieldMessage> fieldsMessage;
}
