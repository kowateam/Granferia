package com.app.grantienda.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.HistorialPedidoEmprendimiento;

@Repository
public interface HistorialPedidoEmprendedorRepository extends JpaRepository<HistorialPedidoEmprendimiento, String>{

}
