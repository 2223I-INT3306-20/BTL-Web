package com.btl.controller;

import com.btl.dto.RequestTransferDTO;
import com.btl.dto.SellDTO;
import com.btl.dto.WarrantyDTO;
import com.btl.entity.*;
import com.btl.repo.*;
import com.btl.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:5500"})
@RolesAllowed({"ROLE_ADMIN", "ROLE_DEALER"})
@RequestMapping("/dealer")
public class DealerController {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    UserRepo userRepo;

    @Autowired
    BatchRepo batchRepo;

    @Autowired
    private LocationRepo locationRepo;

    @Autowired
    private BillRepo billRepo;

    /* Danh sách các sản phẩm có trong kho */
    @GetMapping("/allProductInStore")
    @ResponseBody
    public List<ProductResponse> getAllInStore(@RequestHeader("Username") String username) {
        List<ProductResponse> products = new ArrayList<>();
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> allBatch = batchRepo.findByToId(id);
        Set<Long> listPrd = new HashSet<>();

        for (Batch batch : allBatch) {
            listPrd.add(batch.getProductId());
        }

        for (Long pid : listPrd) {
            ProductResponse productResponse = new ProductResponse();

            productResponse.setProductId(pid);
            productResponse.setName(productRepo.findByProductId(pid).getProductName());
            productResponse.setSku(productRepo.findByProductId(pid).getProductSku());
            productResponse.setInfo(productRepo.findByProductId(pid).getOption().getOptionInfo());
            productResponse.setSlXuat(calcuOut(id, pid));
            productResponse.setSlNhap(calcuIn(id, pid));
            productResponse.setGiaNhap(calcuPrice(id, pid));

            products.add(productResponse);
        }
        return products;
    }

