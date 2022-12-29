package com.btl.controller;

import com.btl.dto.TransferDTO;
import com.btl.entity.*;
import com.btl.repo.*;
import com.btl.response.SoldHistoryResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:5500"})
@RolesAllowed({"ROLE_ADMIN", "ROLE_SERVICE"})
@RequestMapping("/service")
public class ServiceController {

    @Autowired
    BillRepo billRepo;
    @Autowired
    BatchRepo batchRepo;
    @Autowired
    UserRepo userRepo;
    @Autowired
    ProductRepo productRepo;

    @GetMapping("/allNeedWarranty")
    @ResponseBody
    public List<SoldHistoryResponse> allNeedWarranty(@RequestHeader("Username") String username) {

        long id = userRepo.findByUsername(username).get().getLocationId();

        List<SoldHistoryResponse> res = new ArrayList<>();

        Iterable<Batch> batches = batchRepo.findByStatusAndToId("WARRANTY", id);

        for (Batch batch : batches) {
            SoldHistoryResponse temp = new SoldHistoryResponse();
            BillCustomer billCustomer = billRepo.findByBatchId(batch.getId());

            temp.setCustomerId(billCustomer.getId());
            temp.setBatchId(batch.getId());
            temp.setQuantity(batch.getQuantity());
            temp.setPrice(batch.getPrice());
            temp.setSku(productRepo.findByProductId(batch.getProductId()).getProductSku());
            temp.setCustomerName(billCustomer.getCustomerName());
            temp.setCustomerPhone(billCustomer.getCustomerPhone());
            temp.setWarranty(batch.getWarrantyDate());
            temp.setSoldDate(batch.getDate());
            temp.setCustomerAddress(billCustomer.getCustomerAddress());

            res.add(temp);
        }
        return res;
    }

    @PostMapping("/canWarranty")
    public ResponseEntity<?> canWarranty(@RequestHeader("Username") String username, @RequestBody TransferDTO transferDTO) {

        long id = userRepo.findByUsername(username).get().getLocationId();

        Batch batch = new Batch();

        batch.setFromId(id);
        batch.setToId(-1); // Trả luôn cho khách hàng
        batch.setQuantity(transferDTO.getQuantity());
        batch.setProductId(transferDTO.getProductId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        batch.setDate(date);
        batch.setStatus("DONE_WARRANTY");
        batch.setPrice(0);
        batchRepo.save(batch);

        return null;
    }



}
