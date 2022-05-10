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
	
	@Query(value ="SELECT pedido_id from pedido_productos where productos_id = :id"  ,nativeQuery= true)
	public String buscarPorIdPedido(@Param("id")String id);
	
	@Query(value ="SELECT * from pedido where id = :id"  ,nativeQuery= true)
	public Pedido buscarPedido(@Param("id")String id);
	
	
	@Query(value ="SELECT user_id FROM pedido WHERE id = :id "  ,nativeQuery= true)
	public String buscarUserPedido(@Param("id")String id);

	
	@Query(value ="SELECT id_producto FROM pedido WHERE id = :id "  ,nativeQuery= true)
	public String buscarProductoPedido(@Param("id")String id);
	
	@Query(value ="SELECT id_producto FROM pedido WHERE user_id = :iduser  and id=:id"  ,nativeQuery= true)
	public String buscarProductoPedidoPorUsuario(@Param("iduser")String iduser, @Param("id")String id);
	
	@Query(value ="SELECT count(estado) FROM pedido WHERE estado='pedidoEnviado' AND emprendimiento_id=:id",nativeQuery= true)
	public String cantidadDePedidosPendientes(@Param("id")String id);
}