    /*----------------------- Bán sản phẩm cho khách hàng ----------------------------------------------*/
    @PostMapping("/sellProduct")
    public ResponseEntity<?> sellProduct(@RequestHeader("Username") String username, @RequestBody SellDTO sellDTO) {

        Batch batch = new Batch();
        BillCustomer bill = new BillCustomer();

        long fromId = (userRepo.findByUsername(username).get().getLocationId());
        long inStock = calcuAvailable(fromId, sellDTO.getProductId());

        if (sellDTO.getQuantity() > inStock || sellDTO.getQuantity() < 0) {
            return ResponseEntity.badRequest().body("QUANTITY_ISNT_AVAILABLE!");
        } else {
            batch.setFromId(fromId);
            batch.setToId(-1); // To -1 là chuyển cho khách hàng
            batch.setQuantity(sellDTO.getQuantity());
            batch.setProductId(sellDTO.getProductId());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = new java.util.Date(System.currentTimeMillis());
            batch.setDate(date);
            batch.setStatus("SELL");
            batch.setPrice(sellDTO.getPrice());
            Calendar c = Calendar.getInstance();
            c.setTime(date);
            c.add(Calendar.MONTH, sellDTO.getWarranty());
            date = c.getTime();
            batch.setWarrantyDate(date);
            batchRepo.save(batch);

            bill.setBatchId(batch.getId());
            bill.setCustomerName(sellDTO.getCustomerName());
            bill.setCustomerAddress(sellDTO.getCustomerAddress());
            bill.setCustomerPhone(sellDTO.getCustomerPhone());

            billRepo.save(bill);
            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
    }

    /*------------ Nhập sản phẩm lỗi và chuyển về cho trung tam bảo hành--------------*/
    @PostMapping("/recieveWarranty")
    public ResponseEntity getGuarantee(@RequestHeader("Username") String username, @RequestBody WarrantyDTO warrantyDTO) {
        long id = userRepo.findByUsername(username).get().getLocationId();

        Batch recieve = new Batch();
        Batch warranty = new Batch();

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        java.util.Date date = new java.util.Date(System.currentTimeMillis());

        recieve.setFromId(-1);
        recieve.setToId(id);
        recieve.setQuantity(warrantyDTO.getQuantity());
        recieve.setProductId(warrantyDTO.getProductId());
        recieve.setDate(date);
        recieve.setStatus("RECEIVE");
        recieve.setPrice(0);
        batchRepo.save(recieve);

        warranty.setFromId(id);
        warranty.setToId(warrantyDTO.getServiceId());
        warranty.setQuantity(warrantyDTO.getQuantity());
        warranty.setProductId(warrantyDTO.getProductId());
        warranty.setDate(date);
        warranty.setStatus("TO_SERVICE");
        warranty.setPrice(0);
        batchRepo.save(recieve);

        return ResponseEntity.ok("SUCCESS");
    }

    @GetMapping("/validFactory")
    @ResponseBody
    public List<Stored> getListValid() {

        List<Stored> res = new ArrayList<>();
        Iterable<Stored> all = locationRepo.findByLocationType("FACTORY");

        for (Stored stored : all) {
            if (stored.getLocationType().equals("FACTORY")) {
                res.add(stored);
            }
        }

        return res;
    }

    @GetMapping("/validProduct")
    @ResponseBody
    public List<ProductResponse> getValidProduct(@RequestParam("id") String factoryId) {
        List<ProductResponse> products = new ArrayList<>();

        long id = Long.parseLong(factoryId);

        Iterable<Batch> allBatch = batchRepo.findByToId(id);
        Set<Long> listPrd = new HashSet<>();
        for (Batch batch : allBatch) {
            listPrd.add(batch.getProductId());
        }

        System.out.println(listPrd);

        for (Long pid : listPrd) {
            ProductResponse productResponse = new ProductResponse();

            productResponse.setProductId(pid);
            productResponse.setName(productRepo.findByProductId(pid).getOption().getOptionName());
            productResponse.setSku(productRepo.findByProductId(pid).getProductSku());
            productResponse.setInfo(productRepo.findByProductId(pid).getOption().getOptionInfo());
            productResponse.setSlXuat(calcuOut(id, pid));
            productResponse.setSlNhap(calcuIn(id, pid));

            products.add(productResponse);
        }
        return products;
    }

    @GetMapping("/getSellByMonth")
    @ResponseBody
    public ChartResponse getSellByMonth(@RequestHeader("Username") String username) {
        ChartResponse res = new ChartResponse();
        List<String> label = new ArrayList<>();
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> sellBatch = batchRepo.findByStatusAndFromId("SELL", id);
        Set<Long> listPrd = new HashSet<>();

        for (Batch batch : sellBatch) {
            listPrd.add(batch.getProductId());
        }
        List<ComponentResponse> componentResponses = new ArrayList<>();
        for (Long pid : listPrd) {
            ComponentResponse temp = new ComponentResponse();
            temp.setLabel(productRepo.findByProductId(pid).getOption().getOptionName() + " - " + productRepo.findByProductId(pid).getProductSku());

            java.util.Date now = new java.util.Date(System.currentTimeMillis());
            int y = now.getYear();
            List<Long> data = new ArrayList<>();
            for (int m = 0; m < 12; m++) {
                long qtt = 0;
                for (Batch batch : sellBatch) {
                    if (batch.getDate().getMonth() == m && batch.getDate().getYear() == y && batch.getProductId() == pid) {
                        qtt += batch.getQuantity();
                    }
                }
//                if (qtt == 0) {
//                    continue;
//                }
                label.add((m + 1) + " / " + (y + 1900));
                data.add(qtt);
            }
            temp.setData(data);

            componentResponses.add(temp);
        }
        res.setDatasets(componentResponses);
        res.setLabels(label);

        return res;
    }


    @GetMapping("/getSellByQuarter")
    @ResponseBody
    public ChartResponse getSellByQuarter(@RequestHeader("Username") String username) {
        ChartResponse res = new ChartResponse();
        List<String> label = new ArrayList<>();
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> sellBatch = batchRepo.findByStatusAndFromId("SELL", id);
        Set<Long> listPrd = new HashSet<>();

        for (Batch batch : sellBatch) {
            listPrd.add(batch.getProductId());
        }
        List<ComponentResponse> componentResponses = new ArrayList<>();
        for (Long pid : listPrd) {
            ComponentResponse temp = new ComponentResponse();
            temp.setLabel(productRepo.findByProductId(pid).getOption().getOptionName() + " - " + productRepo.findByProductId(pid).getProductSku());

            java.util.Date now = new java.util.Date(System.currentTimeMillis());
            int y = now.getYear();
            List<Long> data = new ArrayList<>();
            long qtt = 0;
            for (int m = 0; m < 12; m++) {
                for (Batch batch : sellBatch) {
                    if (batch.getDate().getMonth() == m && batch.getDate().getYear() == y && batch.getProductId() == pid) {
                        qtt += batch.getQuantity();
                    }
                }
                if (m == 2 || m == 5 || m == 8 || m == 11) {
                    label.add("Quý " + (((int) (m / 3)) + 1) + " - " + (y + 1900));
                    data.add(qtt);
                    qtt = 0;
                }
            }
            temp.setData(data);
            componentResponses.add(temp);
        }
        res.setDatasets(componentResponses);
        res.setLabels(label);

        return res;
    }

    @GetMapping("/getSellByYear")
    @ResponseBody
    public ChartResponse getSellByYear(@RequestHeader("Username") String username) {
        ChartResponse res = new ChartResponse();
        List<String> label = new ArrayList<>();
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> sellBatch = batchRepo.findByStatusAndFromId("SELL", id);
        Set<Long> listPrd = new HashSet<>();

        for (Batch batch : sellBatch) {
            listPrd.add(batch.getProductId());
        }
        List<ComponentResponse> componentResponses = new ArrayList<>();
        for (Long pid : listPrd) {
            ComponentResponse temp = new ComponentResponse();
            temp.setLabel(productRepo.findByProductId(pid).getOption().getOptionName() + " - " + productRepo.findByProductId(pid).getProductSku());

            java.util.Date now = new java.util.Date(System.currentTimeMillis());
            int year = now.getYear();
            List<Long> data = new ArrayList<>();
            long qtt = 0;
            for (int y = year - 5; y <= year; y++) {
                for (int m = 0; m < 12; m++) {
                    for (Batch batch : sellBatch) {
                        if (batch.getDate().getMonth() == m && batch.getDate().getYear() == y && batch.getProductId() == pid) {
                            qtt += batch.getQuantity();
                        }
                    }
                    if (m == 11) {
                        label.add("" + (y + 1900));
                        data.add(qtt);
                        qtt = 0;
                    }
                }
            }
            temp.setData(data);
            componentResponses.add(temp);
        }
        res.setDatasets(componentResponses);
        res.setLabels(label);

        return res;
    }

    @GetMapping("/listSellByMonth")
    @ResponseBody
    public List<StatisticRespone> listSellByMonth(@RequestHeader("Username") String username) {
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> sellBatch = batchRepo.findByStatusAndFromId("SELL", id);
        Set<Long> listPrd = new HashSet<>();

        for (Batch batch : sellBatch) {
            listPrd.add(batch.getProductId());
        }
        List<StatisticRespone> res = new ArrayList<>();
        for (Long pid : listPrd) {

            StatisticRespone temp = new StatisticRespone();
            temp.setName(productRepo.findByProductId(pid).getOption().getOptionName());
            temp.setSku(productRepo.findByProductId(pid).getProductSku());
            temp.setInfo(productRepo.findByProductId(pid).getOption().getOptionInfo());

            java.util.Date now = new java.util.Date(System.currentTimeMillis());
            int y = now.getYear();
            int m = now.getMonth();
            long qtt = 0;
            long price = 0;
            for (Batch batch : sellBatch) {
                if (batch.getDate().getMonth() == m && batch.getDate().getYear() == y && batch.getProductId() == pid) {
                    qtt += batch.getQuantity();
                    price += batch.getPrice() * batch.getQuantity();
                }
            }

            temp.setQuantity(qtt);
            temp.setPrice(price);
            res.add(temp);

        }
        return res;
    }

    @GetMapping("/listSellByQuarter")
    @ResponseBody
    public List<StatisticRespone> listSellByQuarter(@RequestHeader("Username") String username) {
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> sellBatch = batchRepo.findByStatusAndFromId("SELL", id);
        Set<Long> listPrd = new HashSet<>();

        for (Batch batch : sellBatch) {
            listPrd.add(batch.getProductId());
        }
        List<StatisticRespone> res = new ArrayList<>();
        for (Long pid : listPrd) {

            StatisticRespone temp = new StatisticRespone();
            temp.setName(productRepo.findByProductId(pid).getOption().getOptionName());
            temp.setSku(productRepo.findByProductId(pid).getProductSku());
            temp.setInfo(productRepo.findByProductId(pid).getOption().getOptionInfo());

            java.util.Date now = new java.util.Date(System.currentTimeMillis());
            int y = now.getYear();
            int m = now.getMonth();

            int thisQuarter = (int) (m / 3);
            List<Integer> quarter = new ArrayList<>();

            quarter.add(thisQuarter * 3);
            quarter.add(thisQuarter * 3 + 1);
            quarter.add(thisQuarter * 3 + 2);

            long qtt = 0;
            long price = 0;
            for (int month : quarter) {
                for (Batch batch : sellBatch) {
                    if (batch.getDate().getMonth() == month && batch.getDate().getYear() == y && batch.getProductId() == pid) {
                        qtt += batch.getQuantity();
                        price += batch.getPrice() * batch.getQuantity();
                    }
                }
            }

            temp.setQuantity(qtt);
            temp.setPrice(price);
            res.add(temp);

        }
        return res;
    }

    @GetMapping("/listSellByYear")
    @ResponseBody
    public List<StatisticRespone> listSellByYear(@RequestHeader("Username") String username) {
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> sellBatch = batchRepo.findByStatusAndFromId("SELL", id);
        Set<Long> listPrd = new HashSet<>();

        for (Batch batch : sellBatch) {
            listPrd.add(batch.getProductId());
        }
        List<StatisticRespone> res = new ArrayList<>();
        for (Long pid : listPrd) {

            StatisticRespone temp = new StatisticRespone();
            temp.setName(productRepo.findByProductId(pid).getOption().getOptionName());
            temp.setSku(productRepo.findByProductId(pid).getProductSku());
            temp.setInfo(productRepo.findByProductId(pid).getOption().getOptionInfo());

            java.util.Date now = new java.util.Date(System.currentTimeMillis());
            int y = now.getYear();
            long qtt = 0;
            long price = 0;

            for (Batch batch : sellBatch) {
                if (batch.getDate().getYear() == y && batch.getProductId() == pid) {
                    qtt += batch.getQuantity();
                    price += batch.getPrice() * batch.getQuantity();
                }
            }

            temp.setQuantity(qtt);
            temp.setPrice(price);
            res.add(temp);

        }
        return res;
    }


    private long calcuAvailable(long id, long prdId) {
        return calcuIn(id, prdId) - calcuOut(id, prdId);
    }

    private long calcuOut(long id, long prdId) {
        long out = 0;
        List<Batch> productOut = batchRepo.findByFromIdAndProductId(id, prdId);

        for (int i = 0; i < productOut.size(); i++) {
            out += productOut.get(i).getQuantity();
        }
        return out;

    }

    private long calcuIn(long id, long prdId) {
        long in = 0;
        List<Batch> productOut = batchRepo.findByToIdAndProductId(id, prdId);

        for (int i = 0; i < productOut.size(); i++) {
            in += productOut.get(i).getQuantity();
        }
        return in;
    }

    private long calcuPrice(long id, long prdId) {
        long price = 0;
        List<Batch> prices = batchRepo.findByToIdAndProductId(id, prdId);

        for (int i = 0; i < prices.size(); i++) {
            price += (prices.get(i).getPrice() * prices.get(i).getQuantity());
        }

        return price;
    }

    @PostMapping("/requestTransfer")
    public ResponseEntity<?> submitTransfer(@RequestHeader("Username") String username, @RequestBody RequestTransferDTO requestTransferDTO) {
        long dealerId = userRepo.findByUsername(username).get().getLocationId();
        if (calcuAvailable(requestTransferDTO.getFactoryId(), requestTransferDTO.getFactoryId()) < requestTransferDTO.getQuantity()
                || requestTransferDTO.getQuantity() < 0) {
            return ResponseEntity.badRequest().body("QUANTITY_ISNT_AVAIABLE");
        }
        RequestTransfer newRequest = new RequestTransfer();

        newRequest.setFactoryId(requestTransferDTO.getFactoryId());
        newRequest.setProductId(requestTransferDTO.getProductId());
        newRequest.setPrice(requestTransferDTO.getPrice());
        newRequest.setQuantity(requestTransferDTO.getQuantity());
        newRequest.setDealerId(dealerId);
        newRequest.setStatus(1);

        return ResponseEntity.ok("SUCCESS");
    }

    /* Lấy ra lịch sử bán hàng của cơ sở đó */
    @GetMapping("/getSoldHistory")
    @ResponseBody
    public List<SoldHistoryResponse> getHistory(@RequestHeader("Username") String username) {
        long id = userRepo.findByUsername(username).get().getLocationId();

        List<SoldHistoryResponse> res = new ArrayList<>();
        Iterable<Batch> batches = batchRepo.findByFromIdAndStatus(id, "SELL");

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

    @GetMapping("/thisMonth")
    @ResponseBody
    public AnalysisReponse getThisMonth(@RequestHeader("Username") String username) {
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> batchIn = batchRepo.findByToId(id);
        Iterable<Batch> batchOut = batchRepo.findByFromId(id);

        long inValue = 0;
        long outValue = 0;

        Date now = new Date(System.currentTimeMillis());
        AnalysisReponse res = new AnalysisReponse();

        for (Batch batch : batchIn) {
            if ((batch.getDate().getMonth() == now.getMonth()) && (batch.getDate().getYear() == now.getYear())) {
                inValue += batch.getQuantity();
            }
        }
        for (Batch batch : batchOut) {
            if ((batch.getDate().getMonth() == now.getMonth()) && (batch.getDate().getYear() == now.getYear())) {
                outValue += batch.getQuantity();
            }
        }

        res.setNhap(inValue);
        res.setXuat(outValue);

        return res;
    }

    @GetMapping("/thisQuarter")
    @ResponseBody
    public AnalysisReponse getThisQuarter(@RequestHeader("Username") String username) {
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> batchIn = batchRepo.findByToId(id);
        Iterable<Batch> batchOut = batchRepo.findByFromId(id);

        long inValue = 0;
        long outValue = 0;

        Date now = new Date(System.currentTimeMillis());
        int thisQuarter = (int) (now.getMonth() / 3);
        List<Integer> quarter = new ArrayList<>();

        quarter.add(thisQuarter * 3);
        quarter.add(thisQuarter * 3 + 1);
        quarter.add(thisQuarter * 3 + 2);

        AnalysisReponse res = new AnalysisReponse();

        for (int i = 0; i < 3; i++) {
            for (Batch batch : batchIn) {
                if ((batch.getDate().getMonth() == quarter.get(i)) && (batch.getDate().getYear() == now.getYear())) {
                    inValue += batch.getQuantity();
                }
            }
            for (Batch batch : batchOut) {
                if ((batch.getDate().getMonth() == quarter.get(i)) && (batch.getDate().getYear() == now.getYear())) {
                    outValue += batch.getQuantity();
                }
            }
        }

        res.setNhap(inValue);
        res.setXuat(outValue);

        return res;
    }

    @GetMapping("/thisYear")
    @ResponseBody
    public AnalysisReponse getThisYear(@RequestHeader("Username") String username) {
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> batchIn = batchRepo.findByToId(id);
        Iterable<Batch> batchOut = batchRepo.findByFromId(id);

        long inValue = 0;
        long outValue = 0;

        Date now = new Date(System.currentTimeMillis());
        AnalysisReponse res = new AnalysisReponse();

        for (Batch batch : batchIn) {
            if (batch.getDate().getYear() == now.getYear()) {
                inValue += batch.getQuantity();
            }
        }
        for (Batch batch : batchOut) {
            if (batch.getDate().getYear() == now.getYear()) {
                outValue += batch.getQuantity();
            }
        }

        res.setNhap(inValue);
        res.setXuat(outValue);

        return res;
    }

    /*Danh sách các sản phẩm còn hạn bảo hành*/
    @GetMapping("/validWarranty")
    @ResponseBody
    public List<SoldHistoryResponse> validWarranty(@RequestHeader("Username") String username) {
        long id = userRepo.findByUsername(username).get().getLocationId();

        List<SoldHistoryResponse> res = new ArrayList<>();
        Iterable<Batch> batches = batchRepo.findByFromIdAndStatus(id, "SELL");

        java.util.Date date = new java.util.Date(System.currentTimeMillis());

        for (Batch batch : batches) {
            if ((batch.getWarrantyDate()).after(date)) {
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
        }
        return res;
    }
}