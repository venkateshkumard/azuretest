package com.oms.product.service;

import com.oms.product.delegate.ProductDelegate;
import com.oms.product.exception.ProductNameExistingException;
import com.oms.product.exception.ProductNotFoundException;
import com.oms.product.model.Dimensions;
import com.oms.product.model.PackingInfo;
import com.oms.product.model.Specifications;
import com.oms.product.model.domain.ProductDTO;
import com.oms.product.model.entity.ProductEntity;
import com.oms.product.model.request.ProductRequest;
import com.oms.product.model.response.ProductResponse;
import com.oms.product.repository.ProductRepository;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private ProductDelegate productDelegate;

    private ProductRepository productRepository;


    public ProductServiceImpl(ProductRepository productRepository,ProductDelegate productDelegate) {
        this.productDelegate = productDelegate;
        this.productRepository = productRepository;
    }


    public ProductResponse addProduct(ProductRequest productRequest) {
        ProductResponse productResponse = new ProductResponse();
        productRequest.getProducts().forEach(productDTO -> {
                    String productName = productDTO.getProductDisplayName();
                    ProductEntity productEntityFound = productRepository.findByProductDisplayName(productName);
                    if (productEntityFound != null) {
                        throw new ProductNameExistingException("Product name is already existing - " + productName);
                    }
                }
        );
        List<ProductEntity> productEntityList = convertToProductEntityList(productRequest.getProducts(), "ADD");
        List<ProductEntity> productEntityListReturned = productRepository.insert(productEntityList);
        productResponse.setProducts(convertToProductDtoList(productEntityListReturned));
        return productResponse;
    }

    public ProductDTO updateProductDetails(ProductDTO productDTO) {
        String productId = productDTO.getId();
        ProductEntity productEntityFound = productRepository.findOne(productId);
        if (productEntityFound == null) {
            throw new ProductNotFoundException("Product is not fund for given product name - " + productId);
        }
        ProductEntity productEntityToBeUpdated = deriveDifferenceToBeUpdated(productDTO, productEntityFound);
        ProductEntity productEntityRetrieved = productRepository.updateProductByName(productEntityToBeUpdated);
        return entityToDomain(productEntityRetrieved);
    }

    private ProductEntity deriveDifferenceToBeUpdated(ProductDTO productDTO, ProductEntity productEntityFound) {
        ProductEntity productEntityToBeUpdated = new ProductEntity();
        productEntityToBeUpdated.setId(productDTO.getId());
        productEntityToBeUpdated.setLastModifiedDate(new Date());
        productEntityToBeUpdated.filedValueIfUpdated(productDTO.getProductDisplayName(), productEntityFound.getProductDisplayName(), productEntityToBeUpdated::setProductDisplayName);
        productEntityToBeUpdated.filedValueIfUpdated(productDTO.getDescription(), productEntityFound.getDescription(), productEntityToBeUpdated::setDescription);
        productEntityToBeUpdated.filedValueIfUpdated(productDTO.getPrice(), productEntityFound.getPrice(), productEntityToBeUpdated::setPrice);
        productEntityToBeUpdated.setSpecifications(deriveDifferenceToBeUpdated(productDTO.getSpecifications(), productEntityFound.getSpecifications()));
        productEntityToBeUpdated.setPackingInfo(deriveDifferenceToBeUpdated(productDTO.getPackingInfo(), productEntityFound.getPackingInfo()));
        return productEntityToBeUpdated;
    }

    private Specifications deriveDifferenceToBeUpdated(Specifications specificationsSource, Specifications specificationsTarget) {
        Specifications specificationsToBeUpdated = new Specifications();
        specificationsToBeUpdated.filedValueIfUpdated(specificationsSource.getName(), specificationsTarget.getName(), specificationsToBeUpdated::setName);
        specificationsToBeUpdated.filedValueIfUpdated(specificationsSource.getValue(), specificationsTarget.getValue(), specificationsToBeUpdated::setValue);
        return specificationsToBeUpdated;
    }

    private PackingInfo deriveDifferenceToBeUpdated(PackingInfo packingInfoSource, PackingInfo packingInfoTarget) {
        PackingInfo packingInfoToBeUpdated = new PackingInfo();
        packingInfoToBeUpdated.filedValueIfUpdated(packingInfoSource.getWeight(), packingInfoTarget.getWeight(), packingInfoToBeUpdated::setWeight);
        packingInfoToBeUpdated.setDimensions(deriveDifferenceToBeUpdated(packingInfoSource.getDimensions(), packingInfoTarget.getDimensions()));
        return packingInfoToBeUpdated;
    }

    private Dimensions deriveDifferenceToBeUpdated(Dimensions dimensionsSource, Dimensions dimensionsTarget) {
        Dimensions dimensionsToBeUpdated = new Dimensions();
        dimensionsToBeUpdated.filedValueIfUpdated(dimensionsSource.getWeight(), dimensionsTarget.getWeight(), dimensionsToBeUpdated::setWeight);
        dimensionsToBeUpdated.filedValueIfUpdated(dimensionsSource.getHeight(), dimensionsTarget.getHeight(), dimensionsToBeUpdated::setHeight);
        dimensionsToBeUpdated.filedValueIfUpdated(dimensionsSource.getDepth(), dimensionsTarget.getDepth(), dimensionsToBeUpdated::setDepth);
        return dimensionsToBeUpdated;
    }

    public ProductResponse getProduct(String id) {
        ProductResponse productResponse = new ProductResponse();
        List<ProductDTO> products = new ArrayList<ProductDTO>();
        ProductEntity productEntity = productRepository.findOne(id);
        if (productEntity != null) {
            ProductDTO productDTO = entityToDomain(productEntity);
            products.add(productDTO);
            productResponse.setProducts(products);
        } else {
            throw new ProductNotFoundException("Product Not Found For Product Id - " + id);
        }
        return productResponse;
    }

    public void cancelProduct(String id) {
        productRepository.delete(id);
        productDelegate.deleteProductFromInventory(id);
    }

    public ProductResponse searchAllProducts() {
        LOGGER.info("message={}", "searchAllProducts");
        ProductResponse productResponse = new ProductResponse();
        List<ProductEntity> productEntityListReturned = productRepository.findAll();
        productResponse.setProducts(convertToProductDtoList(productEntityListReturned));
        return productResponse;
    }

    public ProductResponse searchProductsByName(String name, boolean isLike) {
        ProductResponse productResponse = new ProductResponse();
        ProductEntity productEntityReturned;
        List<ProductEntity> productEntityList;
        if (!isLike) {
            productEntityReturned = productRepository.findByProductDisplayName(name);
            if (productEntityReturned != null) {
                List<ProductDTO> products = new ArrayList<ProductDTO>();
                products.add(entityToDomain(productEntityReturned));
                productResponse.setProducts(products);
            } else {
                throw new ProductNotFoundException("Product(s) not found for the given name -" + name);
            }
        } else {
            productEntityList = productRepository.findByProductDisplayNameIsLike(name);
            if (CollectionUtils.isNotEmpty(productEntityList)) {
                productResponse.setProducts(convertToProductDtoList(productEntityList));
            } else {
                throw new ProductNotFoundException("Product(s) not found for the given name -" + name);
            }
        }
        return productResponse;
    }


    private List<ProductEntity> convertToProductEntityList(List<ProductDTO> productDTOList, String operation) {
        List<ProductEntity> productEntityList = new ArrayList<ProductEntity>();
        productDTOList.forEach(productDTO -> {
            productEntityList.add(domainToEntity(productDTO, operation));
        });
        return productEntityList;
    }

    private List<ProductDTO> convertToProductDtoList(List<ProductEntity> productEntityListReturned) {
        List<ProductDTO> productDTOList = new ArrayList<ProductDTO>();
        if (CollectionUtils.isNotEmpty(productEntityListReturned)) {
            productEntityListReturned.forEach(productEntity -> {
                productDTOList.add(entityToDomain(productEntity));
            });
        }
        return productDTOList;
    }

    private ProductEntity domainToEntity(ProductDTO productDTO, String Operation) {
        return new ProductEntity().setId(productDTO.getId())
                .setProductDisplayName(productDTO.getProductDisplayName())
                .setDescription(productDTO.getDescription())
                .setCreatedDate(new Date())
                .setLastModifiedDate(new Date())
                .setPrice(productDTO.getPrice())
                .setPackingInfo(productDTO.getPackingInfo())
                .setSpecifications(productDTO.getSpecifications());
    }

    private ProductDTO entityToDomain(ProductEntity productEntity) {
        return new ProductDTO().setId(productEntity.getId())
                .setProductDisplayName(productEntity.getProductDisplayName())
                .setDescription(productEntity.getDescription())
                .setCreatedDate(productEntity.getCreatedDate())
                .setLastModifiedDate(productEntity.getLastModifiedDate())
                .setPrice(productEntity.getPrice())
                .setPackingInfo(productEntity.getPackingInfo())
                .setSpecifications(productEntity.getSpecifications());
    }

}
