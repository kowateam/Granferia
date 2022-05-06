package com.app.grantienda.repositorio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.Seguidores;

@Repository
public interface SeguidoresRepository extends JpaRepository<Seguidores, String>{
	
	@Query(value="SELECT seguidor FROM seguidores WHERE emprendimiento_id = :idemprendimiento AND user_id=:iduser",nativeQuery = true )
	public String existeSeguidor(@Param("idemprendimiento") String idemprendimiento,@Param("iduser") String iduser);
	
	@Query(value="SELECT * FROM seguidores WHERE user_id = :id AND emprendimiento_id = :idemprendimiento",nativeQuery = true )
	public Seguidores modificarSeguidor(@Param("id") String id,@Param("idemprendimiento") String idemprendimsiento);
	
	
	@Query(value="SELECT count(seguidor) FROM seguidores WHERE emprendimiento_id = :id AND seguidor=true",nativeQuery = true )
	public String contadorDeSeguidores(@Param("id") String id);
	
}
