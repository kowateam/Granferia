package com.app.grantienda.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.CoordenadasEmp;

@Repository
public interface CoordenadasEmpRepository extends JpaRepository<CoordenadasEmp, String>{

	@Query(value="SELECT * FROM coordenadas_emp",nativeQuery = true)
	public List<CoordenadasEmp> todos();
	
	@Query(value="SELECT * FROM coordenadas_emp WHERE emp=:id",nativeQuery = true)
	public CoordenadasEmp existe(@Param("id") String id);

}
