package com.oms.product.service

import com.oms.product.delegate.ProductDelegate
import com.oms.product.model.Dimensions
import com.oms.product.model.PackingInfo
import com.oms.product.model.Specifications
import com.oms.product.model.domain.ProductDTO
import com.oms.product.model.entity.ProductEntity
import com.oms.product.model.request.ProductRequest
import com.oms.product.model.response.ProductResponse
import com.oms.product.repository.ProductRepository
import spock.lang.Specification
import spock.lang.Unroll

class ProductServiceSpec extends Specification {

    ProductService subject

    ProductDelegate productDelegate

    ProductRepository productRepository

    def setup() {
        productDelegate = Mock ProductDelegate
        productRepository = Mock ProductRepository
        subject = new ProductServiceImpl(productRepository,productDelegate)
    }


    def 'Add product(s)'() {
        given:
        def id = 'testId'
        def productDisplayName = 'testProduct'
        def auditDateValue = new Date()
        Specifications specifications = [name: 'specName', value: 'specVal']
        Dimensions dimensions = [weight: 9.5, height: 2.5, depth: 1.5]
        PackingInfo packingInfo = [weight: 6.5, dimensions: dimensions]
        ProductDTO productReq = [productDisplayName: productDisplayName, description: 'testDescription', price: 10.50,
                                 specifications    : specifications, packingInfo: packingInfo,
                                 createdDate       : auditDateValue, lastModifiedDate: auditDateValue]
        List<ProductDTO> products = new ArrayList<ProductDTO>()
        products.add(productReq)
        ProductRequest productRequest = [products: products]

        ProductEntity productEntityResp = [id            : id, productDisplayName: productDisplayName, description: 'testDescription', price: 10.50,
                                           specifications: specifications, packingInfo: packingInfo,
                                           createdDate   : auditDateValue, lastModifiedDate: auditDateValue]
        List<ProductEntity> productEntityRespList = new ArrayList<ProductEntity>()
        productEntityRespList.add(productEntityResp)

        when:
        ProductResponse productResponse = subject.addProduct(productRequest)
        then:
        1 * productRepository.findByProductDisplayName(productDisplayName) >> null
        1 * productRepository.insert({
            it
            it[0].productDisplayName == 'testProduct'
            it[0].description == 'testDescription'
        }) >> productEntityRespList
        productResponse
        productResponse.products.size() == 1
        productResponse.products[0].id == id
        productResponse.products[0].productDisplayName == productDisplayName
        productResponse.products[0].description == 'testDescription'
        productResponse.products[0].price == 10.50
    }

    def 'Update product'() {
        given:
        def id = 'testId'
        def auditDateValueWhileLoading = new Date()
        def auditDateValueWhileUpdating = new Date()
        Specifications specifications = [name: 'specName', value: 'specVal']
        Dimensions dimensions = [weight: 9.5, height: 2.5, depth: 1.5]
        PackingInfo packingInfo = [weight: 6.5, dimensions: dimensions]
        ProductDTO productDTO = [id            : id, productDisplayName: 'testProduct', description: 'testDescription', price: 10.50,
                                 specifications: specifications, packingInfo: packingInfo,
                                 createdDate   : auditDateValueWhileLoading, lastModifiedDate: auditDateValueWhileUpdating]

        ProductEntity productEntityFound = [id         : id, productDisplayName: 'testProd', description: 'testDesc',
                                            price      : 9.50, specifications: specifications, packingInfo: packingInfo,
                                            createdDate: auditDateValueWhileLoading, lastModifiedDate: auditDateValueWhileLoading]

        ProductEntity productEntityReturned = [id            : id, productDisplayName: 'testProduct', description: 'testDescription', price: 10.50,
                                               specifications: specifications, packingInfo: packingInfo,
                                               createdDate   : auditDateValueWhileLoading, lastModifiedDate: auditDateValueWhileUpdating]
        when:
        ProductDTO productDtoResponse = subject.updateProductDetails(productDTO)

        then:
        1 * productRepository.findOne(id) >> productEntityFound
        1 * productRepository.updateProductByName({
            it
            it.productDisplayName == 'testProduct'
            it.description == 'testDescription'
        }) >> productEntityReturned
        productDtoResponse
        productDtoResponse.id == id
        productDtoResponse.productDisplayName == 'testProduct'
        productDtoResponse.description == 'testDescription'
        productDtoResponse.price == 10.50
    }

