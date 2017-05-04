package dois.redes;

/**
 *
 * Author: Luan Augusto, LAF
 * Date: 3 de mai de 2017
 * Time: 19:28:10
 *
 */
public class Hash {

	public static final byte[] HASH_HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D' ,'E', 'F' };
	public static final int HASH_TAMANHO = 32;

	/**/

	private byte[] hash;
	private int multiplicador;

	/*
	 * Na construção do objeto é escolhido um multiplicador para preencher 
	 * a string caso seja necessário (hash / tamanho da entrada)
	 */
	public Hash(String entrada) {
		this.hash = entrada.getBytes();
		this.multiplicador = (Hash.HASH_TAMANHO / entrada.length());
	}

	/*
	 * Basicamente dividi o sistema de Hash em 3 etapas: 
	 * Preparação, nivelamento e conversão em hexadecimal
	 */
	public void iniciar() {
		this.preparar();
		this.nivelar();
		this.converter();
	}

	public String getHash() {
		return new String(this.hash);
	}

	public byte[] getBytes() {
		return this.hash;
	}

	/*
	 * Dentro do primeiro loop basicamente vai servir pra encontrar 
	 * um multiplicador que servirá pra gerar o valor que completará 
	 * a string.
	 * 
	 * O segundo loop vai fazer o preenchimento da String que será 
	 * nivelada, funciona da seguinte forma: o valor atual do hash 
	 * será multiplicado pelo resultado atual (tendo em vista que o 
	 * resultado incrementa a cada loop), para evitar que as letras 
	 * repetidas sigam um padrão eu fiz a multiplicação do resultado 
	 * por j + 1 (coloquei +1 pois em alguns momento j vai ser 0) e 
	 * o resultado será pego pelo resto da divisão por 256 (valor 
	 * entre 0-255) que representa um caractere na tabela ASCII.
	 */
	private void preparar() {
		int tamanho = this.hash.length;
		byte[] preparada = new byte[tamanho + (tamanho * this.multiplicador)];
		int resultado = 0;

		for (int i = 0; i < tamanho; ++i) { // Primeiro loop
			resultado += (this.hash[i] * 397) / (Hash.HASH_TAMANHO + i);
			
			for (int j = 0; j <= this.multiplicador; ++j) { // Segundo loop
				byte valor = (byte) ((this.hash[i] * (resultado * (j + 1))) % 256);
				preparada[(tamanho * j) + i] = valor;
			}
		}

		this.hash = preparada;
	}

	/*
	 * Após o nivelamento, todas as entradas terá um tamanho maior
	 * que o tamanho máximo da Hash, pra corrigir isso fiz o seguinte:
	 * enquanto (i < tamanho hash) não vai fazer nenhuma operação extra,
	 * quando o tamanho exceder o tamanho da Hash vai ser feita uma soma
	 * entre uma posição (i % tamanho hash) e o valor atual já nivelado
	 * e dividido por 2 para ficar entre 0 e 255.
	 */
	private void nivelar() {
		byte[] nivelada = new byte[Hash.HASH_TAMANHO];
		for (int i = 0; i < this.hash.length; ++i) {
			if ((i < Hash.HASH_TAMANHO)) {
				nivelada[i] = this.hash[i];
			} else {
				int posicao = (i % Hash.HASH_TAMANHO);
				nivelada[posicao] = (byte) ((nivelada[posicao] + this.hash[i]) / 2);
			}
		}
		this.hash = nivelada;
	}

	/*
	 * Após o nivelamento já é possível converter em hexadecimal, pra 
	 * evitar padrões no Hash, fiz da seguinte forma:
	 * 
	 * - Se a posição atual for par é feita a  soma lógica (bit a bit) 
	 * do valor atual da hash & 15, isso vai retornar um valor entre 
	 * 0 e 15 representando um caractere Hexadecimal.
	 * 
	 * - Se a posição atual for ímpar é feita a soma lógica entre o 
	 * valor atual da hash e 240, isso vai retornar um valor entre
	 * 0 e 240, pra que fique um valor entre 0 e 15, arrastei 4 bits
	 * (metade do byte) pra limitar o valor entre 0 e 15.
	 * 
	 * - Após isso só pego diretamente da array o caractere correspondente
	 * ao valor.
	 */
	private void converter() {
		for (int i = 0; i < this.hash.length; ++i) {
			if ((i % 2 == 0)) {
				this.hash[i] = (byte) (this.hash[i] & 15);
			} else {
				this.hash[i] = (byte) ((this.hash[i] & 240) >> 4);
			}
			this.hash[i] = Hash.HASH_HEX[this.hash[i]];
		}
	}

	public int matches(Hash hash) {
		int total = 0;
		for (int i = 0; i < Hash.HASH_TAMANHO; ++i) {
			if ((this.hash[i] == hash.getBytes()[i])) {
				total += 1;
			}
		}
		return total;
	}

}
