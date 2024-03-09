/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.service;

import com.example.entity.Cliente;
import java.util.List;

/**
 *
 * @author Manuel
 */
public interface ClienteServicio {
    
    public List<Cliente> findAll();
    
    public Cliente save(Cliente cliente);
    
    public Cliente findById(String id);
    
    public void delete(Cliente c);
}
