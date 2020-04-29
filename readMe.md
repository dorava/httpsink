## sink config
```$xslt
{
    "topics": "orderbsvc_product1",
    "tasks.max": "1",
    "connector.class": "io.confluent.connect.http.HttpSinkConnector",
    "http.api.url": "http://172.31.206.219:8080",
    "key.converter": "org.apache.kafka.connect.storage.StringConverter",
    "value.converter": "io.confluent.connect.avro.AvroConverter",
    "value.converter.schema.registry.url": "http://schema-registry:8081",
    "confluent.topic.bootstrap.servers": "kafka1:9092,kafka2:9093,kafka3:9094",
    "confluent.topic.replication.factor": "1",
    "reporter.bootstrap.servers": "kafka1:9092,kafka2:9093,kafka3:9094",
    "reporter.error.topic.replication.factor": "1",
    "reporter.result.topic.replication.factor": "1"
}
```