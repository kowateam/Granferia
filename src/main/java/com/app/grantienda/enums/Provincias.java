package com.app.grantienda.enums;

public enum Provincias {
Provincia("Provincia"),
Buenos_Aires("Buenos Aires"),
	Catamarca("Catamarca"),
	Chaco("Chaco"),
	Chubut("Chubut"),
	Cordoba("Córdoba"),
	Corrientes("Corrientes"),
	Entre_Rios("Entre Ríos"),
	Formosa("Formosa"),
	Jujuy("Jujuy"),
	La_Pampa("La Pampa"),
	La_Rioja("La Rioja"),
	Mendoza("Mendoza"),
	Misiones("Misiones"),
	Neuquen("Neuquén"),
	Rio_Negro("Río Negro"),
	Salta("Salta"),
	San_Juan("San Juan"),
	San_Luis("San Luis"),
	Santa_Cruz("Santa Cruz"),
	Santa_fe("Santa Fe"),
	Sgo_del_estero("Sgo. del Estero"),
	Tierra_del_fuego("Tierra del Fuego"),
	Tucuman("Tucumán");

	private String nombre;

	Provincias(String nombre) {
		this.nombre=nombre;
	}
	public String getNombre() {
		return nombre;
	}
}
