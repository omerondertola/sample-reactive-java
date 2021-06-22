package tr.com.tolaas.spring.reactive.demo.samplereactivejava.fluxNmono;

import org.junit.jupiter.api.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Supplier;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class FluxFactoryMethodsTest {

    @Test
    public void testFluxFromArray() {
        String[] people = new String[] {
                "Ömer","Önder","Defne","İrem","Ayşegül"
        };

        Flux<String> peopleFlux = Flux.fromArray(people);
        peopleFlux.subscribe(
                System.out::println,
                e -> System.out.println("Error occured: "+e),
                () -> System.out.println("flux completed")
        );
    };

    @Test
    public void testVerifyFluxFromArray() {
        String[] people = new String[] {"Ömer","Önder","Defne","İrem","Ayşegül"};
        Flux<String> peopleFlux = Flux.fromArray(people).log();
        StepVerifier.create(peopleFlux)
                .expectNext("Ömer","Önder")
                .expectNext("Defne","İrem")
                .expectNext("Ayşegül")
                .verifyComplete();
    }

    @Test
    public void testFluxFromList() throws InterruptedException {
        List<String> family = List.of("Ömer","Önder","Defne","İrem","Ayşegül");
        Flux<String> familyFlux = Flux.fromIterable(family);
        Flux<String> familyFluxWithLog = familyFlux.log().delayElements(Duration.ofSeconds(1));
        familyFluxWithLog.subscribe(
                System.out::println
        );
        Thread.sleep(6000);
    }

    @Test
    public void testVerifyFluxFromList() {
        List<String> teams = List.of("Fenerbahçe","Beşiktaş","Galatasaray");
        Flux<String> teamsFlux = Flux.fromIterable(teams);
        StepVerifier.create(teamsFlux.log())
                .expectNext("Fenerbahçe")
                .expectNext("Beşiktaş")
                .expectNext("Galatasaray")
                .verifyComplete();
    }

    @Test
    public void testFluxFromStream() {
        String[] cities = new String[] {"Ankara","İstanbul","İzmir"};

        Flux<String> citiesFlux = Flux.fromStream(Arrays.stream(cities)).log();

        StepVerifier.create(citiesFlux)
                .expectNext("Ankara")
                .expectNext("İstanbul")
                .expectNext("İzmir")
                .verifyComplete();
    }

    @Test
    public void testFluxFromIntStream() throws IOException, ExecutionException, InterruptedException {
        Stream<Object> generate = Stream.generate(
                new FileInputStreamSupplier());
        Flux<Object> fileReaderFlux = Flux.fromStream(generate);
        fileReaderFlux.subscribe(System.out::println);
    }

    public class FileInputStreamSupplier implements Supplier {

        private final BufferedReader bufferedReader;

        public FileInputStreamSupplier() throws IOException, ExecutionException, InterruptedException {
            FileReader reader = new FileReader("D:/deneme.txt");
            bufferedReader = new BufferedReader(reader);
        }

        @Override
        public Object get() {
            try {
                while(true) {
                    String s = bufferedReader.readLine();
                    if (s != null) {
                        Thread.sleep(100);
                        return s;
                    } else {
                        Thread.sleep(0,10);
                    }
                }
            } catch (IOException | InterruptedException e) {
                e.printStackTrace();
            }
            return "null";
        }
    }
}