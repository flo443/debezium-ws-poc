package com.example.config;

import io.debezium.config.Configuration;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Produces;

import java.util.HashMap;

@Singleton
public class DebeziumConfig {

    private static final String HOST = "localhost";

    @ConfigProperty(name = "quarkus.datasource.devservices.port", defaultValue = "")
    String PORT;

    @ConfigProperty(name = "quarkus.datasource.devservices.db-name", defaultValue = "")
    String DB_NAME;

    @ConfigProperty(name = "quarkus.datasource.devservices.username", defaultValue = "")
    String DB_USER;

    @ConfigProperty(name = "quarkus.datasource.devservices.password", defaultValue = "")
    String DB_PASSWORD;

    @Produces
    @Singleton
    public Configuration postgresConnector(DebeziumProperties debeziumProperties) {

        var properties = new HashMap<String, String>();
        properties.put("name", "websocket-application");
        properties.put("connector.class", "io.debezium.connector.postgresql.PostgresConnector");

        properties.put("offset.storage", "org.apache.kafka.connect.storage.MemoryOffsetBackingStore");

        properties.put("offset.flush.interval.ms", "60000");
        properties.put("database.hostname", HOST);
        properties.put("database.port", PORT);
        properties.put("database.user", DB_USER);
        properties.put("database.password", DB_PASSWORD);
        properties.put("database.dbname", DB_NAME);
        properties.put("plugin.name", "pgoutput");
        properties.put("schema.include.list", String.join(",", debeziumProperties.schemas()));
        properties.put("table.include.list", String.join(",", debeziumProperties.tables()));
        properties.put("topic.prefix", "cdc_"); // not needed in the code, but required by debezium
        properties.put("snapshot.mode", "no_data");
        properties.put("slot.name", "debezium_slot_dummy_name"); // TODO needs to be unique for each instance

        /**
         * TODO add cron job that checks open replication connections and removes all that are active=false
         */

        return Configuration.from(properties);
    }
}
