package com.app.grantienda.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import com.app.grantienda.entidades.Historial;

@Repository
public interface HistorialRepository extends JpaRepository<Historial, String> {

	
	@Query("SELECT c FROM Historial c where c.id = :id ")
	public Historial buscarHistorialPorid(@Param("id") String id);
}
