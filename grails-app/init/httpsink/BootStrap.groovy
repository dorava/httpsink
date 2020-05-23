package httpsink

class BootStrap {

    def init = { servletContext ->
        def sink = Sink.get("5ec8bde40799e602009f8117")
        if (!sink)
            sink = new Sink(id: "5ec8bde40799e602009f8117", domain: "test", service: "test").save(flush: true, failOnError: true)
        println("first record:" + sink)
    }
    def destroy = {
    }
}
