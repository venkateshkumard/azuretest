package com.oms.product

import com.oms.product.config.ConfigurationSpec
import com.oms.product.config.ProductHelper
import groovyx.net.http.RESTClient
import org.springframework.http.HttpStatus
import spock.lang.Shared
import spock.lang.Specification

class ProductCRUDAccSpec extends Specification {

    @Shared
    ConfigurationSpec configurationSpec

    @Shared
    RESTClient restClient

    @Shared
    ProductHelper productHelper = new ProductHelper()

    def setupSpec() {
        configurationSpec = new ConfigurationSpec()
        restClient = new RESTClient(configurationSpec.host)
    }

    def 'CRUD operation for Product Details'() {

        given:
        def productId
        def productDisplayName = 'testProduct'
        def updatedDisplayName = 'updateProduct'
        def specifications = [name: 'specName', value: 'specVal']
        def dimensions = [weight: 9.5, height: 2.5, depth: 1.5]
        def packingInfo = [weight: 6.5, dimensions: dimensions]
        def productReq = [productDisplayName: productDisplayName, description: 'testDescription', price: 10.50,
                                 specifications    : specifications, packingInfo: packingInfo]
        def products = [productReq]
        def productRequest = [products:products]

        when: 'Adding one or more Product(s)'
        def addResponse = productHelper.addProduct(null, productRequest)
        productId = addResponse.responseData.products[0].id

        then: 'Checking the response code for Adding Product(s)'
        addResponse
        addResponse.responseBase.h.original.code == HttpStatus.CREATED.value()
        addResponse.responseData.products[0].id
        addResponse.responseData.products[0].productDisplayName
        addResponse.responseData.products[0].description
        addResponse.responseData.products[0].price
        addResponse.responseData.products[0].specifications
        addResponse.responseData.products[0].specifications.name
        addResponse.responseData.products[0].specifications.value
        addResponse.responseData.products[0].packingInfo
        addResponse.responseData.products[0].packingInfo.weight
        addResponse.responseData.products[0].packingInfo.dimensions
        addResponse.responseData.products[0].packingInfo.dimensions.weight
        addResponse.responseData.products[0].packingInfo.dimensions.height
        addResponse.responseData.products[0].packingInfo.dimensions.depth

        when:'Get Product Details'
        def getResponse = productHelper.getProduct(null,productId)

        then: 'Checking the response code for Get Product details'
        getResponse
        getResponse.responseBase.h.original.code == HttpStatus.FOUND.value()
        addResponse.responseData.products[0].id
        addResponse.responseData.products[0].id == productId
        addResponse.responseData.products[0].productDisplayName
        addResponse.responseData.products[0].productDisplayName == productReq.productDisplayName
        addResponse.responseData.products[0].description
        addResponse.responseData.products[0].description == productReq.description
        addResponse.responseData.products[0].price
        addResponse.responseData.products[0].price == productReq.price
        addResponse.responseData.products[0].specifications
        addResponse.responseData.products[0].specifications.name
        addResponse.responseData.products[0].specifications.name == specifications.name
        addResponse.responseData.products[0].specifications.value
        addResponse.responseData.products[0].specifications.value == specifications.value
        addResponse.responseData.products[0].packingInfo
        addResponse.responseData.products[0].packingInfo.weight
        addResponse.responseData.products[0].packingInfo.weight == packingInfo.weight
        addResponse.responseData.products[0].packingInfo.dimensions
        addResponse.responseData.products[0].packingInfo.dimensions.weight
        addResponse.responseData.products[0].packingInfo.dimensions.weight == dimensions.weight
        addResponse.responseData.products[0].packingInfo.dimensions.height
        addResponse.responseData.products[0].packingInfo.dimensions.height ==  dimensions.height
        addResponse.responseData.products[0].packingInfo.dimensions.depth
        addResponse.responseData.products[0].packingInfo.dimensions.depth == dimensions.depth


        when:'Update Product Details'
        productReq.id = productId
        productReq.productDisplayName = 'updateProduct'
        productReq.description = 'updateDescription'
        def updateResponse = productHelper.updateProduct(null,productReq)

        then: 'Checking the response code for Get Product details'
        updateResponse
        updateResponse.responseBase.h.original.code == HttpStatus.OK.value()
        updateResponse.responseData.productDisplayName == updatedDisplayName
        updateResponse.responseData.id
        updateResponse.responseData.id == productId
        updateResponse.responseData.productDisplayName
        updateResponse.responseData.productDisplayName == 'updateProduct'
        updateResponse.responseData.description
        updateResponse.responseData.description == 'updateDescription'
        updateResponse.responseData.price
        updateResponse.responseData.price == productReq.price
        updateResponse.responseData.specifications
        updateResponse.responseData.specifications.name
        updateResponse.responseData.specifications.name == specifications.name
        updateResponse.responseData.specifications.value
        updateResponse.responseData.specifications.value == specifications.value
        updateResponse.responseData.packingInfo
        updateResponse.responseData.packingInfo.weight
        updateResponse.responseData.packingInfo.weight == packingInfo.weight
        updateResponse.responseData.packingInfo.dimensions
        updateResponse.responseData.packingInfo.dimensions.weight
        updateResponse.responseData.packingInfo.dimensions.weight == dimensions.weight
        updateResponse.responseData.packingInfo.dimensions.height
        updateResponse.responseData.packingInfo.dimensions.height ==  dimensions.height
        updateResponse.responseData.packingInfo.dimensions.depth
        updateResponse.responseData.packingInfo.dimensions.depth == dimensions.depth

        when: 'Search All Products'
        def searchResponse = productHelper.searchAllProducts(null)

        then: 'Checking the response code of search product details'
        searchResponse
        searchResponse.responseData.products.size>0

        when:'Search product by name with likeSearch'
        def searchByNameResponse = productHelper.searchByName(null,updatedDisplayName,true)

        then: 'Checking the response code of search product details with likeSearch'
        searchByNameResponse
        searchByNameResponse.responseData.products.size>0
        searchByNameResponse.responseData.products[0].id
        searchByNameResponse.responseData.products[0].id == productId
        searchByNameResponse.responseData.products[0].productDisplayName
        searchByNameResponse.responseData.products[0].productDisplayName == productReq.productDisplayName
        searchByNameResponse.responseData.products[0].description
        searchByNameResponse.responseData.products[0].description == productReq.description
        searchByNameResponse.responseData.products[0].price
        searchByNameResponse.responseData.products[0].price == productReq.price
        searchByNameResponse.responseData.products[0].specifications
        searchByNameResponse.responseData.products[0].specifications.name
        searchByNameResponse.responseData.products[0].specifications.name == specifications.name
        searchByNameResponse.responseData.products[0].specifications.value
        searchByNameResponse.responseData.products[0].specifications.value == specifications.value
        searchByNameResponse.responseData.products[0].packingInfo
        searchByNameResponse.responseData.products[0].packingInfo.weight
        searchByNameResponse.responseData.products[0].packingInfo.weight == packingInfo.weight
        searchByNameResponse.responseData.products[0].packingInfo.dimensions
        searchByNameResponse.responseData.products[0].packingInfo.dimensions.weight
        searchByNameResponse.responseData.products[0].packingInfo.dimensions.weight == dimensions.weight
        searchByNameResponse.responseData.products[0].packingInfo.dimensions.height
        searchByNameResponse.responseData.products[0].packingInfo.dimensions.height ==  dimensions.height
        searchByNameResponse.responseData.products[0].packingInfo.dimensions.depth
        searchByNameResponse.responseData.products[0].packingInfo.dimensions.depth == dimensions.depth



        when:'Search product by exact name'
        def searchByExactNameResponse = productHelper.searchByName(null,updatedDisplayName,false)

        then: 'Checking the response code of search product details with exact name'
        searchByExactNameResponse
        searchByExactNameResponse.responseData.products.size == 1


        cleanup: 'Cancelling product'
        productHelper.cancelProduct(null, productId)
        def afterCleanupResponse = productHelper.getProduct(null,productId)
        afterCleanupResponse.responseBase.h.original.code == HttpStatus.NOT_FOUND.value()
    }
}
