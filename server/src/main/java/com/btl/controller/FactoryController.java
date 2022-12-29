package com.btl.controller;

import com.btl.dto.*;
import com.btl.entity.*;
import com.btl.entity.Products;
import com.btl.repo.*;
import com.btl.response.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:5500"})
@RequestMapping("/factory")

public class FactoryController {
    @Autowired
    ProductRepo productRepo;
    @Autowired
    OptionRepo optionRepo;

    @Autowired
    RoleRepo roleRepo;
    @Autowired
    LocationRepo locationRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    BatchRepo batchRepo;

    @Autowired
    RequestTransferRepo requestTransferRepo;

    @Autowired
    BillRepo billRepo;

    @GetMapping("/allProduct")
    @ResponseBody
    public List<ProductResponse> getAllProduct(@RequestHeader("Username") String username) {
        List<ProductResponse> products = new ArrayList<>();
        long id = userRepo.findByUsername(username).get().getLocationId();
        //Iterable<Products> productsAll = productRepo.findAll();
        Iterable<Batch> allBatch = batchRepo.findByToId(id);
        Set<Long> listPrd = new HashSet<>();
        for (Batch batch : allBatch) {
            listPrd.add(batch.getProductId());
        }

        System.out.println(listPrd);

        for (Long pid : listPrd) {
            ProductResponse productResponse = new ProductResponse();

            productResponse.setProductId(pid);
            productResponse.setName(productRepo.findByProductId(pid).getProductName());
            productResponse.setSku(productRepo.findByProductId(pid).getProductSku());
            productResponse.setInfo(productRepo.findByProductId(pid).getOption().getOptionInfo());
            productResponse.setSlXuat(calcuOut(id, pid));
            productResponse.setSlNhap(calcuIn(id, pid));

            products.add(productResponse);
        }
        return products;
    }


    @GetMapping("/allDealer")
    @ResponseBody
    public List<Stored> getDealer() {
        List<Stored> storeds = new ArrayList<>();
        Iterable<Stored> allLocation = locationRepo.findByLocationType("DEALER");
        for (Stored location : allLocation) {
            storeds.add(location);
        }
        return storeds;
    }


    /* --------------------------------------- 3 nhiệm vụ đầu -------------------------------------------*/
    @PostMapping("/makeNew")
    public ResponseEntity<?> makeNewProduct(@RequestHeader("Username") String username, @RequestBody ProductDTO productDTO) {

        //sản xuất các sản phẩm mới vào kho
        Batch batch = new Batch();
        Products product = productRepo.findByProductSku(productDTO.getProductSku());
        long fromId = (userRepo.findByUsername(username).get().getLocationId());

        if (product == null) {
            product = new Products();
            product.setProductSku(productDTO.getProductSku());
            product.setProductName(productDTO.getProductName());
            product.setProductMfg(productDTO.getProductMfg());
            product.setOption(optionRepo.findOptionsByOptionId(productDTO.getOptionId()));
            product.setProductStock(productDTO.getQuantity());
        } else {
            if (productDTO.getOptionId() != product.getOption().getOptionId()) {
                return ResponseEntity.badRequest().body("DUPLICATE_SKU_WITH_WRONG_OPTION");
            }
            product.setProductStock(product.getProductStock() + productDTO.getQuantity());
        }
        productRepo.save(product);

        batch.setDate(productDTO.getProductMfg());
        batch.setProductId(product.getProductId());
        batch.setQuantity(productDTO.getQuantity());
        batch.setFromId(0);
        batch.setStatus("MAKE");
        batch.setPrice(0);
        batch.setToId((userRepo.findByUsername(username).get().getLocationId()));

        batchRepo.save(batch);

        return ResponseEntity.ok("SUCCESS");

    }

