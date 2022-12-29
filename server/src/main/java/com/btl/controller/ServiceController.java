package com.btl.controller;

import com.btl.dto.TransferDTO;
import com.btl.entity.*;
import com.btl.repo.*;
import com.btl.response.SoldHistoryResponse;
import com.btl.response.WarrantyResponse;
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

    @Autowired
    FaultRepo faultRepo;

    @GetMapping("/allNeedWarranty")
    @ResponseBody
    public List<SoldHistoryResponse> allNeedWarranty(@RequestHeader("Username") String username) {

        long id = userRepo.findByUsername(username).get().getLocationId();

        List<SoldHistoryResponse> res = new ArrayList<>();

        Iterable<Fault> faults = faultRepo.findByServiceIdAndStatus(id, "WARRANTY");
        List<Batch> batches = new ArrayList<>();
        for (Fault fault : faults) {
            Batch temp = batchRepo.findById(fault.getBatchId()).get();
            batches.add(temp);
        }

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
            temp.setWarranty(faultRepo.findByBatchId(batch.getId()).getReceiveDate());
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
        batch.setProductId(batchRepo.findById(transferDTO.getProductId()).get().getProductId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        batch.setDate(date);
        batch.setStatus("DONE");
        batch.setPrice(0);
        batchRepo.save(batch);

        Date now = new Date(System.currentTimeMillis());

        Fault done =  faultRepo.findByBatchId(transferDTO.getProductId()); // dùng productId thay thế cho batchId
        done.setStatus("DONE");
        done.setPassDate(now);

        faultRepo.save(done);

        return null;
    }

    @PostMapping("/cantWarranty")
    public ResponseEntity<?> cantWarranty(@RequestHeader("Username") String username, @RequestBody TransferDTO transferDTO) {

        long id = userRepo.findByUsername(username).get().getLocationId();

        Batch batch = new Batch();

        batch.setFromId(id);
        batch.setToId(0); // Trả luôn cho cssx
        batch.setQuantity(transferDTO.getQuantity());
        batch.setProductId(transferDTO.getProductId());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date(System.currentTimeMillis());
        batch.setDate(date);
        batch.setStatus("CANT");
        batch.setPrice(0);
        batchRepo.save(batch);

        Date now = new Date(System.currentTimeMillis());

        Fault done =  faultRepo.findByBatchId(transferDTO.getProductId()); // dùng productId thay thế cho batchId
        done.setStatus("CANT");
        done.setPassDate(now);

        faultRepo.save(done);

        return null;
    }

    @GetMapping("/doneList")
    @ResponseBody
    public List<WarrantyResponse> listWarranty(@RequestHeader("Username") String username) {
        return findList(username, 0);
    }

    @GetMapping("/cantList")
    @ResponseBody
    public List<WarrantyResponse> listCantWarranty(@RequestHeader("Username") String username) {
        return findList(username, 1);
    }

    private List<WarrantyResponse> findList(String username, int code) {
        long id = userRepo.findByUsername(username).get().getLocationId();
        String toCode;
        if (code == 0) {
            toCode = "DONE";
        } else {
            toCode = "CANT";
        }
        List<WarrantyResponse> res = new ArrayList<>();
        List<Fault> done = faultRepo.findByServiceIdAndStatus(id, toCode);
        //List<Fault> cant = faultRepo.findByServiceIdAndStatus(id, "CANT");

        for (Fault fault : done) {
            BillCustomer billCustomer = billRepo.findByBatchId(fault.getBatchId());
            WarrantyResponse temp = new WarrantyResponse();
            temp.setPassDate(fault.getPassDate());
            temp.setReceiveDate(fault.getReceiveDate());
            temp.setCustomerPhone(billCustomer.getCustomerPhone());
            temp.setCustomerName(billCustomer.getCustomerName());
            temp.setCustomerAddress(billCustomer.getCustomerAddress());
            temp.setBatchId(fault.getBatchId());

            res.add(temp);
        }
        return res;
    }

}
