/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.example.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Manuel
 */
@Entity
@Table(name = "citas")
public class Cita implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Id
    private String id;
    
    private String doctor_id;
    private Integer piso;
    
    @Temporal(TemporalType.TIMESTAMP)
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSX")
    private Date horario;
    
    @Temporal(TemporalType.TIMESTAMP)
    private Date horario_fin;
    
    private Integer consultorio;
    private String cliente_id;

   
    public Cita() {
    }

    public Cita(String id) {
        this.id = id;
    }
    
    

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDoctor_id() {
        return doctor_id;
    }

    public void setDoctor_id(String doctor_id) {
        this.doctor_id = doctor_id;
    }

    public Integer getPiso() {
        return piso;
    }

    public void setPiso(Integer piso) {
        this.piso = piso;
    }

    public Date getHorario() {
        return horario;
    }

    public void setHorario(Date horario) {
        this.horario = horario;
    }
    
     public Date getHorario_fin() {
        return horario_fin;
    }

    public void setHorario_fin(Date horario_fin) {
        this.horario_fin = horario_fin;
    }


    public Integer getConsultorio() {
        return consultorio;
    }

    public void setConsultorio(Integer consultorio) {
        this.consultorio = consultorio;
    }

    public String getCliente_id() {
        return cliente_id;
    }

    public void setCliente_id(String cliente_id) {
        this.cliente_id = cliente_id;
    }
    
    
}
