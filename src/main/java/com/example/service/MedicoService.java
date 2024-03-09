/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.service;

import com.example.entity.Medico;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manuel
 */
@Service
public interface MedicoService {
    public List<Medico> findAll();
    
    public Medico save(Medico medico);
    
    public Medico findById(String id);
    
    public void delete(Medico med);
}
