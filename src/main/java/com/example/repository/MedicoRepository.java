/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.repository;

import com.example.entity.Medico;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 *
 * @author Manuel
 */
public interface MedicoRepository extends JpaRepository<Medico, String>{
    
}
