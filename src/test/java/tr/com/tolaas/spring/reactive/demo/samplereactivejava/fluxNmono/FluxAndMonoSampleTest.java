package tr.com.tolaas.spring.reactive.demo.samplereactivejava.fluxNmono;

import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.sql.SQLOutput;

public class FluxAndMonoSampleTest {

    @Test
    public void testBasicFlux() {
        Flux<String> flux = Flux.just("Ömer Önder Tola", "Defne Tola", "İrem Tola");
            flux.subscribe(System.out::println);
    }

    @Test
    public void testFluxWithException() {
        Flux<String> flux = Flux.just("Ömer Önder Tola", "Defne Tola", "İrem Tola")
                .concatWith(Flux.error(new RuntimeException("Simulate An Exception Occured.")));

        flux.subscribe(System.out::println,
                (error) -> System.err.println(error.getMessage()));
    }

    @Test
    public void testFluxWithLog() {
        Flux<String> flux = Flux.just("Ömer Önder Tola", "Defne Tola", "İrem Tola")
                .log();

        flux.subscribe(
                System.out::println,
                (e)-> System.out.println("Exception occured: "+e.getMessage()));
    }

    @Test
    public void testFluxWithExceptionAndLog() {
        Flux<String> flux = Flux.just("Ömer Önder Tola", "Defne Tola", "İrem Tola")
                .concatWith(Flux.error(new RuntimeException("Exception attached to the flux!")))
                .log();

        flux.subscribe(
                System.out::println,
                (e)-> System.out.println("Exception occured: "+e.getMessage()));
    }

    @Test
    public void testFluxAfterErrorMessagesNotSend() {
        Flux<String> aFlux = Flux.just("Fenerbahçe", "Beşiktaş", "Galatasaray")
                .concatWith(Flux.error(new RuntimeException("An exception occurs here.")))
                .concatWith(Flux.just("This message should not be seen!"))
                .log();

        aFlux.subscribe(System.out::println,
                (error) -> System.out.println("Error occured: "+ error.getMessage()));
    }

    @Test
    public void testFluxCompleteMessage() {
        Flux<String> messageFlux = Flux.just("I", "Love", "You", "Baby", ":)")
                .log();

        messageFlux.subscribe(
                System.out::println,
                (e) -> System.out.println("Error occured: "+e.getMessage()),
                () -> System.out.println("Flux completed succesfully.")
        );
    }
}
