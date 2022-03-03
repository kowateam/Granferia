package com.app.grantienda.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.Variable;
@Repository
public interface VariableRepository  extends JpaRepository<Variable,String>{

	@Query("SELECT c FROM Variable c where c.id = :id ")
	Variable buscarVariablePorId(@Param("id") String id);
	@Modifying
	@Query(value="DELETE FROM producto_variables where variables_id like :id ",nativeQuery = true)
	public void borrarEnPedidoVariable(@Param("id") String id);
	@Modifying
	@Query(value="DELETE FROM variable_valores where variable_id like :id ",nativeQuery = true)
	public void borrarEnPedidoVariable2(@Param("id") String id);

}
