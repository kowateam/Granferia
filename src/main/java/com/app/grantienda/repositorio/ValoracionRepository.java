package com.app.grantienda.repositorio;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.Valoracion;

@Repository
public interface ValoracionRepository extends JpaRepository<Valoracion,String>{

	@Query(value="SELECT AVG(productov) FROM valoracion where producto = :id ",nativeQuery = true )
	public String buscarValoracionDeProducto(@Param("id") String id);
	
	@Query(value="SELECT AVG(tiempov) FROM valoracion where producto = :id ",nativeQuery = true )
	public String buscarValoracionTiempo(@Param("id") String id);
	
	@Query(value="SELECT AVG(serviciov) FROM valoracion  where producto = :id ",nativeQuery = true )
	public String buscarValoracionServicio(@Param("id") String id);
	
	@Query(value="SELECT productos_id FROM emprendimiento_productos where emprendimiento_productos.emprendimiento_id = :id",nativeQuery = true )
	public List<String> buscarTodosProdDeUnEmp(@Param("id") String id);
	
	@Query(value="SELECT id FROM valoracion where user = :idUser and producto = :idProducto",nativeQuery = true )
	public String buscar(@Param("idUser") String idUser,@Param("idProducto") String idProducto);
}
