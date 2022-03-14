package com.app.grantienda.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.Producto;

@Repository
public interface ProductoRepositorio extends JpaRepository<Producto,String> {

		@Query("SELECT c FROM Producto c where c.id = :id ")
		public Producto buscarProductoPorId(@Param("id") String id);


		@Query(value="SELECT * FROM producto left OUTER JOIN emprendimiento_productos ON producto.id = emprendimiento_productos.productos_id where emprendimiento_productos.emprendimiento_id = :id ",nativeQuery = true )
		public List<Producto> buscarProductosPorId(@Param("id") String id);

		@Modifying
		@Query(value="DELETE  FROM emprendimiento_productos  where emprendimiento_productos.productos_id like :id ",nativeQuery = true)
		public void borrarProductoEnEmprendimiento(@Param("id") String id);

		@Modifying
		@Query(value="DELETE  FROM categoria_producto_productos where categoria_producto_productos.productos_id like :id ",nativeQuery = true)
		public void borrarRelacionesProducto(@Param("id")String id);

		@Query(value="SELECT * FROM producto LEFT OUTER JOIN emprendimiento_productos on producto.id = emprendimiento_productos.productos_id where emprendimiento_productos.emprendimiento_id like :id",nativeQuery = true)
		public List<Producto> buscarProductosPorEmprendimiento(@Param("id") String id);


}
