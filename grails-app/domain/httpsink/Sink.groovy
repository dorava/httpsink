package httpsink

class Sink {
    String id
    String service
    String domain
    String domainId
    String operation
    String timestampms
    Map<String, String> before
    Map<String, String> after
    String changes
    Date createdon

    static constraints = {
        before nullable: true
        after nullable: true
        changes nullable: true
    }
    static mapping = {
        version false
    }
}
