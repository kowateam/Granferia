package com.app.grantienda.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.Foto;



@Repository
public interface FotoRepositorio extends JpaRepository<Foto,String> {

	@Modifying
	@Query(value="DELETE FROM producto_foto  where producto_foto.foto_id like :id ",nativeQuery = true)
	void borrarFotosEnProducto(@Param("id")String id);


	
	
}
