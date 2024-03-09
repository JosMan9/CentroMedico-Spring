/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.serviceImp;

import com.example.entity.Cliente;
import com.example.service.ClienteServicio;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Manuel
 */
@Service
public class ClienteServicioImp implements ClienteServicio {

    @Autowired
    private com.example.repository.ClienteRepository repository;

    @Override
    @Transactional
    public List<Cliente> findAll() {
        return repository.findAll();
    }

    @Override
    @Transactional
    public Cliente save(Cliente cliente) {
        return repository.save(cliente);
    }

    @Override
    @Transactional
    public Cliente findById(String id) {
        return repository.findById(id).orElse(null);
    }

    @Override
    @Transactional
    public void delete(Cliente c) {
        repository.delete(c);
    }

}
