package controller

import com.oms.inventory.controller.InventoryController
import com.oms.inventory.model.InventoryProduct
import com.oms.inventory.model.InventoryRequest
import com.oms.inventory.model.InventoryResponse
import com.oms.inventory.model.InventoryUpdate
import com.oms.inventory.service.InventoryServiceImpl
import spock.lang.Specification

class InventoryControllerSpec extends Specification {
    InventoryServiceImpl mockInventoryService
    InventoryRequest mockInventoryRequest

    def setup() {
        mockInventoryService = Mock InventoryServiceImpl
        mockInventoryRequest = Mock InventoryRequest
    }

    def '/add - invoke the /add controller'() {
        given:
        InventoryController inventoryController = new InventoryController(mockInventoryService)

        InventoryResponse inventoryResponse = new InventoryResponse([new InventoryProduct(id: 'mockProdId',
                count: 10, threshold: 100, productDisplayName: 'mockProd', description: 'prodDesc')])

        when:
        InventoryResponse inventoryResp = inventoryController.addAvailableProduct(mockInventoryRequest)

        then:
        1 * mockInventoryService.addAvailableProductToInventory(mockInventoryRequest) >> inventoryResponse
        inventoryResp.getInventoryResponse()[0].count == 10
        inventoryResp.getInventoryResponse()[0].threshold == 100
        inventoryResp.getInventoryResponse()[0].id == 'mockProdId'
    }

    def '/{productId}/count - get the count of the product from inventory'() {
        given:
        InventoryController inventoryController = new InventoryController(mockInventoryService)

        when:
        int count = inventoryController.getProductCount('mockProdId')

        then:
        mockInventoryService.getAvailableProductCountFromInventory('mockProdId') >> 10
    }

    def '/products/delete - deleteProductFromInventory'(){
        given:
        InventoryController inventoryController = new InventoryController(mockInventoryService)
        List<String> prodIds =['id1', 'id2']

        when:
        inventoryController.deleteProductFromInventory(prodIds)

        then:
        1 * mockInventoryService.deleteProductFromInventory(prodIds)
    }

    def '/inventory/{update} - update product from inventory'(){
        given:
        InventoryController inventoryController = new InventoryController(mockInventoryService)
        List<InventoryUpdate> updateList = [new InventoryUpdate(productId: 'prod1', threshold: 100,
        count: 10), new InventoryUpdate(productId: 'prod2',threshold: 200, count: 30)]

        when:
        inventoryController.updateProductFromInventory(updateList)

        then:
        1 * mockInventoryService.updateInventory(updateList)
    }
}