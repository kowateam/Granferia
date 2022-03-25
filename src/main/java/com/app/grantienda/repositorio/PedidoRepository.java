package com.app.grantienda.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido,String>{

	@Query("SELECT c FROM Pedido c where c.user.id = :id ")
	public List<Pedido> buscarPorUser(@Param("id") String id);
	
	@Query("SELECT c FROM Pedido c where c.emprendimiento.id = :idemprendimiento ")
	public List<Pedido> buscarPorEmprendimiento(@Param("idemprendimiento")String idemprendimiento);
	
	@Query(value ="SELECT * FROM granferia.pedido where user_id like :id order by granferia.pedido.alta limit 1"  ,nativeQuery= true)
	public Pedido buscarPorUserUltimoPedido(@Param("id")String id);

}
