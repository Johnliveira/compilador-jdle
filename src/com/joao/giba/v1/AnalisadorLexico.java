package com.joao.giba.v1;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class AnalisadorLexico {

	private List<Token> tokens;

	public AnalisadorLexico() {
		this.tokens = new LinkedList<Token>();
	}

	public List<Token> getTokens() {
		return this.tokens;
	}

	public void loadTokens() {

		try {
			Scanner scan = new Scanner(new File("src/jdle.txt"));
			while (scan.hasNextLine()) {
				tokens.addAll(getTokensLinha(scan.nextLine()));
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

	}

	private List<Token> getTokensLinha(String linha) {
		List<Token> tokensLine = new LinkedList<Token>();

		StringBuilder conteudo = new StringBuilder();
		System.out.println("Linha: " + linha);

		for (int i = 0; i < linha.length(); i++) {

			// Obtem os tipos numericos
			if (numero(linha.charAt(i))) {
				conteudo.append(linha.charAt(i));
				while (numero(linha.charAt(i + 1))) {
					conteudo.append(linha.charAt(i));
					i++;
				}
				tokensLine.add(Token.getNumero(conteudo.toString()));
				conteudo = new StringBuilder();
			}

			// Obtem execute ou identificador
			if (letra(linha.charAt(i))) {
				conteudo.append(linha.charAt(i));
				while (letra(linha.charAt(i + 1))) {
					conteudo.append(linha.charAt(i + 1));
					i++;
				}
				tokensLine.add(Token.getIdentifier(conteudo.toString()));
				conteudo = new StringBuilder();
			}

			// Obtem operadores
			if (operador(linha.charAt(i))) {
				tokensLine.add(Token.getOperador("" + linha.charAt(i)));
			}

			// Obtem parametros
			if (parametro(linha.charAt(i))) {
				tokensLine.add(Token.getParam("" + linha.charAt(i)));
			}

			// Obtem blocos
			if (bloco(linha.charAt(i))) {
				tokensLine.add(Token.getBlock("" + linha.charAt(i)));
			}

			// Obtem os finais de linha
			if (fimLinha(linha.charAt(i))) {
				tokensLine.add(Token.getFimLinha("" + linha.charAt(i)));
			}
		}

		return tokensLine;
	}

	private boolean numero(char charAt) {
		return charAt >= '0' && charAt <= '9';
	}

	private boolean letra(char charAt) {
		return (charAt >= 'a' && charAt <= 'z') || (charAt >= 'A' && charAt <= 'Z');
	}

	private boolean operador(char charAt) {
		return charAt == '=' || charAt == '+' || charAt == '-' || charAt == '*' || charAt == '/';
	}

	private boolean parametro(char charAt) {
		return charAt == '(' || charAt == ')';
	}

	private boolean bloco(char charAt) {
		return charAt == '{' || charAt == '}';
	}

	private boolean fimLinha(char charAt) {
		return charAt == ';';
	}
}
