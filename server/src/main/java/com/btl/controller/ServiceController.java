package com.btl.controller;

import com.btl.entity.Fault;
import com.btl.entity.User;
import com.btl.repo.FaultRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:5500"})
@RolesAllowed({"ROLE_ADMIN", "ROLE_SERVICE"})
@RequestMapping("/service")
public class ServiceController {


    @Autowired
    private FaultRepo faultRepo;

    /*Hoàn thành bảo hành và trả về cho cơ sở sản xuất */
    @PostMapping("/doneWarranty")
    public ResponseEntity<?> doneWarranty(@RequestBody Fault fault) {
        return null;
    }

    /*Trả các sản phẩn không thể bảo hành về cho cơ sở sản xuất*/
    @PostMapping("/cantWarranty")
    public ResponseEntity<?> cantWarranty(@RequestBody Fault fault) {
        return null;
    }

    /*Thống kê sản phẩm lỗi theo từng loại */
    /*Theo ngày*/
    @GetMapping("/getStatisticByDate")
    public ResponseEntity<?> getStatisticByDate(@RequestParam String type, @RequestParam String date) {

        List<Fault> res = new ArrayList<>();
        Iterable<Fault> allFault = faultRepo.findAll();
        if (type.equals("year")) {
            // theo năm
            for (Fault fault : allFault) {

            }
        } else if (type.equals("month")) {
            //theo tháng
        } else {
            //theo ngày
        }
        return null;
    }



}
