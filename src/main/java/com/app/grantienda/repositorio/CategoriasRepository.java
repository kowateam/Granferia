package com.app.grantienda.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.subCategorias;




@Repository
public interface CategoriasRepository extends JpaRepository<subCategorias, String>{
	
    @Query(value="SELECT * FROM sub_categorias  WHERE sub_categorias.categoria LIKE :categoria",nativeQuery = true)
	public List<subCategorias> buscarUsuarioPorsubCategorias(@Param("categoria") String categoria);
	
	

}
