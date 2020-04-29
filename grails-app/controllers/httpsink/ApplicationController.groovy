package httpsink

import com.google.common.base.Splitter
import com.google.common.collect.MapDifference
import com.google.common.collect.Maps
import com.google.common.collect.Sets
import com.google.gson.Gson
import grails.core.GrailsApplication
import grails.plugins.*
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient
import io.confluent.kafka.serializers.KafkaAvroDeserializer
import io.confluent.kafka.serializers.KafkaAvroSerializer
import org.apache.avro.Schema
import org.apache.avro.file.DataFileStream
import org.apache.avro.generic.GenericData
import org.apache.avro.generic.GenericDatumReader
import org.apache.avro.generic.GenericDatumWriter
import org.apache.avro.generic.GenericRecord
import org.apache.avro.io.DatumReader
import org.apache.avro.io.DatumWriter
import org.apache.avro.io.Decoder
import org.apache.avro.io.EncoderFactory
import org.apache.avro.io.JsonDecoder
import org.apache.avro.io.JsonEncoder
import org.apache.commons.lang3.StringUtils
import org.apache.kafka.clients.consumer.KafkaConsumer

class ApplicationController implements PluginManagerAware {

    GrailsApplication grailsApplication

    GrailsPluginManager pluginManager

    def index() {
        def s = request.reader.text
        println(s)
        def before = StringUtils.substringBetween(s, "before=Struct{", "},");
        def after = StringUtils.substringBetween(s, "after=Struct{", "},");
        def db = StringUtils.substringBetween(s, "db=", ",");
        def table = StringUtils.substringBetween(s, "table=", ",");
        def op = StringUtils.substringBetween(s, "op=", ",");
        def ts_ms = StringUtils.substringBetween(s, "ts_ms=", ",");

        println before
        println after
        println db
        println table
        println op
        println ts_ms

        if (op != "c") {
            def be = Splitter.on(",").withKeyValueSeparator("=").split(before);
            if (op != "d") {
                def af = Splitter.on(",").withKeyValueSeparator("=").split(after);

                MapDifference<String, String> dif = Maps.difference(be, af)
                println(dif)
                println dif.entriesDiffering() //changed
                println(dif.entriesOnlyOnRight()) // Null to value
                println(dif.entriesOnlyOnLeft())  // Value to null
            }
        }

        [grailsApplication: grailsApplication, pluginManager: pluginManager]
    }
}