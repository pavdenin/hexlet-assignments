package exercise.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import java.util.List;

import exercise.repository.ProductRepository;
import exercise.dto.ProductDTO;
import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.ProductMapper;
import exercise.model.Product;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductRepository productRepository;

    // BEGIN
    @Autowired
    private ProductMapper productMapper;

    @GetMapping("")
    public List<ProductDTO> getProductList() {
        var products = productRepository.findAll();

        return products.stream().map(p -> productMapper.toDto(p)).toList();
    }

    @GetMapping("{id}")
    public ProductDTO getProduct(@PathVariable Long id) {
        var product = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("A product with id " + id + "is not found"));

        return productMapper.toDto(product);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO createProduct(@RequestBody ProductCreateDTO productCreateDTO) {
        Product product = productMapper.toEntityForCreate(productCreateDTO);
        return productMapper.toDto(productRepository.save(product));
    }

    @PutMapping("{id}")
    public ProductDTO updateProduct(@PathVariable Long id, @RequestBody ProductUpdateDTO productUpdateDTO) {
        Product product = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("A product with id " + id + "is not found"));
        productMapper.toEntityForUpdate(productUpdateDTO, product);
        return productMapper.toDto(productRepository.save(product));
    }
    // END
}