    def 'Get product details'() {
        given:
        def id = 'testId'
        def auditDateValue = new Date()
        Specifications specifications = [name: 'specName', value: 'specVal']
        Dimensions dimensions = [weight: 9.5, height: 2.5, depth: 1.5]
        PackingInfo packingInfo = [weight: 6.5, dimensions: dimensions]
        ProductEntity productEntity = [id            : id, productDisplayName: 'testProduct', description: 'testDescription', price: 10.50,
                                       specifications: specifications, packingInfo: packingInfo,
                                       createdDate   : auditDateValue, lastModifiedDate: auditDateValue]
        when:
        ProductResponse productResponse = subject.getProduct(id)
        then:
        1 * productRepository.findOne(id) >> productEntity
        productResponse
    }

    def 'Cancel product details'() {
        given:
        def id = 'testId'
        when:
        subject.cancelProduct(id)
        then:
        1 * productRepository.delete(id)
    }

    def 'Search all product details'() {
        given:
        def id = 'testId'
        def auditDateValue = new Date()
        Specifications specifications = [name: 'specName', value: 'specVal']
        Dimensions dimensions = [weight: 9.5, height: 2.5, depth: 1.5]
        PackingInfo packingInfo = [weight: 6.5, dimensions: dimensions]
        ProductEntity productEntity = [id            : id, productDisplayName: 'testProduct', description: 'testDescription', price: 10.50,
                                       specifications: specifications, packingInfo: packingInfo,
                                       createdDate   : auditDateValue, lastModifiedDate: auditDateValue]
        List<ProductEntity> productEntities = new ArrayList<ProductEntity>()
        productEntities.add(productEntity)
        when:
        ProductResponse productResponse = subject.searchAllProducts()
        then:
        1 * productRepository.findAll() >> productEntities
        productResponse
        productResponse.products.size() == 1
        productResponse.products[0].id == id
        productResponse.products[0].productDisplayName == 'testProduct'
        productResponse.products[0].description == 'testDescription'
        productResponse.products[0].price == 10.50
    }

    @Unroll
    def 'Search product details by name'() {
        given:
        def id = 'testId'
        def productDisplayName1 = 'testProduct'
        def auditDateValue = new Date()
        Specifications specifications = [name: 'specName', value: 'specVal']
        Dimensions dimensions = [weight: 9.5, height: 2.5, depth: 1.5]
        PackingInfo packingInfo = [weight: 6.5, dimensions: dimensions]
        ProductEntity productEntity1 = [id            : id, productDisplayName: productDisplayName1, description: 'testDescription', price: 10.50,
                                       specifications: specifications, packingInfo: packingInfo,
                                       createdDate   : auditDateValue, lastModifiedDate: auditDateValue]
        def productDisplayName2 = 'testProduct11'
        ProductEntity productEntity2 = [id            : id, productDisplayName: productDisplayName2, description: 'testDescription', price: 10.50,
                                        specifications: specifications, packingInfo: packingInfo,
                                        createdDate   : auditDateValue, lastModifiedDate: auditDateValue]
        List<ProductEntity> productEntitiesLikeSearch = new ArrayList<ProductEntity>()
        productEntitiesLikeSearch.add(productEntity1)
        productEntitiesLikeSearch.add(productEntity2)
        when:
        ProductResponse productResponse = subject.searchProductsByName(productName,isLike)

        then:
        _ * productRepository.findByProductDisplayName(productName) >> productEntity1
        _ * productRepository.findByProductDisplayNameIsLike(productName) >> productEntitiesLikeSearch
        productResponse
        where:
        productName   | isLike | isLikeMock
        'testProduct' | true   |  true
        'testProduct' | false  |  false
    }
}
