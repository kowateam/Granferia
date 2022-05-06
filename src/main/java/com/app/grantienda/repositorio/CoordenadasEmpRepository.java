package com.app.grantienda.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.CoordenadasEmp;

@Repository
public interface CoordenadasEmpRepository extends JpaRepository<CoordenadasEmp, String>{

	@Query(value="SELECT * FROM coordenadas_emp",nativeQuery = true)
	public List<CoordenadasEmp> todos();

}
