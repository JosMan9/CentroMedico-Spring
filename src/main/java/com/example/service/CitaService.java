/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.service;

import com.example.entity.Cita;
import java.util.List;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manuel
 */
@Service
public interface CitaService {
    public List<Cita> findAll();
    
    public Cita save(Cita cita);
    
    public Cita findById(String id);
    
    public void delete(Cita c);
}
