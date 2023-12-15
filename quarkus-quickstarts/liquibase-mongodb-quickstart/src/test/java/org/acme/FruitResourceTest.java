package org.acme;

import com.mongodb.client.ListIndexesIterable;
import com.mongodb.client.MongoClient;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.common.mapper.TypeRef;
import org.bson.Document;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.DisabledOnOs;
import org.junit.jupiter.api.condition.OS;

import jakarta.inject.Inject;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static io.restassured.RestAssured.get;

@QuarkusTest
@DisabledOnOs(OS.WINDOWS)
class FruitResourceTest {

    @Inject
    MongoClient mongoClient;

    @Test
    public void testTheEndpoint() {
        // assert that a fruit exist as one has been created in the changelog
        List<Fruit> list = get("/fruits").as(new TypeRef<List<Fruit>>() {
        });
        Assertions.assertEquals(1, list.size());
    }

    @Test
    public void validateTheIdx() {
        // check that the index that the changelog created exist
        ListIndexesIterable<Document> indexes = mongoClient.getDatabase("fruits").getCollection("Fruit").listIndexes();
        Set<String> names = StreamSupport.stream(indexes.spliterator(), false)
                .map(doc -> doc.getString("name"))
                .collect(Collectors.toSet());
        Assertions.assertTrue(names.contains("colorIdx"));
    }
}
