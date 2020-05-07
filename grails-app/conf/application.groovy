environments {
    development {
        grails {
            mongodb {
//                url = "${MONGO_URL: 'mongodb://root:root@172.16.10.245:27017/httpsink?authSource=admin'}"
                url = 'mongodb://root:root@172.16.10.245:27017/httpsink?authSource=admin'
            }
        }
    }
    production {
        grails {
            mongodb {
                url = 'mongodb://root:root@mongo:27017/httpsink?authSource=admin'
            }
        }
    }
}



