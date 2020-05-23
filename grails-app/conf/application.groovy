environments {
    development {
        grails {
            mongodb {
//                url = "${MONGO_URL: 'mongodb://root:root@172.16.10.245:27017/httpsink?authSource=admin'}"
                url = 'mongodb://root:root@172.16.10.246:27017/httpsink?authSource=admin'
            }
        }
    }
    production {
        grails {
            mongodb {
                url = 'mongodb://root:root@mongo:27017/httpsink?authSource=admin'
                options {
                    connectionsPerHost = 1000 // The maximum number of connections allowed per host
//                    threadsAllowedToBlockForConnectionMultiplier = 5
//                    maxWaitTime = 120000 // Max wait time of a blocking thread for a connection.
//                    connectTimeout = 0 // The connect timeout in milliseconds. 0 == infinite
//                    socketTimeout = 0 // The socket timeout. 0 == infinite
//                    socketKeepAlive = false // Whether or not to have socket keep alive turned on
//                    writeConcern = new com.mongodb.WriteConcern(0, 0, false) // Specifies the number of servers to wait for on the write operation, and exception raising behavior
//                    sslEnabled = false // Specifies if the driver should use an SSL connection to Mongo
//                    socketFactory = ... // Specifies the SocketFactory to use for creating connections
                }
            }
        }
    }
}

grails.gorm.default.constraints = {
    '*'(nullable: true)
}

