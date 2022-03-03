package com.app.grantienda.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.ProductoPedido;
@Repository
public interface ProductoPedidoRepository extends JpaRepository<ProductoPedido, String> {

}
