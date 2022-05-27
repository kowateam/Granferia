package com.app.grantienda.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.CategoriaProducto;

@Repository
public interface CategoriaProductoRepository extends JpaRepository<CategoriaProducto,String> {

	
	@Query("SELECT c FROM CategoriaProducto c where c.id = :id ")
	public List<CategoriaProducto> buscarCategoriaPorProducto(@Param("id") String id);

	@Query(value="SELECT * FROM categoria_producto  where categoria_producto.emprendimiento_id = :id ",nativeQuery = true)
	public List<CategoriaProducto> buscarCategoriasSegunEmprendimiento(@Param("id")String id);

	@Modifying
	@Query(value="DELETE  FROM producto_categorias where producto.categoria_id = :id ",nativeQuery = true)
	public void borrarRelaciones(@Param("id")String id);
	
	@Modifying
	@Query(value="DELETE  FROM categoria_producto  where categoria_producto.emprendimiento_id = :id ",nativeQuery = true)
	public void borrarRelacionesEmprendimiento(@Param("id")String id);
	
	
	@Modifying
	@Query(value="delete from categoria_producto_productos where categoria_producto_id = :id ",nativeQuery = true)
	public void borrarRelacionesCategoriaProductoProducto(@Param("id")String id);
	@Modifying
	@Query(value="DELETE  FROM producto where producto.categoria_id = :id ",nativeQuery = true)
	public void borrarRelacionesProducto(@Param("id")String id);

	@Query(value ="SELECT * FROM granferia.categoria_producto where  primero = true and granferia.categoria_producto.emprendimiento_id like :idEmprendimiento ", nativeQuery = true)
	public CategoriaProducto buscarPrincipal(@Param("idEmprendimiento")String idEmprendimiento);

	@Query(value ="SELECT * FROM granferia.categoria_producto LEFT OUTER JOIN categoria_producto on categoria_producto.id = granferia.categoria_producto_productos.categoria_producto_id where granferia.categoria_producto_productos.productos_id like :idProducto" , nativeQuery = true)
	public CategoriaProducto buscarCategoriaDelProducto(@Param("idProducto") String idProducto);


	@Modifying
	@Query(value="DELETE  FROM categoria_producto_productos  where categoria_producto_productos.productos_id = :id",nativeQuery = true) 
	public void borrarRelacionesCategoriaProducto(@Param("id")String id);

	

}
