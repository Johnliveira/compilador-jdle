package com.joao.giba.v1;

public class JDleApp {

	public static void main(String[] args) throws Exception {
		AnalisadorLexico let = new AnalisadorLexico();
		let.loadTokens();
		
		AnalisadorSintatico sit = new AnalisadorSintatico(let.getTokens());
		sit.execute();
	
	}
}
