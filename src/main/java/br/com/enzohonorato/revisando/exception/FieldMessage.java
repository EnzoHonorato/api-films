package br.com.enzohonorato.revisando.exception;

import lombok.Data;

@Data
public class FieldMessage {
    private String fieldName;
    private String fieldMessage;

    public FieldMessage(String fieldName, String fieldMessage) {
        this.fieldName = fieldName;
        this.fieldMessage = fieldMessage;
    }
}
