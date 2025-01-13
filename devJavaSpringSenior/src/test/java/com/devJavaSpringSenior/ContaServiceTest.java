package com.devJavaSpringSenior;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.devJavaSpringSenior.domain.ContaEntity;
import com.devJavaSpringSenior.domain.ContaRepository;
import com.devJavaSpringSenior.infrastructure.ContaService;
import com.devJavaSpringSenior.infrastructure.dto.ContaDto;
import com.devJavaSpringSenior.infrastructure.dto.ValorPagoDto;

//@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class ContaServiceTest {
	
    @InjectMocks
    private ContaService contaService;

    @Mock
    private ContaRepository contaRepository;

    private ContaDto contaDto;
    private ContaEntity contaEntity;
    private List<ContaEntity> listaDeContas;
    
    @Before
    public void setUp() throws ParseException {
    	 MockitoAnnotations.openMocks(this);
        // Preparando os dados para os testes
        contaDto = new ContaDto();
        contaDto.setId(1L);
        contaDto.setDescricao("Teste de Conta");
        contaDto.setSituacao("Paga");
        contaDto.setValor(100.0);
        contaDto.setDataPagamento("10/10/2020");
        contaDto.setDataVencimento("01/10/2020");

        contaEntity = new ContaEntity();
        contaEntity.setId(1L);
        contaEntity.setDescricao("Teste de Conta");
        contaEntity.setSituacao("Paga");
        contaEntity.setValor(100.0);
        contaEntity.setDataPagamento(new SimpleDateFormat("dd/MM/yyyy").parse("10/10/2020"));
        contaEntity.setDataVencimento(new SimpleDateFormat("dd/MM/yyyy").parse("01/10/2020"));

        listaDeContas = Arrays.asList(contaEntity);
    }
    
    @Test
    public void testGetById() {
        // Definir comportamento do mock
        when(contaRepository.findById(1L)).thenReturn(Optional.of(contaEntity));

        // Teste do método
        ContaDto resultado = contaService.getById(1L);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId().longValue());
        verify(contaRepository, times(1)).findById(1L);
    }
    
    @Test
    public void testCreate() {
        // Definir comportamento do mock
        when(contaRepository.save(any(ContaEntity.class))).thenReturn(contaEntity);

        // Teste do método
        ContaDto resultado = contaService.create(contaDto);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId().longValue());
        verify(contaRepository, times(1)).save(any(ContaEntity.class));
    }
    
    @Test
    public void testFindTotalPagoPorPeriodo() {
        // Preparando os dados
        Pageable pageable = PageRequest.of(0, 10);
        when(contaRepository.findByDataPagamentoBetween(any(Date.class), any(Date.class), eq(pageable)))
                .thenReturn(listaDeContas);

        // Teste do método
        ValorPagoDto resultado = contaService.findTotalPagoPorPeriodo("01-10-2020", "31-10-2020", pageable);
        assertNotNull(resultado);
        assertEquals(100.0, resultado.getValorPago(), 0.001);
    }

    @Test
    public void testFindContasPorVencimentoDescricao() {
        // Preparando os dados
        Pageable pageable = PageRequest.of(0, 10);
        when(contaRepository.findByDataVencimentoAndDescricaoContainingIgnoreCase(any(Date.class), eq("Teste"), eq(pageable)))
                .thenReturn(listaDeContas);

        // Teste do método
        List<ContaDto> resultado = contaService.findContasPorVencimentoDescricao("01-10-2020", "Teste", pageable);
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(contaRepository, times(1)).findByDataVencimentoAndDescricaoContainingIgnoreCase(any(Date.class), eq("Teste"), eq(pageable));
    }

    @Test
    public void testAtualizaConta() {
        // Definir comportamento do mock
        when(contaRepository.existsById(1L)).thenReturn(true);
        when(contaRepository.save(any(ContaEntity.class))).thenReturn(contaEntity);

        // Teste do método
        ContaDto resultado = contaService.atualizaConta(contaDto);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId().longValue());
        verify(contaRepository, times(1)).save(any(ContaEntity.class));
    }

    @Test
    public void testAtualizaSituacaoConta() {
        // Definir comportamento do mock
        when(contaRepository.existsById(1L)).thenReturn(true);
        when(contaRepository.save(any(ContaEntity.class))).thenReturn(contaEntity);

        // Teste do método
        ContaDto resultado = contaService.atualizaSituacaoConta(contaDto);
        assertNotNull(resultado);
        assertEquals(1L, resultado.getId().longValue());
        verify(contaRepository, times(1)).save(any(ContaEntity.class));
    }

    @Test
    public void testExisteObjeto() {
        // Definir comportamento do mock
        when(contaRepository.existsById(1L)).thenReturn(true);

        // Teste do método
        boolean existe = contaService.existeObjeto(1L);
        assertTrue(existe);
        verify(contaRepository, times(1)).existsById(1L);
    }

}
