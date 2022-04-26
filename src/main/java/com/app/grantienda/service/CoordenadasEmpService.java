package com.app.grantienda.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.CoordenadasEmp;
import com.app.grantienda.repositorio.CoordenadasEmpRepository;


@Service
public class CoordenadasEmpService {

	@Autowired
	private CoordenadasEmpRepository coordenadasEmpRepository;
	
	
	public double  calculadistancia(double latitudpunto1, double longitud1,
            double latitudpunto2, double longitud2){

            latitudpunto1=Math.toRadians(latitudpunto1);
            latitudpunto2=Math.toRadians(latitudpunto2);
            longitud1=Math.toRadians(longitud1);
            longitud2=Math.toRadians(longitud2);

            final double radio_tierra = 6371.01; // kilometro

            double distancia = radio_tierra * Math.acos(Math.sin(latitudpunto1) * Math.sin(latitudpunto1)
		                       * Math.cos(latitudpunto1) * Math.cos(latitudpunto1) * 
			                   Math.cos(longitud1 - longitud2));

            return distancia;
}
	
	@Transactional
	public void saveCoordenadas(double longitud, double latitud) {
		CoordenadasEmp coor = new CoordenadasEmp();
		coor.setLatitud(String.valueOf(latitud));
		coor.setLongitud(String.valueOf(longitud));
		coor.setEmp(null);
		coordenadasEmpRepository.save(coor);
	}
	
	
	
	
}
