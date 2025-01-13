package com.devJavaSpringSenior;

import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.ArgumentMatchers.anyList;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.multipart.MultipartFile;

import com.devJavaSpringSenior.domain.ContaEntity;
import com.devJavaSpringSenior.domain.ContaRepository;
import com.devJavaSpringSenior.infrastructure.ImportaContasCsv;
import com.devJavaSpringSenior.infrastructure.exception.CabecalhoException;

@SpringBootTest
@ComponentScan(basePackages = "com.devJavaSpringSenior")
public class ImportaContasCsvTest {

	    @InjectMocks
	    private ImportaContasCsv importaContaCsv;

	    @Mock
	    private ContaRepository contaRepository;

	    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

	    @Before
	    public void setUp() {
	    	 MockitoAnnotations.openMocks(this);
	    }

	    @Test
	    public void testLerArquivoMultipartFile() throws Exception {
	        // Mock do arquivo multipart
	        MultipartFile mockFile = mock(MultipartFile.class);
	        String conteudoArquivo = "dataVencimento;dataPagamento;valor;descricao;situacao\n01-01-2025;05-01-2025;1000.0;Conta de Luz;Paga";
	        InputStream inputStream = new ByteArrayInputStream(conteudoArquivo.getBytes());

	        when(mockFile.getInputStream()).thenReturn(inputStream);

	        List<ContaEntity> contasMock = new ArrayList<>();
	        contasMock.add(new ContaEntity(dateFormat.parse("01-01-2025"), dateFormat.parse("05-01-2025"), 1000.0, "Conta de Luz", "Paga"));

	        when(contaRepository.saveAll(anyList())).thenReturn(contasMock);

	        List<ContaEntity> resultado = importaContaCsv.lerArquivo(mockFile);

	        assertNotNull(resultado);
	        assertEquals(1, resultado.size());
	        assertEquals("Conta de Luz", resultado.get(0).getDescricao());
	    }

	    @Test
	    public void testLerArquivoInputStream() throws Exception {
	        String conteudoArquivo = "dataVencimento;dataPagamento;valor;descricao;situacao\n01-01-2025;05-01-2025;1000.0;Conta de Luz;Paga";
	        InputStream inputStream = new ByteArrayInputStream(conteudoArquivo.getBytes());

	        List<ContaEntity> contasMock = new ArrayList<>();
	        contasMock.add(new ContaEntity(dateFormat.parse("01-01-2025"), dateFormat.parse("05-01-2025"), 1000.0, "Conta de Luz", "Paga"));

	        when(contaRepository.saveAll(anyList())).thenReturn(contasMock);

	        List<ContaEntity> resultado = importaContaCsv.lerArquivo(inputStream);

	        assertNotNull(resultado);
	        assertEquals(1, resultado.size());
	        assertEquals("Conta de Luz", resultado.get(0).getDescricao());
	    }

	    @Test
	    public void testAdicionarContasLinhaValida() throws Exception {
	        List<ContaEntity> contas = new ArrayList<>();
	        String linha = "01-01-2025;05-01-2025;1000.0;Conta de Luz;Paga";

	        List<ContaEntity> resultado = importaContaCsv.adicionarContas(linha, contas);

	        assertNotNull(resultado);
	        assertEquals(1, resultado.size());
	        assertEquals("Conta de Luz", resultado.get(0).getDescricao());
	    }

	    @Test
	    public void testAdicionarContasLinhaInvalida() {
	        List<ContaEntity> contas = new ArrayList<>();
	        String linha = "01-01-2025;05-01-2025;1000.0"; // Linha com campos faltando

	        List<ContaEntity> resultado = importaContaCsv.adicionarContas(linha, contas);

	        assertNull(resultado);
	    }

	    @SuppressWarnings("static-access")
		@Test
	    public void testValidadorCabecalhoInvalido() {
	        try {
	        	ImportaContasCsv.validadorCabecalho("dataVencimento;dataPagamento");
	            fail("Deveria ter lançado CabecalhoException");
	        } catch (CabecalhoException e) {
	            // Exceção esperada
	        }
	    }

	    @SuppressWarnings("static-access")
		@Test
	    public void testValidadorCabecalhoValido() {
	        try {
	        	ImportaContasCsv.validadorCabecalho("dataVencimento;dataPagamento;valor;descricao;situacao");
	        } catch (CabecalhoException e) {
	            fail("Não deveria lançar exceção para um cabeçalho válido.");
	        }
	    }
}
