package com.oms.product.config

class ConfigurationSpec {

    String host

    ConfigurationSpec() {
        if (null == host) {
            host = 'http://localhost:9092'
            println 'INFO: target-test-host: ' + host
        }
    }
}
