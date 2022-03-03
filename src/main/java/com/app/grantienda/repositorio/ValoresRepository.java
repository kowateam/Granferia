package com.app.grantienda.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.Valores;
@Repository
public interface ValoresRepository extends JpaRepository<Valores,String>{

	@Query("SELECT a FROM Valores a WHERE a.id LIKE :id")
	public Valores buscarPorId(@Param("id") String id);
	
	@Query(value="SELECT * FROM Valores LEFT OUTER JOIN granferia.variable_valores on granferia.variable_valores.valores_id = granferia.valores.id where granferia.variable_valores.variable_id like  :id ",nativeQuery = true)
	public List<Valores> buscarPorVariable(@Param("id")String id);


}
