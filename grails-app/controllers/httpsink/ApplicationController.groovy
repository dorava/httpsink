package httpsink

import com.google.common.base.Splitter
import com.google.common.collect.Maps
import grails.core.GrailsApplication
import grails.plugins.GrailsPluginManager
import grails.plugins.PluginManagerAware
import org.apache.commons.lang3.StringUtils

import static grails.async.Promises.task

class ApplicationController implements PluginManagerAware {

    GrailsApplication grailsApplication

    GrailsPluginManager pluginManager

    def index() {
        def s = request.reader.text
        println(URLDecoder.decode(s, "UTF-8"))
        def before = StringUtils.substringBetween(s, "before=Struct{", "},")
        def after = StringUtils.substringBetween(s, "after=Struct{", "},")
        def db = StringUtils.substringBetween(s, "db=", ",")
        def table = StringUtils.substringBetween(s, "table=", ",")
        def op = StringUtils.substringBetween(s, "op=", ",")
        def ts_ms = StringUtils.substringBetween(s, "op=u,ts_ms=", "}")

        if (!before && !after) return []

        Map<String, String> be
        Map<String, String> af
        def id
        def dif
        switch (op) {
            case "u":
                be = Splitter.on(",").withKeyValueSeparator("=").split(before)
                be = Maps.newHashMap(be)
                be.replaceAll({ k, v -> decode(v) })
                af = Splitter.on(",").withKeyValueSeparator("=").split(after)
                af = Maps.newHashMap(af)
                af.replaceAll({ k, v -> decode(v) })
                dif = Maps.difference(be, af).entriesDiffering().keySet().join(",")
                break
            case "c":
                be = null
                af = Splitter.on(",").withKeyValueSeparator("=").split(after)
                af = Maps.newHashMap(af)
                af.replaceAll({ k, v -> decode(v) })
                break
            case "d":
                be = Splitter.on(",").withKeyValueSeparator("=").split(before)
                be = Maps.newHashMap(be)
                be.replaceAll({ k, v -> decode(v) })
                af = null
                break
            default:
                break
        }

        def task = task {
            Sink.withNewTransaction {
                def domainId = be ? be["id"] : af["id"]
                def sink = Sink.findByServiceAndDomainAndDomainIdAndTimestampms(db, table, domainId, ts_ms)
                if (!sink) {
                    sink = new Sink()
                    sink.domainId = domainId
                    sink.service = db
                    sink.domain = table
                    sink.operation = op
                    sink.timestampms = ts_ms
                    sink.createdon = new Date()
                    sink.before = be
                    sink.after = af
                    sink.changes = dif
                    sink.save(flash: true, failOnError: true)
                }
                return sink
            }
        }

        task.onComplete { result ->
            println(result)
        }
        []
    }

    private String decode(String value) {
        if (value != null && value != "") {
            return URLDecoder.decode(value, "UTF-8")
        }
        return value
    }
}