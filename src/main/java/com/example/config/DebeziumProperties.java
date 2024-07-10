package com.example.config;

import io.smallrye.config.ConfigMapping;
import java.util.List;

@ConfigMapping(prefix = "debezium")
public interface DebeziumProperties {

    /**
     * @return list of schemas which should be monitored by the {@link
     *     io.debezium.engine.DebeziumEngine}
     */
    List<String> schemas();

    /**
     * @return list of tables which should be monitored by the {@link
     *     io.debezium.engine.DebeziumEngine}
     */
    List<String> tables();
}
