package com.btl.controller;

import com.btl.entity.Products;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/api")
public class DealerController {

    /*---------------------- Nhập các sản phẩm mới sản xuất về kho riêng của Đại lý --------------------*/
    @PostMapping("/takeIntoStored")
    public void takeGoodsIntoStored(/*Đầu vào gổm: sản phẩm, factory id, số lượng, store id*/) {
        //Xử lý thêm vào DB của kho
    }

    /*----------------------- Bán sản phẩm cho khách hàng ----------------------------------------------*/
    @GetMapping("/sell")
    public RequestEntity<?> sellProduct() {
        if (/*check số lượng sản phẩm còn trong */true) {

        } else {

        }

        return null;
    }

    /*--------------------------*/


}