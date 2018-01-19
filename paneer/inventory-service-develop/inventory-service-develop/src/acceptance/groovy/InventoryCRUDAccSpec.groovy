import com.oms.inventory.model.InventoryItem
import com.oms.inventory.model.InventoryProduct
import com.oms.inventory.model.InventoryRequest
import com.oms.inventory.model.InventoryUpdate
import groovyx.net.http.HttpResponseDecorator
import groovyx.net.http.RESTClient
import spock.lang.Shared
import spock.lang.Specification

class InventoryCRUDAccSpec extends Specification {

    @Shared
    RESTClient restClient

    @Shared
    String localhost = new AcceptanceSpecConfiguration().host

    def setupSpec() {
        restClient = new RESTClient(localhost)
    }

    def '/inventory/* - add the products into the inventory and invoke all CRUD endpoints'() {
        given:
        def inventoryProductList = [
                new InventoryProduct(id: '59ccdbbf8b37e93ba0d01261', count: 0, productDisplayName: 'Product1', description: 'Sample Product', price: 100, threshold: 0 ),
                new InventoryProduct(id: '59ccdbbf8b37e93ba0d01262', count: 0, productDisplayName: 'Product2', description: 'Sample Product2',price: 200, threshold: 0 )]
        def productList = ['59ccdbbf8b37e93ba0d01261','59ccdbbf8b37e93ba0d01262']

        when:
        HttpResponseDecorator addProdResponse = restClient.post(
                path: "/inventory/addProduct",
                contentType: 'application/json',
                body: inventoryProductList)

        then:
        addProdResponse.status ==200
        addProdResponse.responseData[0].count == 0
        addProdResponse.responseData[1].count == 0

        when:
        def inventoryItemList = new InventoryRequest(inventoryItemList: [new InventoryItem(productId: '59ccdbbf8b37e93ba0d01261',threshold: 100, count: 5),
                                                                         new InventoryItem(productId: '59ccdbbf8b37e93ba0d01262',threshold: 200, count: 6)])
        addProdResponse = restClient.post(
                path: "/inventory/add",
                contentType: 'application/json',
                body: inventoryItemList)

        then:
        addProdResponse.status == 200
        addProdResponse.responseData.inventoryResponse[1].count == 6
        addProdResponse.responseData.inventoryResponse[1].threshold == 200
        addProdResponse.responseData.inventoryResponse[0].threshold == 100
        addProdResponse.responseData.inventoryResponse[0].count == 5

        when:'Queried with the /inventory/{productId}/count'
        addProdResponse = restClient.
                          get(path: "/inventory/59ccdbbf8b37e93ba0d01261/count",
                          contentType: 'application/json')

        then:'returns the number of product count available.'
        addProdResponse.status == 200
        addProdResponse.responseData == 5

        when:'Called with updated API PATCH - /inventory/update'
        List<InventoryUpdate> inventoryUpdateList =
                    [new InventoryUpdate(productId:  '59ccdbbf8b37e93ba0d01261',threshold: 1000, count: 100),
                     new InventoryUpdate(productId:  '59ccdbbf8b37e93ba0d01262',threshold: 2000, count: 200)]
        addProdResponse = restClient.
                patch(path: "/inventory/update",
                        contentType: 'application/json',
                        body: inventoryUpdateList)
        then:
        addProdResponse.status == 200

        when:'Queried with the /inventory/{productId}/count for the updated count'
        addProdResponse = restClient.
                get(path: "/inventory/59ccdbbf8b37e93ba0d01262/count",
                        contentType: 'application/json')

        then:'returns the number of product count that is updated.'
        addProdResponse.status == 200
        addProdResponse.responseData == 200

        when:'called with /inventory/products/delete, it will reset the product available count to zero'
        List<String> prodIdList = ['59ccdbbf8b37e93ba0d01262','59ccdbbf8b37e93ba0d01261']
        addProdResponse = restClient.
                post(path: "/inventory/products/delete",
                        contentType: 'application/json',
                        body: prodIdList)
        then:
        addProdResponse.status == 200

        when:'queried with the /inventory/{productId}/count for the deleted items'
        addProdResponse = restClient.
                get(path: "/inventory/59ccdbbf8b37e93ba0d01262/count",
                        contentType: 'application/json')

        then:'returns the count as zero'
        addProdResponse.status == 200
        addProdResponse.responseData == 0

        cleanup:
        restClient.post(
                path: "/inventory/deleteProductRecord",
                contentType: 'application/json',
                body: productList)
    }
}
