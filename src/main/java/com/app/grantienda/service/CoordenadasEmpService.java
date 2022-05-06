package com.app.grantienda.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.grantienda.entidades.CoordenadasEmp;
import com.app.grantienda.entidades.Producto;
import com.app.grantienda.repositorio.CoordenadasEmpRepository;


@Service
public class CoordenadasEmpService {

	@Autowired
	private CoordenadasEmpRepository coordenadasEmpRepository;
	
	
	public void calculadistancia(double lat1, double lon1){
        final double radio_tierra = 6371.01; //kilometro
		List<CoordenadasEmp> listEmp=coordenadasEmpRepository.todos();
		List<String> listEmpCer;
		listEmpCer = new ArrayList<>();
		List<String> dist;
		dist = new ArrayList<>();
		for(int i = 0; i < listEmp.size();i++) {		
			double lat2=Double.valueOf(listEmp.get(i).getLatitud());
			double lon2=Double.valueOf(listEmp.get(i).getLongitud());
			 lat2=Math.toRadians(lat2);
			 lon2=Math.toRadians(lon2);
			 lat1=Math.toRadians(lat1);
             lon1=Math.toRadians(lon1);
			 double distancia = radio_tierra * Math.acos(
            		             Math.sin(lat1) * Math.sin(lat2)
		                       * Math.cos(lat1) * Math.cos(lat2) 
		                       * Math.cos(lon1 - lon2));
			 
			 if(distancia<=5) {
				 listEmpCer.add(listEmp.get(i).getId());
				 String dis =String.valueOf(distancia);
				 dist.add(dis);
			
			 }
			 
		}
		
		
}
	
	@Transactional
	public void saveCoordenadas(double longitud, double latitud) {
		CoordenadasEmp coor = new CoordenadasEmp();
		coor.setLatitud(String.valueOf(latitud));
		coor.setLongitud(String.valueOf(longitud));
		coor.setEmp(null);
		coordenadasEmpRepository.save(coor);
	}
	
	public List<String> distancia(List<String> lista) {
		return lista;
	}
	
	
}
