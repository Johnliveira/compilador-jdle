package com.joao.giba.v1;

public class Token {

	public static final int IDENTIFICADOR = 0;
	public static final int VARIAVEL = 1;
	public static final int NUMERO = 2;
	public static final int OPERADOR = 3;
	public static final int EXECUTE = 4;
	public static final int FIM_LINHA = 5;
	public static final int ABRE_PARAMETRO = 6; 
	public static final int FECHA_PARAMETRO = 7; 
	public static final int INICIO_BLOCO = 8;
	public static final int FIM_BLOCO = 9;

	private int tipo;
	private String lexema;
	private String texto;

	public int getTipo() {
		return tipo;
	}

	public String getLexema() {
		return lexema;
	}

	public String getTexto() {
		return texto;
	}

	public Token(int tipo, String lexema, String texto) {
		super();
		this.tipo = tipo;
		this.lexema = lexema;
		this.texto = texto;
	}

	@Override
	public String toString() {
		return "Token [tipo: " + tipo + ", lexema: " + lexema + ", texto: " + texto + "]";
	}

	public static Token getIdentifier(String conteudo) {
		if (conteudo.equals("execute")) {
			return new Token(EXECUTE, "<execute>", conteudo);
		} else if (conteudo.equals("int")) {
			return new Token(VARIAVEL, "<field>", conteudo);
		} else {
			return new Token(IDENTIFICADOR, "<identifier>", conteudo);
		}
	}
	
	public static Token getNumero(String conteudo) {
		return new Token(NUMERO, "<number>", conteudo);
	}
	
	public static Token getOperador(String conteudo) {
		return new Token(OPERADOR, "<operador>", conteudo);
	}
	
	public static Token getParam(String conteudo) {
		if (conteudo.equals("(")) {
			return new Token(ABRE_PARAMETRO, "<open parameter>", conteudo);
		} else {
			return new Token(FECHA_PARAMETRO, "<close parameter>", conteudo);
		}
	}
	
	public static Token getBlock(String conteudo) {
		if (conteudo.equals("{")) {
			return new Token(INICIO_BLOCO, "<block start>", conteudo);
		} else {
			return new Token(FIM_BLOCO, "<block end>", conteudo);
		}
	}
	
	public static Token getFimLinha(String conteudo) {
		return new Token(FIM_LINHA, "<end line>", conteudo);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((lexema == null) ? 0 : lexema.hashCode());
		result = prime * result + ((texto == null) ? 0 : texto.hashCode());
		result = prime * result + tipo;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Token other = (Token) obj;
		if (lexema == null) {
			if (other.lexema != null)
				return false;
		} else if (!lexema.equals(other.lexema))
			return false;
		if (texto == null) {
			if (other.texto != null)
				return false;
		} else if (!texto.equals(other.texto))
			return false;
		if (tipo != other.tipo)
			return false;
		return true;
	}
	
}