    /*   Chuyển số lượng sản phẩm cho của hàng.   */
    @PostMapping("/toDealer")
    public ResponseEntity<?> exportToDealer(@RequestHeader("Username") String username, @RequestBody TransferDTO transferDTO) {
        Batch batch = new Batch();
        long fromId = (userRepo.findByUsername(username).get().getLocationId());

        List<Batch> product = batchRepo.findByToIdAndProductId(fromId, transferDTO.getProductId());

        long available = 0;
        for (int i = 0; i < product.size(); i++) {
            available += product.get(i).getQuantity();
        }

        if (transferDTO.getQuantity() > available || transferDTO.getQuantity() < 0) {
            return ResponseEntity.badRequest().body("QUANTITY_ISNT_AVAILABLE!");
        } else {
            batch.setFromId(fromId);
            batch.setToId(transferDTO.getToId());
            batch.setQuantity(transferDTO.getQuantity());
            batch.setProductId(transferDTO.getProductId());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date(System.currentTimeMillis());
            batch.setDate(date);
            batch.setStatus("TRANSFER");
            batch.setPrice(transferDTO.getPrice());
            batchRepo.save(batch);

            return new ResponseEntity<>("SUCCESS", HttpStatus.OK);
        }
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

    @PostMapping("/createNewOption")
    public ResponseEntity<?> createNewOption(@RequestBody OptionDTO optionDTO) {

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

    @GetMapping("/makeByMonth")
    @ResponseBody
    public ChartResponse makeByMonthResponese(@RequestHeader("Username") String username) {
        ChartResponse res = new ChartResponse();
        List<String> label = new ArrayList<>();
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> sellBatch = batchRepo.findByStatusAndToId("MAKE", id);
        Set<Long> listPrd = new HashSet<>();

        for (Batch batch : sellBatch) {
            listPrd.add(batch.getProductId());
        }

        List<ComponentResponse> componentResponses = new ArrayList<>();

        SortedSet<Integer> month = new TreeSet<Integer>();
        SortedSet<Integer> year = new TreeSet<Integer>();

        for (Batch batch : sellBatch) {
            int mm = batch.getDate().getMonth();
            int yyyy = batch.getDate().getYear();
            month.add(mm);
            year.add(yyyy);
        }

        for (Long pid : listPrd) {
            ComponentResponse temp = new ComponentResponse();
            temp.setLabel(productRepo.findByProductId(pid).getProductSku());
            label = new ArrayList<>();
            List<Long> data = new ArrayList<>();
            for (int y : year) {

                for (int m : month) {
                    long qtt = 0;
                    for (Batch batch : sellBatch) {
                        if (batch.getDate().getMonth() == m && batch.getDate().getYear() == y && batch.getProductId() == pid) {
                            qtt += batch.getQuantity();
                        }
                    }
                    label.add((m + 1) + " / " + (y + 1900));
                    data.add(qtt);
                }

            }
            temp.setData(data);
            componentResponses.add(temp);
        }
        res.setDatasets(componentResponses);
        res.setLabels(label);

        return res;
    }

    @GetMapping("/makeByYear")
    @ResponseBody
    public ChartResponse makeByYearResponese(@RequestHeader("Username") String username) {
        ChartResponse res = new ChartResponse();
        List<String> label = new ArrayList<>();

        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> sellBatch = batchRepo.findByStatusAndToId("MAKE", id);
        Set<Long> listPrd = new HashSet<>();

        for (Batch batch : sellBatch) {
            listPrd.add(batch.getProductId());
        }

        List<ComponentResponse> componentResponses = new ArrayList<>();

        SortedSet<Integer> month = new TreeSet<Integer>();
        SortedSet<Integer> year = new TreeSet<Integer>();

        for (Batch batch : sellBatch) {
            int mm = batch.getDate().getMonth();
            int yyyy = batch.getDate().getYear();
            month.add(mm);
            year.add(yyyy);
        }

        for (Long pid : listPrd) {
            ComponentResponse temp = new ComponentResponse();
            temp.setLabel(productRepo.findByProductId(pid).getProductSku());
            label = new ArrayList<>();
            List<Long> data = new ArrayList<>();
            for (int y : year) {
                long qtt = 0;
                for (Batch batch : sellBatch) {
                    if (batch.getDate().getYear() == y && batch.getProductId() == pid) {
                        qtt += batch.getQuantity();
                    }
                }
                label.add((y + 1900) + "");
                data.add(qtt);
            }
            temp.setData(data);
            componentResponses.add(temp);
        }
        res.setDatasets(componentResponses);
        res.setLabels(label);

        return res;
    }

    @GetMapping("/xuatByYear")
    @ResponseBody
    public List<MakeByResponse> xuatByYearResponese(@RequestHeader("Username") String username) {

        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> batches = batchRepo.findByFromId(id);

        List<MakeByResponse> res = new ArrayList<>();

        SortedSet<Integer> year = new TreeSet<Integer>();

        for (Batch batch : batches) {
            int yyyy = batch.getDate().getYear();
            year.add(yyyy);
        }

        for (int y : year) {
            MakeByResponse makeByRespone = new MakeByResponse();
            long qtt = 0;
            for (Batch batch : batches) {
                if (batch.getDate().getYear() == y) {
                    qtt += batch.getQuantity();
                }
            }
            makeByRespone.setLabel((y + 1900) + "");
            makeByRespone.setQuantity(qtt);
            res.add(makeByRespone);
        }
        return res;
    }

    @GetMapping("/xuatByMonth")
    @ResponseBody
    public List<MakeByResponse> xuatByMonthResponese(@RequestHeader("Username") String username) {

        long id = userRepo.findByUsername(username).get().getLocationId();
        Iterable<Batch> batches = batchRepo.findByFromId(id);
        List<MakeByResponse> res = new ArrayList<>();

        SortedSet<Integer> month = new TreeSet<Integer>();
        SortedSet<Integer> year = new TreeSet<Integer>();

        for (Batch batch : batches) {
            int mm = batch.getDate().getMonth();
            int yyyy = batch.getDate().getYear();
            month.add(mm);
            year.add(yyyy);
        }

        for (int y : year) {
            for (int m : month) {
                MakeByResponse makeByMonthRespone = new MakeByResponse();
                long qtt = 0;
                for (Batch batch : batches) {
                    if (batch.getDate().getMonth() == m && batch.getDate().getYear() == y) {
                        qtt += batch.getQuantity();
                    }
                }
                if (qtt == 0) {
                    continue;
                }
                makeByMonthRespone.setLabel((m + 1) + " / " + (y + 1900));
                makeByMonthRespone.setQuantity(qtt);
                res.add(makeByMonthRespone);
            }
        }
        return res;
    }

    @GetMapping("/getListRequest")
    @ResponseBody
    public List<RequestTransfer> getListRequest(@RequestHeader("Username") String username) {

        long id = userRepo.findByUsername(username).get().getLocationId();
        List<RequestTransfer> requestTransfers = new ArrayList<>();
        Iterable<RequestTransfer> allRequest = requestTransferRepo.findByFactoryIdAndStatus(id, 1);
        for (RequestTransfer requestTransfer : allRequest) {
            requestTransfers.add(requestTransfer);
        }
        return requestTransfers;
    }

    @PostMapping("/confirmTransfer")
    public ResponseEntity<?> confirmTransfer(@RequestHeader("Username") String username, @RequestBody ConfirmDTO confirmDTO) {
        RequestTransfer transfer = requestTransferRepo.findByRequestId(confirmDTO.getRequestId());

        if (confirmDTO.getCode() == 2) {
            transfer.setStatus(2);

            Batch batch = new Batch();
            batch.setToId(transfer.getDealerId());
            batch.setFromId(transfer.getFactoryId());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date date = new java.util.Date(System.currentTimeMillis());
            batch.setDate(date);
            batch.setStatus("TRANSFER");
            batch.setPrice(transfer.getPrice());
            batch.setQuantity(transfer.getQuantity());
            batch.setProductId(transfer.getProductId());

            batchRepo.save(batch);
            return ResponseEntity.ok("CONFIRM_SUCCESS");
        } else {
            transfer.setStatus(0);
            return ResponseEntity.ok("REJECT_SUCCESS");
        }
    }

}
