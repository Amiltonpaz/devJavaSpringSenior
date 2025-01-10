
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.FileInputStream;
import java.io.InputStream;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.devJavaSpringSenior.infrastructure.ImportaContasCsv;

public class ImportaContasCsvTest {
	
	@Autowired
	private ImportaContasCsv importaContasCsv;
	
	@Test
	void lerArquivosContas() throws Exception {
		
		var inputArquivo = lerArquivoTeste("contas.csv");
		var contas = importaContasCsv.lerArquivo(inputArquivo);
		
		assertAll(
				() -> assertEquals(3, contas.size())//,
				//() -> assertEquals("01/08/2025", contas.get(0).getDataVencimento()),
				//() -> assertEquals("05/01/2025", contas.get(1).getDataPagamento()),
				//() -> assertEquals(55.00, contas.get(2).getValor()),
				//() -> assertEquals("Empréstimo Pessoal.", contas.get(2).getDescricao()),
				//() -> assertEquals("Fechado", contas.get(2).getSituacao())
				);
		inputArquivo.close();
	}

	private InputStream lerArquivoTeste(final String nomeArquivo) throws Exception {
		var pathArquivo = ClassLoader.getSystemResource(nomeArquivo).toURI().getPath();
		return new FileInputStream(pathArquivo);
	}
}
