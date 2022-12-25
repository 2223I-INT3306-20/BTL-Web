package com.btl.controller;

import com.btl.dto.OptionDTO;
import com.btl.dto.ProductDTO;
import com.btl.entity.OptionGroup;
import com.btl.entity.Options;
import com.btl.entity.Products;
import com.btl.entity.Products;
import com.btl.repo.LocationRepo;
import com.btl.repo.OptionGroupRepo;
import com.btl.repo.OptionRepo;
import com.btl.repo.ProductRepo;
import com.btl.response.ProductResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:5500"})
@RequestMapping("/factory")
public class FactoryController {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    OptionRepo optionRepo;
    @Autowired
    LocationRepo locationRepo;

    @Autowired
    OptionGroupRepo optionGroupRepo;

    @GetMapping("/allProduct")
    @ResponseBody
    public List<ProductResponse> getAllProduct() {
        List<ProductResponse> products = new ArrayList<>();
        Iterable<Products> productsAll = productRepo.findAll();
        for (Products product : productsAll) {
            ProductResponse productResponse = new ProductResponse();
            productResponse.setName(product.getOption().getOptionName());
            productResponse.setInfo(product.getOption().getOptionInfo());
            productResponse.setGiaXuat(100);
            productResponse.setGiaNhap(90);
            productResponse.setSlXuat(90);
            productResponse.setSlNhap(100);

            products.add(productResponse);
        }
        return products;
    }


    /* --------------------------------------- 3 nhiệm vụ đầu -------------------------------------------*/
    @PostMapping("/makeNew")
    public ResponseEntity<?> makeNewProduct(@RequestParam long quantity, @RequestBody ProductDTO productDTO) {
        //sản xuất các sản phẩm mới vào kho

        Products product = productRepo.findByProductSku(productDTO.getProductSku());
        if (product == null) {
            product = new Products();
            product.setProductSku(productDTO.getProductSku());
            product.setProductName(productDTO.getProductName());
            product.setProductPrice(productDTO.getProductPrice());
            product.setProductWeight(productDTO.getProductWeight());
            product.setProductImg(productDTO.getProductImg());
            product.setProductCategoryId(productDTO.getProductCategoryId());
            product.setProductMfg(productDTO.getProductMfg());
            product.setOption(optionRepo.findOptionsByOptionId(productDTO.getOptionId()));
            product.setProductStock(quantity);
            //product.setLocation(locationRepo.findByLocationType("FACTORY"));
        } else {
            product.setProductStock(product.getProductStock() + quantity);
        }

        productRepo.save(product);

        return ResponseEntity.ok("SUCCESS");

    }
    @PostMapping("/toDealer") //chuyển số lượng sản phẩm cho của hàng.
    public ResponseEntity<?> exportToDealer(@RequestParam long productId, @RequestParam long quantity) {
        Products products = productRepo.findByProductId(productId);
        if (quantity > products.getProductStock() || quantity < 0) {
            return ResponseEntity.ok("QUANTITY IS'NT AVAILABLE!");
        } else {
            Products newProduct = products;
            newProduct.setProductStock(quantity);
            products.setProductStock(products.getProductStock() - quantity);
            productRepo.save(newProduct);
            productRepo.save(products);

            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
    }

    @PostMapping("/createNewOption")
    public ResponseEntity<?> createNewOption(@RequestBody OptionDTO optionDTO) {
//        Options options = optionRepo.findByOptionName(optionDTO.getOptionName()).get();
//        if (options != null) {
//            return new ResponseEntity<>("OPTION_AVAILABLE", HttpStatus.BAD_REQUEST);
//        }

        Options newOption = new Options();
        newOption.setBrandName(optionDTO.getBrandName());
        newOption.setResolution(optionDTO.getResolution());
        newOption.setRomType(optionDTO.getRomType());
        newOption.setScreenType(optionDTO.getScreenType());
        newOption.setOptionName(optionDTO.getOptionName());
        newOption.setScreenSize(optionDTO.getScreenSize());
        newOption.setBattery(optionDTO.getBattery());
        newOption.setCpuBrand(optionDTO.getCpuBrand());
        newOption.setCpuName(optionDTO.getCpuName());
        newOption.setRam(optionDTO.getRam());
        newOption.setRom(optionDTO.getRom());
        newOption.setGpu(optionDTO.getGpu());
        newOption.setProducts(null);

        optionRepo.save(newOption);

        return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
    }

    @GetMapping("/allOption")
    @ResponseBody
    public List<Options> allOption() {
        List<Options> options = new ArrayList<>();
        Iterable<Options> allOption = optionRepo.findAll();
        for (Options option : allOption) {
            option.setProducts(null);
            options.add(option);
        }
        return options;
    }

    @GetMapping("/allGroupOption")
    @ResponseBody
    public List<OptionGroup> allGroup() {
        List<OptionGroup> groups = new ArrayList<>();
        Iterable<OptionGroup> allGroup = optionGroupRepo.findAll();
        for (OptionGroup group : allGroup) {
            group.setOptions(null);
            groups.add(group);
        }
        return groups;
    }

    @GetMapping("/getByMFG")
    @ResponseBody
    public List<Products> getByMfg(@RequestParam("from") @DateTimeFormat(pattern = "yyyy-MM-dd") Date mfgDate) {
        List<Products> products = new ArrayList<>();
        Iterable<Products> getProductByMfg = productRepo.findByProductMfg(mfgDate);
        for (Products product : getProductByMfg) {
            products.add(product);
        }
        return products;
    }

    /* Trả về tỷ lệ lỗi của từng loại */
//    @GetMapping("/faultRate")
//    public




    private void importProduct(Products product, long quantity) {
        System.out.println("Nhap thanh cong");
    }

    private void exportToDealer(Products product, long dealerId, long quantity) {
        System.out.println("Xuat den " + dealerId + " thanh cong!");
    }

    private void getFailProduct(Products product, long serviceId, long quantity) {
        System.out.println("Nhap " + quantity + " pham loi tu " + serviceId + " thanh cong");
    }


    /* ---------------- Trả về danh sách sản phẩm theo từng loại ----------------------------*/

    @GetMapping("/getType")
    public RequestEntity<?> getProductByType(@RequestParam String type) {
        return null;
    }


    /*------------------ Số lượng sản phẩm bán ra theo ngày, tháng, năm -----------------------*/
    @GetMapping("/getSold")
    public RequestEntity<?> getSolByDate(@RequestParam String type) {
        return null;
    }


    /*-------------------- Trả về tỷ lệ sản phẩm lỗi -------------------------------------------*/
    @GetMapping("/getFail")
    public RequestEntity<?> getRateFail(@RequestParam long lineId) {
        //Tìm số sản phẩm bị lỗi theo mã id của dòng sản phẩm, tính toán, trả về tỉ lệ , fe dùng biểu đồ đề biểu diễn
        return null;
    }








}
