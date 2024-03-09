/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controller;

import com.example.entity.Cita;
import com.example.entity.Cliente;
import com.example.entity.Medico;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Manuel
 */
@RestController
public class CitaController {

    @Autowired
    private com.example.service.CitaService servicio;

    @Autowired
    private com.example.service.MedicoService servicioMedico;

    @Autowired
    private com.example.service.ClienteServicio clienteService;

    @GetMapping(value = "/cita")
    public ResponseEntity<Object> get() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Cita> lista = servicio.findAll();
            if (lista.isEmpty()) {
                map.put("message", "Sin citas registradas");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/cita/{id}")
    private ResponseEntity<Object> findById(@PathVariable String id) {
        Map<String, Object> map = new HashMap<>();

        try {
            Cita c = servicio.findById(id);
            if (c == null) {
                map.put("message", "Cita no encontrada");
                return new ResponseEntity<Object>(map, HttpStatus.OK);
            }
            return new ResponseEntity<Object>(c, HttpStatus.OK);
        } catch (Exception w) {
            map.put("message", w.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/cita")
    private ResponseEntity<Object> create(@RequestBody Cita cita) {
        Map<String, Object> map = new HashMap<>();
        List<Medico> medicos = servicioMedico.findAll();
        Medico med = null;
        Cliente cli = null;

        List<Cita> citas = servicio.findAll();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(cita.getHorario().toInstant(), java.time.ZoneId.systemDefault());
        System.out.println("Fechaa: " + localDateTime.toLocalDate());
        List<Cliente> clientes = clienteService.findAll();

        boolean bandDoctor = false;
        boolean bandCliente = false;
        boolean bandConsultorio = false;

        //se valida que el cliente y medico exita
        for (Medico m : medicos) {
            if (m.getId().equals(cita.getDoctor_id())) {
                bandDoctor = true;
                med = m;
            }
        }

        for (Cliente c : clientes) {
            if (c.getId().equals(cita.getCliente_id())) {
                bandCliente = true;
                cli = c;
            }
        }

        // se valida que el doctor no tenga mas de 8 citas en el dia
        int i = 0;

        for (Cita c : citas) {
            LocalDateTime locale = LocalDateTime.ofInstant(c.getHorario().toInstant(), java.time.ZoneId.systemDefault());
            LocalDate localeDate = locale.toLocalDate();

            if (c.getDoctor_id().equals(cita.getDoctor_id())) {
                if (localDateTime.toLocalDate().isEqual(localeDate)) {
                    i++;
                }
            }
        }

        try {
            if (i > 8) {
                map.put("message", "El doctor no tiene citas disponibles");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        } catch (Exception w) {
            map.put("message", w.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // se verificsa que exista tanto el doctor como el cliente
        try {
            if (!bandDoctor) {

                map.put("message", "Dcotor no encontrado");
                return new ResponseEntity<>(map, HttpStatus.OK);

            } else {
                if (!bandCliente) {
                    map.put("message", "Cliente no encontrado");
                    return new ResponseEntity<>(map, HttpStatus.OK);
                }
            }

            try {

                for (Cita c : citas) {

                    LocalDateTime locale = LocalDateTime.ofInstant(c.getHorario().toInstant(), java.time.ZoneId.systemDefault());
                    LocalDate localeDate = locale.toLocalDate();
                    if (cita.getDoctor_id().equals(c.getDoctor_id())) {
                        if (localDateTime.toLocalDate().isEqual(localeDate)) {
                            if (locale.getHour() == localDateTime.toLocalTime().getHour()
                                    || ((localDateTime.toLocalTime().getHour() - 3)) < locale.getHour()) {
                                if (Objects.equals(c.getConsultorio(), cita.getConsultorio())) {
                                    map.put("message", "Ya existe una cita en ese horario con el doctor");
                                    map.put("horario", localDateTime);
                                    map.put("consultorio", cita.getConsultorio());
                                    map.put("doctor", servicioMedico.findById(cita.getDoctor_id()).getNombre() + " "
                                            + servicioMedico.findById(cita.getDoctor_id()).getApellidoPaternoDoctor());
                                    return new ResponseEntity<>(map, HttpStatus.OK);
                                }

                            }
                        }
                    } else {
                        if (Objects.equals(c.getConsultorio(), cita.getConsultorio())) {
                            if (localDateTime.toLocalDate().isEqual(localeDate)) {
                                if (locale.getHour() == localDateTime.toLocalTime().getHour()
                                        || ((localDateTime.toLocalTime().getHour() - 3)) < locale.getHour()) {
                                    if (Objects.equals(c.getConsultorio(), cita.getConsultorio())) {
                                        map.put("message", "Ya existe una cita en ese horario en el consultorio");
                                        map.put("horario", localDateTime);
                                        map.put("consultorio", cita.getConsultorio());
                                        return new ResponseEntity<>(map, HttpStatus.OK);
                                    }

                                }
                            }
                        }
                    }

                }

            } catch (Exception w) {
                map.put("message", w.getMessage());
                return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Cita c = servicio.save(cita);
            map.put("message", "Cita registrada");
            map.put("correo_doctor", med.getCorreo_doctor());
            map.put("telefono_cliente", cli.getTelefono());
            return new ResponseEntity<>(map, HttpStatus.OK);

        } catch (Exception w) {
            map.put("message", w.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/cita/{id}")
    private ResponseEntity<Object> update(@RequestBody Cita cita, @PathVariable String id) {
        Map<String, Object> map = new HashMap<>();
        List<Medico> medicos = servicioMedico.findAll();
        Medico med = null;
        Cliente cli = null;


        if (cita == null) {
            map.put("message", "Cita no encontrada");
            return new ResponseEntity<>(map, HttpStatus.OK);
        }

        List<Cita> citas = servicio.findAll();

        LocalDateTime localDateTime = LocalDateTime.ofInstant(cita.getHorario().toInstant(), java.time.ZoneId.systemDefault());
        List<Cliente> clientes = clienteService.findAll();

        boolean bandDoctor = false;
        boolean bandCliente = false;
        boolean bandConsultorio = false;

        //se valida que el cliente y medico exita
        for (Medico m : medicos) {
            if (m.getId().equals(cita.getDoctor_id())) {
                bandDoctor = true;
                med = m;
            }
        }

        for (Cliente c : clientes) {
            if (c.getId().equals(cita.getCliente_id())) {
                bandCliente = true;
                cli = c;
            }
        }

        // se valida que el doctor no tenga mas de 8 citas en el dia
        int i = 0;

        for (Cita c : citas) {
            LocalDateTime locale = LocalDateTime.ofInstant(c.getHorario().toInstant(), java.time.ZoneId.systemDefault());
            LocalDate localeDate = locale.toLocalDate();

            if (c.getDoctor_id().equals(cita.getDoctor_id())) {
                if (localDateTime.toLocalDate().isEqual(localeDate)) {
                    i++;
                }
            }
        }

        try {
            if (i > 8) {
                map.put("message", "El doctor no tiene citas disponibles");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
        } catch (Exception w) {
            map.put("message", w.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        // se verificsa que exista tanto el doctor como el cliente
        try {
            if (!bandDoctor) {

                map.put("message", "Dcotor no encontrado");
                return new ResponseEntity<>(map, HttpStatus.OK);

            } else {
                if (!bandCliente) {
                    map.put("message", "Cliente no encontrado");
                    return new ResponseEntity<>(map, HttpStatus.OK);
                }
            }

            try {

                for (Cita c : citas) {

                    LocalDateTime locale = LocalDateTime.ofInstant(c.getHorario().toInstant(), java.time.ZoneId.systemDefault());
                    LocalDate localeDate = locale.toLocalDate();
                    if (!(cita.getId().equals(c.getId()))) {
                        if (cita.getDoctor_id().equals(c.getDoctor_id())) {
                            if (localDateTime.toLocalDate().isEqual(localeDate)) {
                                if (locale.getHour() == localDateTime.toLocalTime().getHour()
                                        || ((localDateTime.toLocalTime().getHour() - 3)) < locale.getHour()) {
                                    if (Objects.equals(c.getConsultorio(), cita.getConsultorio())) {
                                        map.put("message", "Ya existe una cita en ese horario con el doctor");
                                        map.put("horario", localDateTime);
                                        map.put("consultorio", cita.getConsultorio());
                                        map.put("doctor", servicioMedico.findById(cita.getDoctor_id()).getNombre() + " "
                                                + servicioMedico.findById(cita.getDoctor_id()).getApellidoPaternoDoctor());
                                        return new ResponseEntity<>(map, HttpStatus.OK);
                                    }

                                }
                            }
                        } else {
                            if (Objects.equals(c.getConsultorio(), cita.getConsultorio())) {
                                if (localDateTime.toLocalDate().isEqual(localeDate)) {
                                    if (locale.getHour() == localDateTime.toLocalTime().getHour()
                                            || ((localDateTime.toLocalTime().getHour() - 3)) < locale.getHour()) {
                                        if (Objects.equals(c.getConsultorio(), cita.getConsultorio())) {
                                            map.put("message", "Ya existe una cita en ese horario en el consultorio");
                                            map.put("horario", localDateTime);
                                            map.put("consultorio", cita.getConsultorio());
                                            return new ResponseEntity<>(map, HttpStatus.OK);
                                        }

                                    }
                                }
                            }
                        }
                    }

                }

            } catch (Exception w) {
                map.put("message", w.getMessage());
                return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
            }

            Cita cit2 = servicio.findById(id);
            cit2.setCliente_id(cita.getCliente_id());
            cit2.setConsultorio(cita.getConsultorio());
            cit2.setDoctor_id(cita.getDoctor_id());
            cit2.setHorario(cita.getHorario());
            cit2.setHorario_fin(cita.getHorario_fin());
            cit2.setPiso(cita.getPiso());
            cit2.setId(cita.getId());
            Cita c = servicio.save(cit2);
            
            map.put("message", "Cita registrada");
            map.put("correo_doctor", med.getCorreo_doctor());
            map.put("telefono_cliente", cli.getTelefono());
            return new ResponseEntity<>(map, HttpStatus.OK);

        } catch (Exception w) {
            map.put("message", w.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    
    @GetMapping(value = "/citas/doctor/{id}")
    private ResponseEntity<Object> findByDoctor(@PathVariable String id) {
        Map<String, Object> map = new HashMap<>();
        List<Cita> citas = servicio.findAll();

        try {
            for(Cita c: citas) {
                if(!(c.getDoctor_id()).equals(id)) {
                    citas.remove(c);
                }
            }
            return new ResponseEntity<Object>(citas, HttpStatus.OK);
        } catch (Exception w) {
            map.put("message", w.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    @GetMapping(value = "/citas/consultorio/{id}")
    private ResponseEntity<Object> findByConsultorio(@PathVariable Integer id) {
        Map<String, Object> map = new HashMap<>();
        List<Cita> citas = servicio.findAll();

        try {
            for(Cita c: citas) {
                if(Objects.equals(c.getConsultorio(), id)) {
                    System.out.println("holaaaa");
                } else {
                    System.out.println("holaaaa nooooooo");
                    citas.remove(c);
                }
            }
            return new ResponseEntity<Object>(citas, HttpStatus.OK);
        } catch (Exception w) {
            map.put("message", w.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
