package com.joao.giba.v1;

import java.util.LinkedList;
import java.util.List;

public class AnalisadorSintatico {
	
	private List<Token> tokens;

	private List<Token> identificadores;
	
	public AnalisadorSintatico(List<Token> tokens) {
		this.tokens = tokens;
		this.identificadores = new LinkedList<Token>();
	}

	public void execute() throws Exception {
		
		// Valida método execute
		if (tokens.get(0).getTipo() != Token.EXECUTE) {
			explodeException("O método execute não foi declarado.");
		}
		
		// Valida parâmetro do método execute
		if (tokens.get(1).getTipo() != Token.ABRE_PARAMETRO || tokens.get(2).getTipo() != Token.FECHA_PARAMETRO) {
			explodeException("O método execute necessita de um parâmetro devidamente declarado.");
		}
		
		// Valida abertura do bloco
		if (tokens.get(3).getTipo() != Token.INICIO_BLOCO) {
			explodeException("Não existe conexto para o método execute, bloco não iniciado.");
		}
		
		// Valida o bloco execute
		validaBloco();
	}
	
	private void validaBloco() throws Exception {
		boolean blocoFoiFechado = false;
		
		for (int i = 4; i < tokens.size(); i++) {
			
			if (tokens.get(i).getTipo() == Token.VARIAVEL) {
				i = validaFluxoVariavel(i);
			} else if (tokens.get(i).getTipo() == Token.NUMERO || tokens.get(i).getTipo() == Token.IDENTIFICADOR) {
				
				i = validaFluxoOperacao(i);
			} else if (tokens.get(i).getTipo() == Token.FIM_BLOCO) {
				blocoFoiFechado = true;
			} else {
				explodeException("Argumento não aceito.");
			}
			
		}
		
		if (!blocoFoiFechado) {
			explodeException("O bloco não foi devidamente fechado.");
		}
	}
	
	private int validaFluxoOperacao(int posicao) throws Exception {
		Token algarismo1 = tokens.get(posicao);
		Token operador = tokens.get(posicao + 1);
		Token algarismo2 = tokens.get(posicao + 2);
		Token fimLInha = tokens.get(posicao + 3); 
		
		if ((algarismo1.getTipo() == Token.IDENTIFICADOR && identificadores.contains(algarismo1))) {
		} else if (algarismo1.getTipo() == Token.NUMERO) {
		} else {
			explodeException("Tipo de elemento não aceitável.");
		}
		
		if (operador.getTexto().equals("=")) {
			explodeException("A expressão não está utilizando o operador em devida forma.");
		}
		
		if ((algarismo2.getTipo() == Token.IDENTIFICADOR && identificadores.contains(algarismo2))) {
		} else if (algarismo2.getTipo() == Token.NUMERO) {
		} else {
			explodeException("Tipo de elemento não aceitável.");
		}
		
		if (fimLInha.getTipo() != Token.FIM_LINHA) {
			explodeException("Não foi possível determinar o fim de linha.");
		}
		
		return posicao + 3;
	}

	private int validaFluxoVariavel(int posicao) throws Exception {
		int novaPosicao = 0;
		
		Token identificador = tokens.get(posicao + 1);
		Token operador = tokens.get(posicao + 2);
		
		if (operador.getTipo() == Token.FIM_LINHA) {
			return posicao + 2;
		}
		
		Token valor = tokens.get(posicao + 3);
		Token operacao = tokens.get(posicao + 4);
		Token valorOperacao = tokens.get(posicao + 5);
		Token fimLinha = tokens.get(posicao + 6);
		
		if (identificador.getTipo() != Token.IDENTIFICADOR) {
			explodeException("Declaração de variável não suportada.");
		}
		
		if (!identificadores.contains(identificador)) {
			identificadores.add(identificador);
			
			if ((operador.getTipo() == Token.OPERADOR && operador.getTexto().equals("=")) || operador.getTipo() == Token.FIM_LINHA) {
				novaPosicao = posicao + 3;
				
				if (valor.getTipo() == Token.NUMERO || (valor.getTipo() == Token.IDENTIFICADOR && identificadores.contains(valor))) {
					novaPosicao = posicao + 4;
					
					if (operacao.getTipo() != Token.FIM_LINHA && operacao.getTipo() == Token.OPERADOR && !operacao.getTexto().equals("=")) {
						
						if (valorOperacao.getTipo() == Token.NUMERO) {
							
							if (fimLinha.getTipo() == Token.FIM_LINHA) {
								novaPosicao = posicao + 6;
							} else {
								explodeException("Não foi possível encontrar o fim da instrução para a variável " + identificador.getTexto() + ".");
							}
						} else {
							
							if (valorOperacao.getTipo() == Token.IDENTIFICADOR) {
								
								if (identificadores.contains(valorOperacao)) {
									
									if (fimLinha.getTipo() == Token.FIM_LINHA) {
										novaPosicao = posicao + 6;
									} else {
										explodeException("Não foi possível encontrar o fim da instrução para a variável " + identificador.getTexto() + ".");
									}
								} else {
									explodeException("Variável " + valorOperacao.getTexto() + " ainda não foi declarada.");
								}
							} else {
								explodeException("Valor incorreto para a variável " + identificador.getTexto() + ".");
							}
						}
					} else {
						if (operacao.getTipo() != Token.FIM_LINHA) {
							explodeException("Não foi possível encontrar o fim da instrução para a variável " + identificador.getTexto() + ".");
						}
					}
				} else {
					explodeException("Valor incorreto para a variável " + identificador.getTexto() + ".");
				}
			} else {
				explodeException("A declaração da variável " + identificador.getTexto() + " está incorreta.");
			}
		} else {
			explodeException("Variável " + identificador.getTexto() + " já existe neste escopo.");
		}
		
		return novaPosicao;
	}

	public void explodeException(String msg) throws Exception {
		throw new Exception(msg);
	}
}
