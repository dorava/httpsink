package httpsink

import com.google.common.base.Splitter
import com.google.common.collect.MapDifference
import com.google.common.collect.Maps
import com.google.common.collect.Sets
import grails.core.GrailsApplication
import grails.plugins.*
import org.apache.commons.lang3.StringUtils


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