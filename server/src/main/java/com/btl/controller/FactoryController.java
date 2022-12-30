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

import javax.annotation.security.RolesAllowed;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:5500"})
@RolesAllowed({"ROLE_ADMIN", "ROLE_FACTORY"})
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
    FaultRepo faultRepo;

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

        Iterable<Batch> makeBatch = batchRepo.findByStatusAndToId("MAKE", id);
        Set<Long> listPrd = new HashSet<>();
        Date now = new Date(System.currentTimeMillis());
        int y = now.getYear();

        for (Batch batch : makeBatch) {
            if (batch.getDate().getYear() == y) {
                listPrd.add(batch.getProductId());
            }
        }

        List<ComponentResponse> componentResponses = new ArrayList<>();

        int count = 0;
        for (Long pid : listPrd) {
            ComponentResponse temp = new ComponentResponse();
            temp.setLabel(productRepo.findByProductId(pid).getOption().getOptionName() + " - " + productRepo.findByProductId(pid).getProductSku());
            label = new ArrayList<>();
            List<Long> data = new ArrayList<>();

            for (int m = 0; m < 12; m++) {
                long qtt = 0;
                for (Batch batch : makeBatch) {
                    if (batch.getDate().getMonth() == m && batch.getDate().getYear() == y && batch.getProductId() == pid) {
                        qtt += batch.getQuantity();
                    }
                }
                label.add((m + 1) + " / " + (y + 1900));
                data.add(qtt);
            }
            count = 1;
            temp.setData(data);
            componentResponses.add(temp);
        }
        res.setDatasets(componentResponses);
        res.setLabels(label);

        return res;
    }

    @GetMapping("/makeByQuarter")
    @ResponseBody
    public ChartResponse makeByQuarterResponese(@RequestHeader("Username") String username) {
        ChartResponse res = new ChartResponse();
        List<String> label = new ArrayList<>();
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> sellBatch = batchRepo.findByStatusAndToId("MAKE", id);
        Set<Long> listPrd = new HashSet<>();
        Date now = new Date(System.currentTimeMillis());
        int y = now.getYear();

        for (Batch batch : sellBatch) {
            if (batch.getDate().getYear() == y) {
                listPrd.add(batch.getProductId());
            }
        }

        List<ComponentResponse> componentResponses = new ArrayList<>();

        for (Long pid : listPrd) {
            ComponentResponse temp = new ComponentResponse();
            temp.setLabel(productRepo.findByProductId(pid).getOption().getOptionName() + " - " + productRepo.findByProductId(pid).getProductSku());
            label = new ArrayList<>();
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

        java.util.Date now = new java.util.Date(System.currentTimeMillis());
        int year = now.getYear();

        List<ComponentResponse> componentResponses = new ArrayList<>();
        for (Long pid : listPrd) {
            ComponentResponse temp = new ComponentResponse();
            temp.setLabel(productRepo.findByProductId(pid).getOption().getOptionName() + " - " + productRepo.findByProductId(pid).getProductSku());
            label = new ArrayList<>();

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

    @GetMapping("/xuatByYear")
    @ResponseBody
    public ChartResponse xuatByYearResponese(@RequestHeader("Username") String username) {

        ChartResponse res = new ChartResponse();
        List<String> label = new ArrayList<>();
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> transferBatch = batchRepo.findByStatusAndFromId("TRANSFER", id);
        Set<Long> listPrd = new HashSet<>();

        for (Batch batch : transferBatch) {
            listPrd.add(batch.getProductId());
        }

        java.util.Date now = new java.util.Date(System.currentTimeMillis());
        int year = now.getYear();

        List<ComponentResponse> componentResponses = new ArrayList<>();
        for (Long pid : listPrd) {
            ComponentResponse temp = new ComponentResponse();
            temp.setLabel(productRepo.findByProductId(pid).getOption().getOptionName() + " - " + productRepo.findByProductId(pid).getProductSku());
            label = new ArrayList<>();

            List<Long> data = new ArrayList<>();
            long qtt = 0;
            for (int y = year - 5; y <= year; y++) {
                for (int m = 0; m < 12; m++) {
                    for (Batch batch : transferBatch) {
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

    @GetMapping("/xuatByQuarter")
    @ResponseBody
    public ChartResponse xuatByQuarterResponese(@RequestHeader("Username") String username) {
        ChartResponse res = new ChartResponse();
        List<String> label = new ArrayList<>();
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> sellBatch = batchRepo.findByStatusAndFromId("TRANSFER", id);
        Set<Long> listPrd = new HashSet<>();
        Date now = new Date(System.currentTimeMillis());
        int y = now.getYear();

        for (Batch batch : sellBatch) {
            if (batch.getDate().getYear() == y) {
                listPrd.add(batch.getProductId());
            }
        }

        List<ComponentResponse> componentResponses = new ArrayList<>();

        for (Long pid : listPrd) {
            ComponentResponse temp = new ComponentResponse();
            temp.setLabel(productRepo.findByProductId(pid).getOption().getOptionName() + " - " + productRepo.findByProductId(pid).getProductSku());
            label = new ArrayList<>();
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

    @GetMapping("/xuatByMonth")
    @ResponseBody
    public ChartResponse xuatByMonthResponese(@RequestHeader("Username") String username) {
        ChartResponse res = new ChartResponse();
        List<String> label = new ArrayList<>();

        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> makeBatch = batchRepo.findByStatusAndFromId("TRANSFER", id);
        Set<Long> listPrd = new HashSet<>();
        Date now = new Date(System.currentTimeMillis());
        int y = now.getYear();

        for (Batch batch : makeBatch) {
            if (batch.getDate().getYear() == y) {
                listPrd.add(batch.getProductId());
            }
        }

        List<ComponentResponse> componentResponses = new ArrayList<>();

        for (Long pid : listPrd) {
            ComponentResponse temp = new ComponentResponse();
            temp.setLabel(productRepo.findByProductId(pid).getOption().getOptionName() + " - " + productRepo.findByProductId(pid).getProductSku());
            label = new ArrayList<>();
            List<Long> data = new ArrayList<>();

            for (int m = 0; m < 12; m++) {
                long qtt = 0;
                for (Batch batch : makeBatch) {
                    if (batch.getDate().getMonth() == m && batch.getDate().getYear() == y && batch.getProductId() == pid) {
                        qtt += batch.getQuantity();
                    }
                }
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

    @GetMapping("/listXuatByMonth")
    @ResponseBody
    public List<StatisticRespone> listXuatByMonth(@RequestHeader("Username") String username) {
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> sellBatch = batchRepo.findByStatusAndFromId("TRANSFER", id);
        Set<Long> listPrd = new HashSet<>();

        for (Batch batch : sellBatch) {
            listPrd.add(batch.getProductId());
        }

        java.util.Date now = new java.util.Date(System.currentTimeMillis());
        int y = now.getYear();
        int m = now.getMonth();

        List<StatisticRespone> res = new ArrayList<>();
        for (Long pid : listPrd) {

            StatisticRespone temp = new StatisticRespone();
            temp.setName(productRepo.findByProductId(pid).getOption().getOptionName());
            temp.setSku(productRepo.findByProductId(pid).getProductSku());
            temp.setInfo(productRepo.findByProductId(pid).getOption().getOptionInfo());
            //temp.setToName(locationRepo.findById());
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

    @GetMapping("/listXuatByQuarter")
    @ResponseBody
    public List<StatisticRespone> listSellByQuarter(@RequestHeader("Username") String username) {
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> sellBatch = batchRepo.findByStatusAndFromId("TRANSFER", id);
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

    @GetMapping("/listXuatByYear")
    @ResponseBody
    public List<StatisticRespone> listXuatByYear(@RequestHeader("Username") String username) {
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> sellBatch = batchRepo.findByStatusAndFromId("TRANSFER", id);
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

    @GetMapping("/listMakeByMonth")
    @ResponseBody
    public List<StatisticRespone> listMakeByMonth(@RequestHeader("Username") String username) {
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> sellBatch = batchRepo.findByStatusAndToId("MAKE", id);
        Set<Long> listPrd = new HashSet<>();

        for (Batch batch : sellBatch) {
            listPrd.add(batch.getProductId());
        }

        java.util.Date now = new java.util.Date(System.currentTimeMillis());
        int y = now.getYear();
        int m = now.getMonth();

        List<StatisticRespone> res = new ArrayList<>();
        for (Long pid : listPrd) {

            StatisticRespone temp = new StatisticRespone();
            temp.setName(productRepo.findByProductId(pid).getOption().getOptionName());
            temp.setSku(productRepo.findByProductId(pid).getProductSku());
            temp.setInfo(productRepo.findByProductId(pid).getOption().getOptionInfo());
            //temp.setToName(locationRepo.findById());
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

    @GetMapping("/listMakeByQuarter")
    @ResponseBody
    public List<StatisticRespone> listMakeByQuarter(@RequestHeader("Username") String username) {
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> sellBatch = batchRepo.findByStatusAndToId("MAKE", id);
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

    @GetMapping("/listMakeByYear")
    @ResponseBody
    public List<StatisticRespone> listMakeByYear(@RequestHeader("Username") String username) {
        long id = userRepo.findByUsername(username).get().getLocationId();

        Iterable<Batch> sellBatch = batchRepo.findByStatusAndToId("MAKE", id);
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
            batch.setProductId(productRepo.findByProductSku(Long.toString(transfer.getFactoryId())).getProductId());

            batchRepo.save(batch);
            return ResponseEntity.ok("CONFIRM_SUCCESS");
        } else {
            transfer.setStatus(0);
            return ResponseEntity.ok("REJECT_SUCCESS");
        }
    }

    @GetMapping("/cantWarranty")
    @ResponseBody
    public List<WarrantyResponse> listCantWarranty(@RequestHeader("Username") String username) {
        return findList(username);
    }

    private List<WarrantyResponse> findList(String username) {
        long id = userRepo.findByUsername(username).get().getLocationId();

        List<WarrantyResponse> res = new ArrayList<>();
        List<Fault> done = faultRepo.findByServiceIdAndStatus(id, "CANT");
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
