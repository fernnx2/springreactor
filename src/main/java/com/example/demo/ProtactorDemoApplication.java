package com.example.demo;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class ProtactorDemoApplication implements CommandLineRunner {
	
	public static final Logger log = LoggerFactory.getLogger(ProtactorDemoApplication.class);
	
	public List<String> guitars = new ArrayList<String>();
	

	public static void main(String[] args) {
		SpringApplication.run(ProtactorDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		createMono();
		this.guitars.add("Jackson");
		this.guitars.add("Fender");
		this.guitars.add("Gibson");
		createFlux();
		fluxToMono();
		doToNext();
		map();
		flatMap();
		range();
		zipWith();
		mergeFlux();
		filterFlux();
		takeLast();
		take();
		empty();
		
		
	}

	public void createMono() {
		// flujo de un solo dato
		Mono<Integer> monoNumb = Mono.just(9);
		monoNumb.subscribe(numero -> log.info(numero.toString()));
	}
	
	public void createFlux() {
		// flujo de varios datos
		Flux<String> fxGuitars = Flux.fromIterable(this.guitars);
		fxGuitars.subscribe(g -> log.info(g.toString()));
	}
	
	public void fluxToMono() {
		// convert flux data to mono data
		Flux<String> fxGuitars = Flux.fromIterable(this.guitars);
		fxGuitars.collectList().subscribe(guitarList -> log.info(guitarList.toString()));
		
	}
	
	public void doToNext() {
		
		Flux<String> fxGuitars = Flux.fromIterable(this.guitars);
		fxGuitars.doOnNext(log::info).subscribe();
	}
	
	public void map() {
		Flux<String> fxGuitars = Flux.fromIterable(this.guitars);
		fxGuitars.map(guitar -> "Guitarra profesional: " + guitar).subscribe(log::info);
	}
	
	
	public void flatMap() {
		Mono.just("Fernnado")
			.flatMap(name -> Mono.just(25))
			.subscribe(number -> log.info(number.toString()));
		
	}
	
	
	public void range() {
		Flux<Integer> count = Flux.range(0, 10);
		count.map( x -> {
			return x+1;
		}).subscribe(x -> log.info(x.toString()));
	}
	
	
	public void zipWith() {
		// combina 2 flujos
		Flux<String> fxGuitars = Flux.fromIterable(this.guitars);
		List<String> artist = new ArrayList<>();
		artist.add("Metallica");
		artist.add("Megadeth");
		artist.add("Foo Fighters");
		Flux<String> fxArtist = Flux.fromIterable(artist);
		fxGuitars.zipWith(fxArtist, (g,a) -> String.format("Flux1: %s, Flux2: %s", g,a))
		.subscribe(x-> log.info(x));
	}
	
	public void mergeFlux() {
		// fusiona dos flujos en uno solo
		Flux<String> fxGuitars = Flux.fromIterable(this.guitars);
		List<String> artist = new ArrayList<>();
		artist.add("Metallica");
		artist.add("Megadeth");
		artist.add("Foo Fighters");
		Flux<String> fxArtist = Flux.fromIterable(artist);
		Flux.merge(fxGuitars,fxArtist).subscribe(log::info);
	}
	
	
	public void filterFlux() {
		Flux<String> fxGuitars = Flux.fromIterable(this.guitars);
		fxGuitars.filter(p -> p.startsWith("J")).subscribe(log::info);
	}
	
	
	public void takeLast() {
		Flux<String> fxGuitars = Flux.fromIterable(this.guitars);
		fxGuitars.takeLast(2).subscribe(log::info);
	}
	
	public void take() {
		Flux<String> fxGuitars = Flux.fromIterable(this.guitars);
		fxGuitars.take(1).subscribe(log::info);
	}
	
	public void empty() {
		this.guitars.clear();
		Flux<String> fxGuitars = Flux.fromIterable(this.guitars);
		fxGuitars.defaultIfEmpty("No hay Guitarras").subscribe(log::info);
	}
	
	
	
	
}
