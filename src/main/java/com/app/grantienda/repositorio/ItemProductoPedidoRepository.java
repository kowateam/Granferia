package com.app.grantienda.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.ItemVariablePedido;

@Repository
public interface ItemProductoPedidoRepository extends JpaRepository<ItemVariablePedido,String>{

}
