package com.oms.product.controller

import com.fasterxml.jackson.databind.ObjectMapper
import com.oms.product.model.request.ProductRequest
import com.oms.product.model.response.ProductResponse
import com.oms.product.service.ProductService
import org.springframework.http.MediaType
import org.springframework.test.web.servlet.MockMvc
import spock.lang.Specification
import spock.lang.Unroll

import static org.springframework.http.HttpStatus.CREATED
import static org.springframework.http.HttpStatus.FOUND
import static org.springframework.http.HttpStatus.GONE
import static org.springframework.http.HttpStatus.OK
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get

import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup

class ProductControllerSpec extends Specification {
    ProductController subject
    ProductService productService
    MockMvc mockMvc

    def setup() {
        productService = Mock ProductService
        subject = new ProductController(productService)
        mockMvc = standaloneSetup(subject).build()
    }

    def 'Add product(s) delegates to service'() {
        given:
        def id = 'testId'
        def auditDateValue = new Date()
        def specifications = [name: 'specName', value: 'specVal']
        def dimensions = [weight: 9.5, height: 2.5, depth: 1.5]
        def packingInfo = [weight: 6.5, dimensions: dimensions]
        def productReq = [productDisplayName: 'testProduct', description: 'testDescription', price: 10.50,
                       specifications    : specifications, packingInfo: packingInfo,
                       createdDate       : auditDateValue, lastModifiedDate: auditDateValue]
        def productResp = [id:id,productDisplayName: 'testProduct', description: 'testDescription', price: 10.50,
                          specifications    : specifications, packingInfo: packingInfo,
                          createdDate       : auditDateValue, lastModifiedDate: auditDateValue]
        def productsReq = [productReq]
        ProductRequest productRequest = [products: productsReq]

        def productsResp = [productResp]
        ProductResponse productResponse = [products: productsResp]

        String requestBody = new ObjectMapper().writeValueAsString(productRequest)

        when:
        def response = mockMvc.perform(post("/Product/add")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)).andReturn().response

        then:
        1 * productService.addProduct({
            it
            it.products[0].productDisplayName == 'testProduct'
            it.products[0].description == 'testDescription'
        }) >> productResponse
        response.status == CREATED.value()
    }

    def 'Update product delegates to service'() {
        given:
        def id = 'testId'
        def auditDateValue = new Date()
        def specifications = [name: 'specName', value: 'specVal']
        def dimensions = [weight: 9.5, height: 2.5, depth: 1.5]
        def packingInfo = [weight: 6.5, dimensions: dimensions]
        def product = [id:id,productDisplayName: 'testProduct', description: 'testDescription', price: 10.50,
                          specifications    : specifications, packingInfo: packingInfo,
                          createdDate       : auditDateValue, lastModifiedDate: new Date()]

        String requestBody = new ObjectMapper().writeValueAsString(product)

        when:
        def response = mockMvc.perform(post("/Product/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestBody)).andReturn().response

        then:
        1 * productService.updateProductDetails({
            it
            it.id == id
            it.productDisplayName == 'testProduct'
            it.description == 'testDescription'
        }) >> product
        response.status == OK.value()
    }

    def 'Get product details delegates to service'() {
        given:
        def id = 'testId'
        def auditDateValue = new Date()
        def specifications = [name: 'specName', value: 'specVal']
        def dimensions = [weight: 9.5, height: 2.5, depth: 1.5]
        def packingInfo = [weight: 6.5, dimensions: dimensions]
        def product = [id: id, productDisplayName: 'testProduct', description: 'testDescription', price: 10.50,
                       specifications: specifications, packingInfo: packingInfo,
                       createdDate   : auditDateValue, lastModifiedDate: auditDateValue]
        def products = [product]
        ProductResponse productResponse = [products: products]

        when:
        def response = mockMvc.perform(get("/Product/get/{id}", id)).andReturn().response
        then:
        1 * productService.getProduct(id) >> productResponse
        response.status == FOUND.value()
    }

    def 'Cancel product details delegates to service'() {
        given:
        def id = 'testId'
        when:
        def response = mockMvc.perform(delete("/Product/cancel/{id}", id)).andReturn().response
        then:
        1 * productService.cancelProduct(id)
        response.status == GONE.value()
    }

    def 'Search all product details delegates to service'() {
        given:
        def id = 'testId'
        def auditDateValue = new Date()
        def specifications = [name: 'specName', value: 'specVal']
        def dimensions = [weight: 9.5, height: 2.5, depth: 1.5]
        def packingInfo = [weight: 6.5, dimensions: dimensions]
        def product = [id: id, productDisplayName: 'testProduct', description: 'testDescription', price: 10.50,
                       specifications: specifications, packingInfo: packingInfo,
                       createdDate   : auditDateValue, lastModifiedDate: auditDateValue]
        def products = [product]
        ProductResponse productResponse = [products: products]
        when:
        def response = mockMvc.perform(get("/Product/searchAllProducts")).andReturn().response
        then:
        1 * productService.searchAllProducts() >> productResponse
        response.status == FOUND.value()
    }

    @Unroll
    def 'Search product details by name delegates to service'() {
        given:
        def id = 'testId'
        def auditDateValue = new Date()
        def specifications = [name: 'specName', value: 'specVal']
        def dimensions = [weight: 9.5, height: 2.5, depth: 1.5]
        def packingInfo = [weight: 6.5, dimensions: dimensions]
        def product = [id: id, productDisplayName: 'testProduct', description: 'testDescription', price: 10.50,
                       specifications: specifications, packingInfo: packingInfo,
                       createdDate   : auditDateValue, lastModifiedDate: auditDateValue]
        def products = [product]
        ProductResponse productResponse = [products: products]
        when:
        def response = mockMvc.perform(get("/Product/searchByName/{name}",productName)
                .param("isLike", isLike)).andReturn().response

        then:
        1 * productService.searchProductsByName(productName,isLikeMock) >> productResponse
        response.status == FOUND.value()

        where:
        productName | isLike | isLikeMock
        'BuildProd' | 'true' |  true
        'BuildProd' | 'false'|  false
    }

}
