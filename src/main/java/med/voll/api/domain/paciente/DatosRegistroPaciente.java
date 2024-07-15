package med.voll.api.domain.paciente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import med.voll.api.domain.direccion.DatosDireccion;

public record DatosRegistroPaciente(@NotBlank String nombre, @NotBlank String identificacion, @NotBlank String email,
                                    @NotBlank String telefono, @NotNull DatosDireccion direccion) {
}
