package dois.redes;

/**
 *
 * Author: Luan Augusto, LAF
 * Date: 3 de mai de 2017
 * Time: 13:32:20
 *
 */
public class HashBUGADO {

	private static final byte[] HASH_HEX = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D' ,'E', 'F' };
	private int tamanho, multiplicador;
	private byte[] entrada;

	public HashBUGADO(String entrada, int tamanho) {
		this.entrada = entrada.getBytes();
		this.tamanho = tamanho;
		this.multiplicador = (tamanho / entrada.length()) + 1;
	}

	public String getHash() {
		// Gerar a HASH
		this.preparar();
		this.nivelar();
		this.toHEX();

		// Retorna a HASH para o usuário
		return new String(this.entrada);
	}

	private void preparar() {
		int tamanho = this.entrada.length;
		byte[] tratamento = new byte[tamanho * this.multiplicador];

		for (int i = 0; i < tamanho; ++i) {
			int somatorio = ((this.entrada[i] * 31) / (i + this.tamanho));
			for (int j = 0; j < this.multiplicador; ++j) {
				byte letra = (byte) ((this.entrada[i] * (somatorio * (j + 1))) % 256);
				tratamento[(tamanho * j) + i] = letra;
			}
		}

		this.entrada = tratamento;
	}

	private void nivelar() {
		
	}

	private void nivelarBUGADO() {
		if ((this.entrada.length < this.tamanho)) {
			this.nivelarAbaixo();
		} else if ((this.entrada.length > this.tamanho)) {
			this.nivelarAcima();
		} else {
			// Não precisa nivelar
		}
	}

	private byte[] gerarVazia() {
		byte[] vazia = new byte[this.tamanho];
		for (int i = 0; i < this.tamanho; ++i) {
			vazia[i] = HashBUGADO.HASH_HEX[15];
		}
		return vazia;
	}

	private void nivelarAbaixo() {
		byte[] vazia = this.gerarVazia();
		for (int i = 0; i < this.tamanho; ++i) {
			int posicao = (i % this.entrada.length);
			vazia[i] = (byte) ((vazia[i] + this.entrada[posicao]) / 2);
		}
		this.entrada = vazia;
	}

	private void nivelarAcima() {
		byte[] vazia = this.gerarVazia();
		for (int i = 0; i < this.entrada.length; ++i) {
			vazia[i % 16] = (byte) ((vazia[i % 16] + this.entrada[i]) / 2);
		}
		this.entrada = vazia;
	}

	private void toHEX() {
		for (int i = 0; i < this.entrada.length; ++i) {
			if ((i % 2 == 0)) {
				this.entrada[i] = (byte) (this.entrada[i] & 15);
			} else {
				this.entrada[i] = (byte) ((this.entrada[i] & 240) >> 4);
			}
			this.entrada[i] = HashBUGADO.HASH_HEX[this.entrada[i]];
		}
	}

}
