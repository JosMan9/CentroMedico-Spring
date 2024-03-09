/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.serviceImp;

import com.example.entity.Cita;
import com.example.service.CitaService;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manuel
 */
@Service
public class CitaServicioImp implements CitaService{
    
    @Autowired
    private com.example.repository.CitaRepository repository;

    @Override
    public List<Cita> findAll() {
        return repository.findAll();
    }

    @Override
    public Cita save(Cita cita) {
        return repository.save(cita);
    }

    @Override
    @Transactional
    public Cita findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void delete(Cita c) {
        repository.delete(c);
    }
    
}
