package med.voll.api.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.direccion.DatosDireccion;
import med.voll.api.domain.paciente.*;
import med.voll.api.domain.paciente.DatosRespuestaPaciente;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("pacientes")
public class PacienteController {
    @Autowired
    private PacienteRepository pacienteRepository;
    
    @PostMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> registrarPaciente(
            @RequestBody @Valid DatosRegistroPaciente datosRegistroPaciente,
            UriComponentsBuilder uriComponentsBuilder) {
        Paciente paciente = pacienteRepository.save(new Paciente(datosRegistroPaciente));
        var uri = uriComponentsBuilder.path("/pacientes/{id}").buildAndExpand(paciente.getId()).toUri();
        return ResponseEntity.created(uri).body(new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(),
                                                                           paciente.getEmail(), paciente.getTelefono(),
                                                                           paciente.getIdentificacion(),
                                                                           new DatosDireccion(
                                                                                   paciente.getDireccion().getCalle(),
                                                                                   paciente.getDireccion()
                                                                                           .getDistrito(),
                                                                                   paciente.getDireccion().getCiudad(),
                                                                                   paciente.getDireccion().getNumero(),
                                                                                   paciente.getDireccion()
                                                                                           .getComplemento())));
    }
    
    @GetMapping
    public ResponseEntity<Page<DatosListadoPaciente>> listarPacientes(
            @PageableDefault(size = 10, sort = {"nombre"}) Pageable paginacion) {
        var page = pacienteRepository.findByActivoTrue(paginacion).map(DatosListadoPaciente::new);
        return ResponseEntity.ok(page);
    }
    
    @PutMapping
    @Transactional
    public ResponseEntity<DatosRespuestaPaciente> actualizarPaciente(
            @RequestBody @Valid DatosActualizarPaciente datosActualizarPaciente) {
        Paciente paciente = pacienteRepository.getReferenceById(datosActualizarPaciente.id());
        paciente.actualizarDatos(datosActualizarPaciente);
        return ResponseEntity.ok(
                new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getEmail(),
                                           paciente.getTelefono(), paciente.getIdentificacion(),
                                           new DatosDireccion(paciente.getDireccion().getCalle(),
                                                              paciente.getDireccion().getDistrito(),
                                                              paciente.getDireccion().getCiudad(),
                                                              paciente.getDireccion().getNumero(),
                                                              paciente.getDireccion().getComplemento())));
    }
    
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarPaciente(@PathVariable Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        paciente.desactivarPaciente();
        return ResponseEntity.noContent().build();
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<DatosRespuestaPaciente> retornarDatospaciente(@PathVariable Long id) {
        Paciente paciente = pacienteRepository.getReferenceById(id);
        var datosPaciente = new DatosRespuestaPaciente(paciente.getId(), paciente.getNombre(), paciente.getEmail(),
                                                       paciente.getTelefono(), paciente.getIdentificacion(),
                                                       new DatosDireccion(paciente.getDireccion().getCalle(),
                                                                          paciente.getDireccion().getDistrito(),
                                                                          paciente.getDireccion().getCiudad(),
                                                                          paciente.getDireccion().getNumero(),
                                                                          paciente.getDireccion().getComplemento()));
        return ResponseEntity.ok(datosPaciente);
    }
    
}
