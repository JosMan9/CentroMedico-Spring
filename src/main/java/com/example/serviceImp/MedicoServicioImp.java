/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.serviceImp;

import com.example.entity.Medico;
import com.example.service.MedicoService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manuel
 */
@Service
public class MedicoServicioImp implements MedicoService{
    
    @Autowired
    private com.example.repository.MedicoRepository repository;

    @Override
    public List<Medico> findAll() {
        return repository.findAll();
    }

    @Override
    public Medico save(Medico medico) {
        return repository.save(medico);
    }

    @Override
    public Medico findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    public void delete(Medico med) {
        repository.delete(med);
    }
    
}
