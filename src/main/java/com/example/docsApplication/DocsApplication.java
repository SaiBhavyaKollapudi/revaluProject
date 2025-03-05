package com.example.docsApplication;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.example.models.Categories;
import com.example.models.Documents;

@SpringBootApplication
@EnableMongoRepositories
public class DocsApplication {
    private static final Logger logger = LoggerFactory.getLogger(DocsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(DocsApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(DocumentRepository repository) {
        return args -> {
            if (repository.count() == 0) {
                logger.info("Initializing database with sample documents");
                List<Documents> documents = List.of(
                        new Documents("646e39bfb0768053aa35e7a1", "0d835b0d-4d4e-46cc-88e1-5169f638f6dc", "Paint", List.of(new Categories(1, "Coverings"), new Categories(3, "End product")), List.of("new-product")),
                        new Documents("636e39bfb0768053aa35e7a2", "57f318aa-75ee-4b4b-a7c0-e17400b91897", "Mortar", List.of(new Categories(2, "Building products"), new Categories(3, "End product")), null),
                        new Documents("636e39bfb0768053aa35e7a4", "75ee-4baa-0d835bb-a7c0-e174005169f7", "Expanded cork", List.of(new Categories(4, "Insulation"), new Categories(2, "Building products")), List.of("bio-based"))
                );
                repository.saveAll(documents);
            }
        };
    }
}