package com.app.grantienda.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.CantidadVistas;

@Repository
public interface CantidadVistasRepository extends JpaRepository<CantidadVistas, String> {

	@Query("SELECT c FROM CantidadVistas c where c.principal = true ")
	public CantidadVistas buscarPrincipal();

}
