package com.oms.product.config

import com.oms.product.config.ConfigurationSpec
import groovyx.net.http.RESTClient

class ProductHelper {

    RESTClient restClient

    ProductHelper() {
        ConfigurationSpec configurationSpec = new ConfigurationSpec()
        restClient = new RESTClient(configurationSpec.host)
        restClient.handler.failure = { resp, data ->
            resp.setData(data)
            String headers = ''
            resp.headers.each { h ->
                headers = headers + "${h.name} : ${h.value}\n"
            }
            return resp
        }
    }

    def addProduct(String token, Map productRequest) {
        def response = restClient.post(
                path: "/Product/add",
                contentType: 'application/json;charset=UTF-8',
                headers: ['Authorization': "Bearer ${token}", 'Content-Type': 'application/json'],
                body: productRequest
        )
        return response
    }

    def updateProduct(String token, Map productRequest) {
        def response = restClient.post(
                path: "/Product/update",
                contentType: 'application/json;charset=UTF-8',
                headers: ['Authorization': "Bearer ${token}", 'Content-Type': 'application/json'],
                body: productRequest
        )
        return response
    }

    def getProduct(String token, String id) {
        def response = restClient.get(
                path: "/Product/get/${id}",
                headers: ['Authorization': "Bearer ${token}", 'Content-Type': 'application/json'])
        return response
    }

    def cancelProduct(String token, String id) {
        def response = restClient.delete(
                path: "/Product/cancel/${id}",
                headers: ['Authorization': "Bearer ${token}", 'Content-Type': 'application/json'])
        return response
    }

    def searchAllProducts(String token) {
        def response = restClient.get(
                path: "/Product/searchAllProducts",
                headers: ['Authorization': "Bearer ${token}", 'Content-Type': 'application/json'])
        return response
    }

    def searchByName(String token, String name,boolean isLike) {
        def response = restClient.get(
                path: "/Product/searchByName/${name}",
                headers: ['Authorization': "Bearer ${token}", 'Content-Type': 'application/json'],
                query: ['isLike': isLike])
        return response
    }
}
