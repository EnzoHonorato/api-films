package br.com.enzohonorato.revisando;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

// mvn teste     Por padrao sem os profiles executa todos os testes menos integracao

// mvn test -Pintegration-tests
// ...

// esse teste aqui testa o contexto do spring, entao o bd tem q ta no ar
@SpringBootTest
class FilmsApplicationTests {

	@Test
	void contextLoads() {
	}

}
