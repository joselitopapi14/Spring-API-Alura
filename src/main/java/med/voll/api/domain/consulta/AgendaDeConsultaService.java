package med.voll.api.domain.consulta;

import med.voll.api.domain.medico.Medico;
import med.voll.api.domain.medico.MedicoRepository;
import med.voll.api.domain.paciente.Paciente;
import med.voll.api.domain.paciente.PacienteRepository;
import med.voll.api.infra.errores.ValidacionDeIntegracion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaDeConsultaService {
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @Autowired
    private MedicoRepository medicoRepository;
    
    @Autowired
    private ConsultaRepository consultaRepository;
    
    public void agendaDeConsulta(DatosAgendarConsulta datos) {
        if (pacienteRepository.findById(datos.idPaciente()).isPresent()){
            throw new ValidacionDeIntegracion("EL id del paciente no fue encontrado");
        }
        
        if (datos.idMedico() != null & medicoRepository.existsById(datos.idMedico())){
            throw new ValidacionDeIntegracion("EL id del medico no fue encontrado");
        }
        var medico = seleccionarMedico(datos);
        var paciente = pacienteRepository.findById(datos.idPaciente()).get();
        var consulta = new Consulta(null,medico, paciente, datos.fecha());
        consultaRepository.save(consulta);
    }
    
    private Medico seleccionarMedico(DatosAgendarConsulta datos) {
        if (datos.idMedico() != null){
            return medicoRepository.getReferenceById(datos.idMedico());
        }
        if (datos.especialidad() == null){
            throw new ValidacionDeIntegracion("Debe especificar una especialidad");
        }
        
        return medicoRepository.seleccionarMedicoConEspecialidadEnFecha(datos.especialidad(), datos.fecha());
    }
}
