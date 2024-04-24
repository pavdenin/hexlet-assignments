package exercise.controller;

import java.util.List;

import exercise.dto.ProductCreateDTO;
import exercise.dto.ProductDTO;
import exercise.dto.ProductUpdateDTO;
import exercise.mapper.ProductMapper;
import exercise.model.Product;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import exercise.exception.ResourceNotFoundException;
import exercise.repository.ProductRepository;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/products")
public class ProductsController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductMapper productMapper;

    // BEGIN


    @GetMapping("")
    public List<ProductDTO> getProductList() {
        List<Product> productList = productRepository.findAll();
        return productList.stream()
        .map(productMapper::map).toList();
    }

    @GetMapping("{id}")
    public ProductDTO getProduct(@PathVariable Long id) {
        var product = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " is not found"));

        return productMapper.map(product);
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ProductDTO newProduct(@Valid @RequestBody ProductCreateDTO newProduct) {

       Product product = productMapper.map(newProduct);
       productRepository.save(product);

       return productMapper.map(product);
    }

    @PutMapping("{id}")
    public ProductDTO updateProduct(@Valid @RequestBody ProductUpdateDTO updatedProduct, @PathVariable Long id) {

        Product product = productRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Product with id " + id + " is not found"));

        productMapper.update(updatedProduct, product);

        productRepository.save(product);

        return productMapper.map(product);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteProduct(@PathVariable Long id) {
        productRepository.deleteById(id);
    }

    // END
}
