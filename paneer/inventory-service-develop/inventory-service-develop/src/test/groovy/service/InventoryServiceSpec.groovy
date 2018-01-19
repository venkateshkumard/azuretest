package service

import com.oms.inventory.controller.InventoryController
import com.oms.inventory.exception.InventoryProductNotFoundException
import com.oms.inventory.model.InventoryItem
import com.oms.inventory.model.InventoryProduct
import com.oms.inventory.model.InventoryRequest
import com.oms.inventory.model.InventoryResponse
import com.oms.inventory.model.InventoryUpdate
import com.oms.inventory.repository.InventoryRepository
import com.oms.inventory.repository.InventoryRepositoryCustom
import com.oms.inventory.service.InventoryServiceImpl
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Specification

class InventoryServiceSpec extends Specification{
    InventoryServiceImpl inventoryService
    InventoryRepository mockInventoryRepository
    InventoryRequest mockInventoryRequest
    InventoryRepositoryCustom mockInventoryRepositoryCustom

    def setup() {
        mockInventoryRequest = Mock InventoryRequest
        mockInventoryRepositoryCustom = Mock InventoryRepositoryCustom
        mockInventoryRepository = Mock InventoryRepository
        inventoryService = new InventoryServiceImpl(mockInventoryRepository)
        ReflectionTestUtils.setField(inventoryService,'inventoryRepositoryCustom',mockInventoryRepositoryCustom)

    }

    def '/add - invoke the /add service'() {
        given:
        InventoryController inventoryController = new InventoryController(inventoryService)
        List<InventoryItem> inventoryItemList = [new InventoryItem(productId: 'prodId', threshold: 100, count: 10),
                                                 new InventoryItem(productId: 'prodId1', threshold: 200, count: 20)]
        InventoryRequest request = new InventoryRequest(inventoryItemList : inventoryItemList)
        List<InventoryProduct> inventoryProductList = [new InventoryProduct(id: 'prodId',count: 10, threshold: 100),
                                                       new InventoryProduct(id: 'prodId1',count: 20, threshold: 200)]

        when:
        InventoryResponse inventoryResp = inventoryController.addAvailableProduct(request)

        then:
        2 * mockInventoryRepositoryCustom.findAllInventoryProducts(['prodId','prodId1']) >> inventoryProductList
        inventoryResp.inventoryProducts.size() == 2
        inventoryResp.inventoryProducts[0].count == 10
        inventoryResp.inventoryProducts[1].count == 20
        inventoryResp.inventoryProducts[0].threshold == 100
        inventoryResp.inventoryProducts[1].threshold == 200
    }

    def '/{productId}/count - get the product count'() {
        given:
        InventoryController inventoryController = new InventoryController(inventoryService)
        InventoryProduct inventoryProduct = new InventoryProduct(id: 'prod1', count: 10, threshold:
        100)

        when:
        int productCount = inventoryController.getProductCount('prodId')

        then:
        1 * mockInventoryRepository.findOne('prodId') >> inventoryProduct
        productCount == 10
    }

    def '/products/delete - delete the products from inventory'() {
        given:
        InventoryController inventoryController = new InventoryController(inventoryService)

        when:
        inventoryController.deleteProductFromInventory(['prodId','prodId1'])

        then:
        1 * mockInventoryRepositoryCustom.deleteProductsFromInventory(['prodId','prodId1']);
    }

    def '/inventory/update - Update the product in the inventory'() {
        given:
        InventoryController inventoryController = new InventoryController(inventoryService)
        List<InventoryUpdate> inventoryUpdateList = [new InventoryUpdate(productId: 'prodId', count: 121,threshold: 102)]

        when:
        inventoryController.updateProductFromInventory(inventoryUpdateList)

        then:
        1 * mockInventoryRepositoryCustom.updateProductsInInventory(inventoryUpdateList);
    }

    def 'Adding a product not in product catalog to the inventory throws InventoryProductNotFoundException'(){
        given:
        InventoryController inventoryController = new InventoryController(inventoryService)
        List<InventoryItem> inventoryItemList = [new InventoryItem(productId: 'prodId', threshold: 100, count: 10),
                                                 new InventoryItem(productId: 'prodId1', threshold: 200, count: 20)]
        InventoryRequest request = new InventoryRequest(inventoryItemList : inventoryItemList)
        List<InventoryProduct> inventoryProductList = [new InventoryProduct(id: 'prodId',count: 10, threshold: 100)]

        when:
        inventoryController.addAvailableProduct(request)

        then:
        1 * mockInventoryRepositoryCustom.findAllInventoryProducts(['prodId','prodId1']) >> inventoryProductList
        thrown(InventoryProductNotFoundException.class)
    }

}
