package com.btl.controller;

import com.btl.entity.Product;
import org.springframework.http.RequestEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.List;

@RestController
//@RolesAllowed("ROLE_FACTORY")
@RequestMapping("/test")
public class FactoryController {

    /* --------------------------------------- 3 nhiệm vụ đầu -------------------------------------------*/
    @PostMapping("/add")
    public void addProduct(@RequestParam String mtd, @RequestParam long id, @RequestParam long quantity) {
        Product product = new Product();
        if(mtd.equals("in")) {
            //method = in => nhập các sản phẩm mới sản xuất vào
            System.out.println("Nhap sp moi vao kho!");
            importProduct(product, quantity);
        } else if (mtd.equals("out")) {
            //method = out => xuất các sản phẩm đến các đại lý
            System.out.println("Xuat cac sp moi den cac dai ly");
            exportToDealer(product, id, quantity);
        } else if (mtd.equals("fail")){
            //method = fail => Nhập các sản phẩm lỗi từ các trung tâm bảo hành
            System.out.println("Nhap cac san pham loi thanh cong");
            getFailProduct(product, id, quantity);
        }

        //Thêm một sản phẩm mới vào DB với số lượng là quantity (Nhập các lô sản phẩm mới vừa sản xuất vào kho.)
    }

    private void importProduct(Product product, long quantity) {
        System.out.println("Nhap thanh cong");
    }

    private void exportToDealer(Product product, long dealerId, long quantity) {
        System.out.println("Xuat den " + dealerId + " thanh cong!");
    }

    private void getFailProduct(Product product, long serviceId, long quantity) {
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
