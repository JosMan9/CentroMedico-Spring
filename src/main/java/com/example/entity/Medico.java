/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author Manuel
 */
@Entity
@Table(name = "medico")
public class Medico implements Serializable {
    
    private static final long serialVersionUID = 1L;

    @Id
    private String id;
    
    private String nombre;
    private String apellido_paterno_doctor;
    private String apellido_materno_doctor;
    private String correo_doctor;
    private String telefono_doctor;
    private String especialidad;
    

    public Medico() {
    }

    public Medico(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo_doctor() {
        return correo_doctor;
    }

    public void setCorreo_doctor(String correo_doctor) {
        this.correo_doctor = correo_doctor;
    }

    public String getTelefono_doctor() {
        return telefono_doctor;
    }

    public void setTelefono_doctor(String telefono_doctor) {
        this.telefono_doctor = telefono_doctor;
    }

    public String getEspecialidad() {
        return especialidad;
    }

    public void setEspecialidad(String especialidad) {
        this.especialidad = especialidad;
    }

    public String getApellidoPaternoDoctor() {
        return apellido_paterno_doctor;
    }

    public void setApellidoPaternoDoctor(String apellidoPaternoDoctor) {
        this.apellido_paterno_doctor = apellidoPaternoDoctor;
    }

    public String getApellidoMaternoDoctor() {
        return apellido_materno_doctor;
    }

    public void setApellidoMaternoDoctor(String apellidoMaternoDoctor) {
        this.apellido_materno_doctor = apellidoMaternoDoctor;
    }
    
    
    
}
