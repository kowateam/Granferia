package com.app.grantienda.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.ProductoPedido;
import com.app.grantienda.entidades.ProductoPedidoHistorial;
@Repository
public interface ProductoPedidoHistorialRepository extends JpaRepository<ProductoPedidoHistorial, String> {

}
