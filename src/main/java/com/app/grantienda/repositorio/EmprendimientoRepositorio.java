package com.app.grantienda.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.Emprendimiento;



@Repository
public interface EmprendimientoRepositorio extends JpaRepository<Emprendimiento,String> {
	
	@Query("SELECT c FROM Emprendimiento c where c.id = :id ")
	public Emprendimiento buscarEmpPorMail(@Param("id") String id);
	
	@Query("SELECT a FROM Emprendimiento a WHERE a.id LIKE 'id'")
	public List<Emprendimiento> listarTodos();
	
	@Query("SELECT c FROM Emprendimiento c where c.regEmpNombre = :regEmpNombre ")
	public Emprendimiento buscarEmpPorNombre(@Param("regEmpNombre") String regEmpNombre);
	
	@Query(value="SELECT * FROM emprendimiento  WHERE emprendimiento.user_id LIKE :user_id",nativeQuery = true)
    public List<Emprendimiento> buscarEmprendimientoPorIdUsuario(@Param("user_id") String id);


	@Query("SELECT c FROM Emprendimiento c where c.regEmpDir LIKE :buscar")
	public List<Emprendimiento> buscarPorNombrePagina(@Param("buscar") String buscar);
	
	@Query("SELECT c FROM Emprendimiento c where c.estadoEmp LIKE :estadoEmp")
	public List<Emprendimiento>buscarEmprendimientoPorEstado(@Param("estadoEmp") String estadoEmp);
	
	@Query(value="SELECT * FROM emprendimiento LEFT OUTER JOIN emprendimiento_productos on emprendimiento.id = emprendimiento_productos.emprendimiento_id where emprendimiento_productos.productos_id like :idProducto ",nativeQuery = true)
	public List<Emprendimiento> buscarPorProducto(@Param("idProducto")String idProducto);

	@Query("SELECT c FROM Emprendimiento c where c.regEmpDir LIKE :direccionweb")
	public Emprendimiento buscarUnoPorNombrePagina(@Param("direccionweb") String direccionweb);

	
}
