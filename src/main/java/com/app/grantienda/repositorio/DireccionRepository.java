package com.app.grantienda.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.Direccion;


@Repository
public interface DireccionRepository extends JpaRepository<Direccion, String> {

	
	@Query("SELECT c FROM Direccion c where c.id = :id ")
	public Direccion buscarDireccionPorId(@Param("id") String id);
	
}
