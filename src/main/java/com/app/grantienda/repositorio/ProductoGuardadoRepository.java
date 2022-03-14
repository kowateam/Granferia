package com.app.grantienda.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.ProductoGuardado;

@Repository
public interface ProductoGuardadoRepository extends JpaRepository<ProductoGuardado,String>{


	@Query("SELECT a FROM ProductoGuardado a WHERE a.idProducto LIKE :idProducto")
	public ProductoGuardado buscarProductod(@Param("idProducto") String idProducto);
	
	@Query(value="SELECT * FROM producto_guardado  left outer JOIN user_productos_guardados on user_productos_guardados.productos_guardados_id = user_productos_guardados.productos_guardados_id where user_productos_guardados.user_id like :idusuario  group by producto_guardado.id_producto",nativeQuery = true)
	public List<ProductoGuardado> buscarProductoPorUsuario(@Param ("idusuario")String id);
	@Modifying
	@Query(value="DELETE  FROM user_productos_guardados where user_productos_guardados.productos_guardados_id like :id ",nativeQuery = true)
	public void borrarRelacionesProductoGuardados(@Param("id")String id);
}
