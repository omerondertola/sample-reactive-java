package tr.com.tolaas.spring.reactive.demo.samplereactivejava.fluxNmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

public class FluxRangeFactoryMethodsTest {

    @Test
    public void testIntegerRangeFlux() {
        Flux<Integer> integerFlux = Flux.range(1, 7);

        StepVerifier.create(integerFlux.log())
                .expectNext(1,2,3,4,5)
                .expectNext(6,7)
                .verifyComplete();
    }
}
