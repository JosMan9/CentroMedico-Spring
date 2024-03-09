/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.controller;

import com.example.entity.Cliente;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Manuel
 */
@Controller
@CrossOrigin(origins = "http://localhost:4200")
public class ClienteController {

    @Autowired
    private com.example.service.ClienteServicio servicio;

    
    @GetMapping(value="/cliente")
    public ResponseEntity<Object> get() {
        Map<String, Object> map = new HashMap<>();
        try {
            List<Cliente> lista = servicio.findAll();
            if(lista.isEmpty()) {
                map.put("mesage", "Sin clientes");
                return new ResponseEntity<>(map, HttpStatus.OK);
            }
            return new ResponseEntity<>(lista, HttpStatus.OK);
        } catch(Exception e) {
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
     
    
    @RequestMapping("/")
    public String index(Model model) {
        model.addAttribute("lista", servicio.findAll());
        return "home";
    }


    @GetMapping(value = "/cliente/{id}")
    private ResponseEntity<Object> findById(@PathVariable String id) {
        Map<String, Object> map = new HashMap<>();

        try {
            Cliente c = servicio.findById(id);
            if(c == null) {
                map.put("message", "Cliente no encontrado");
                return new ResponseEntity<Object>(map, HttpStatus.OK);
            }
            return new ResponseEntity<Object>(c, HttpStatus.OK);
        } catch (Exception w) {
            map.put("message", w.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/cliente")
    private ResponseEntity<Object> create(@RequestBody Cliente cliente) {
        Map<String, Object> map = new HashMap<>();

        try {
            Cliente c = servicio.save(cliente);
            return new ResponseEntity<>(c, HttpStatus.OK);
        } catch (Exception w) {
            map.put("message", w.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/cliente/{id}")
    private ResponseEntity<Object> update(@RequestBody Cliente cliente, @PathVariable String id) {
        Map<String, Object> map = new HashMap<>();

        try {
            Cliente c = servicio.findById(id);

            c.setNombre(cliente.getNombre());
            c.setApellidos(cliente.getApellidos());
            c.setCorreo(cliente.getCorreo());
            c.setId(cliente.getId());
            c.setEdad(cliente.getEdad());
            c.setTelefono(cliente.getTelefono());

            Cliente customer = servicio.save(c);

            return new ResponseEntity<>(customer, HttpStatus.OK);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/cliente/{id}")
    public ResponseEntity<Object> delete(@PathVariable String id) {
        Map<String, Object> map = new HashMap<>();

        try {
            Cliente c = servicio.findById(id);

            servicio.delete(c);
            map.put("deleted", true);
            map.put("id", c.getId());
            return new ResponseEntity<>(map, HttpStatus.OK);
        } catch (Exception e) {
            map.put("message", e.getMessage());
            return new ResponseEntity<>(map, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
