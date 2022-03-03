package com.app.grantienda.repositorio;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.app.grantienda.entidades.User;




@Repository
public interface UserRepository extends JpaRepository<User, String> {
	
	@Query("SELECT a FROM User a WHERE a.role LIKE 'SUPERADMIN'")
	public List<User> buscadAdmin();

	@Query("SELECT c FROM User c where c.mail LIKE :mail ")
	public User buscarUserPorMail(@Param("mail") String mail);
	
	@Query("SELECT c FROM User c where c.regNombre LIKE :regNombre ")
	public User buscarUserPorNombre(@Param("regNombre") String regNombre);
	
	@Query("SELECT c FROM User c where c.id = :id ")
	public User buscarUserPorId(@Param("id") String id);

	@Query("SELECT a FROM User a WHERE a.role LIKE 'USUARIO'")
	public List<User> listarTodos();
	

	@Query(value ="SELECT * FROM user c where c.codigo_de_verificacion LIKE :codigoDeVerificacion" ,nativeQuery= true)
	public User findByCodigoVerificacion(@Param("codigoDeVerificacion") String codigoDeVerificacion);
	
	@Query(value ="SELECT * FROM user c where c.codigo_de_contrasena LIKE :codigoDeContrasena" ,nativeQuery= true)
	public User findByCodigoPass(@Param("codigoDeContrasena") String codigoDeContrasena);
}