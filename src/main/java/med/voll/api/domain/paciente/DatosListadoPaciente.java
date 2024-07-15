package med.voll.api.domain.paciente;

public record DatosListadoPaciente(Long id, String nombre, String identificacion, String email) {
    
    public DatosListadoPaciente(Paciente paciente) {
        this(paciente.getId(), paciente.getNombre(), paciente.getIdentificacion(), paciente.getEmail());
    }
}
