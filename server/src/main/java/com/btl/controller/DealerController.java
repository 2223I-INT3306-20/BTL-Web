package com.btl.controller;

import com.btl.dto.SellDTO;
import com.btl.entity.Products;
import com.btl.entity.Sold;
import com.btl.entity.User;
import com.btl.repo.ProductRepo;
import com.btl.repo.SoldRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:5500"})
@RolesAllowed({"ROLE_ADMIN","ROLE_DEALER"})
@RequestMapping("/dealer")
public class DealerController {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private SoldRepo soldRepo;

    /*---------------------- Nhập các sản phẩm mới sản xuất về kho riêng của Đại lý --------------------*/
    @PostMapping("/takeIntoStored")
    public void takeGoodsIntoStore(/*Đầu vào gổm: sản phẩm, factory id, số lượng, store id*/) {
        //Xử lý thêm vào DB của kho
    }

    /*----------------------- Bán sản phẩm cho khách hàng ----------------------------------------------*/
    @PostMapping("/sellProduct")
    public ResponseEntity<?> sellProduct(@RequestBody SellDTO sellDTO) {

        Products products = productRepo.findByProductId(sellDTO.getProductId());
        long inStock = products.getProductStock();
        if (inStock > sellDTO.getQuantity()) {
            Sold soldProduct = new Sold();
            soldProduct.setDealerId(1);
            soldProduct.setProductId(sellDTO.getProductId());
            soldProduct.setSoldDate(new java.sql.Date(System.currentTimeMillis()));
            soldProduct.setSoldPrice(products.getProductPrice());
            soldProduct.setQuantity(products.getWarranty());
            soldProduct.setQuantity(sellDTO.getQuantity());

            products.setProductStock(products.getProductStock() - sellDTO.getQuantity());

            productRepo.save(products);
            soldRepo.save(soldProduct);

            return ResponseEntity.ok().body("SUCCESS");

        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("QUANTITY IS'NT AVAILABLE!");
        }
    }

    /*------------ Nhập sản phẩm lỗi và chuyển về cho nhà bảo hành--------------*/
    @PostMapping("/guarantee")
    public ResponseEntity getGuarantee(@RequestBody long productId) {
        Sold sold = soldRepo.findByProductId(productId);
        Date todayDate = new Date(System.currentTimeMillis());
        Date soldDate = sold.getSoldDate();
        if (checkValidWarranty(todayDate,soldDate, sold.getWarrantyDate())) {
            //bảo hành
            return ResponseEntity.ok("SUCCESS");

        } else {
            return ResponseEntity.badRequest().body("OUT OF DATE");
        }
    }

    /*Danh sách các sản phẩm còn hạn bảo hành*/
    @PostMapping("/validWarranty")
    @ResponseBody
    public List<Sold> validWarranty() {
        List<Sold> list = new ArrayList<>();
        Iterable<Sold> allSold = soldRepo.findAll();
        Date todayDate = new Date(System.currentTimeMillis());

        for (Sold bill : allSold) {
            if(checkValidWarranty(todayDate, bill.getSoldDate(), bill.getWarrantyDate())) {
                list.add(bill);
            }
        }
        return list;
    }


    /*Hàm tính xem còn hạn bảo hành hay không*/
    private boolean checkValidWarranty(Date from, Date to, int warrantyDate) {
        return true; //
    }
}