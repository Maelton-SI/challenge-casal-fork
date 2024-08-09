package maelton.casal.vehicle_rental_api.api.v1.exception.handler;

import io.swagger.v3.oas.annotations.media.Schema;

import lombok.Getter;

@Schema(description = "Represents exception messages")
@Getter
public class ExceptionResponse {

    private final String error;

    protected ExceptionResponse(String error) {
        this.error = error;
    }
}
