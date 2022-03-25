package com.app.grantienda.enums;

public enum Categorias {
	Categoria("Categoría"),
	arteydiseno("Arte y diseño"),
	bebesyninos("Bebés y niños"),
	alimentoybebidas("Alimentos y Bebidas"),
	hogar("Hogar"),
	belleza("Belleza"),
	moda("Moda");
	
	private String nombre;
	
	Categorias(String nombre) {
		this.nombre=nombre;
	}

	public String getNombre() {
		return nombre;
	}
	
}
