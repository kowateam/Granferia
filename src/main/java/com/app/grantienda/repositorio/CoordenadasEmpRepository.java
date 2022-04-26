package com.app.grantienda.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.CoordenadasEmp;
import com.app.grantienda.entidades.subCategorias;

@Repository
public interface CoordenadasEmpRepository extends JpaRepository<CoordenadasEmp, String>{

	
	
	
}
