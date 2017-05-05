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
	public static final int HASH_TAMANHO = 64;

	/**/

	private byte[] hash;
	private int multiplicador;

	public Hash(String entrada) {
		this.hash = entrada.getBytes();
		this.multiplicador = (Hash.HASH_TAMANHO / entrada.length());
	}

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
