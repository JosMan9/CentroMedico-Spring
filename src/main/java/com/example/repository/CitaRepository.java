/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.repository;

import com.example.entity.Cita;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Manuel
 */
public interface CitaRepository extends JpaRepository<Cita, String>{
    
}
