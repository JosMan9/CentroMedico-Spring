/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controller;

import com.example.entity.Cliente;
import com.example.entity.Medico;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Manuel
 */
@RestController
public class MedicoController {

    @Autowired
    private com.example.service.MedicoService servicio;

    @GetMapping(value = "/medico")
    public ResponseEntity<Object> get() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Medico> lista = servicio.findAll();
            if (lista.isEmpty()) {
                map.put("mesage", "Sin médicos");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/medico/{id}")
    private ResponseEntity<Object> findById(@PathVariable String id) {
        Map<String, Object> map = new HashMap<>();

        try {
            Medico c = servicio.findById(id);
            if (c == null) {
                map.put("message", "Médico no encontrado");
                return new ResponseEntity<Object>(map, HttpStatus.OK);
            }
            return new ResponseEntity<Object>(c, HttpStatus.OK);
        } catch (Exception w) {
            map.put("message", w.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/medico")
    private ResponseEntity<Object> create(@RequestBody Medico medico) {
        Map<String, Object> map = new HashMap<>();

        try {
            Medico c = servicio.save(medico);
            return new ResponseEntity<>(c, HttpStatus.OK);
        } catch (Exception w) {
            map.put("message", w.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
