package com.btl.controller;

import com.btl.dto.LocationDTO;
import com.btl.dto.LoginDTO;
import com.btl.dto.OptionDTO;
import com.btl.dto.SignUpDTO;
import com.btl.entity.*;
import com.btl.repo.*;
import com.btl.response.ChartResponse;
import com.btl.response.ComponentResponse;
import com.btl.response.PieChartResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@CrossOrigin(origins = {"http://localhost:63342", "http://127.0.0.1:5500"})
@RolesAllowed("ROLE_ADMIN")
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private RoleRepo roleRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    ProductRepo productRepo;
    @Autowired
    private LocationRepo locationRepo;
    @Autowired
    BatchRepo batchRepo;

    @Autowired
    OptionRepo optionRepo;
    @Autowired
    FaultRepo faultRepo;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/changeRole")
    public ResponseEntity<?> changRole(@RequestBody LoginDTO loginDTO, @RequestParam int targetRoleId) {
        User user = userRepo.findByUsername(loginDTO.getUsername()).get();

        String tgRole = "";
        if (targetRoleId == 1) {
            tgRole = "ROLE_FACTORY";
        } else if (targetRoleId == 2) {
            tgRole = "ROLE_DEALER";
        } else if (targetRoleId == 3) {
            tgRole = "ROLE_SERVICE";
        } else if (targetRoleId == 4) {
            tgRole = "ROLE_ADMIN";
        } else {
            tgRole = "ROLE_DEALER";
        }
        user.setRoles(roleRepo.findByName(tgRole).stream().collect(Collectors.toSet()));
        userRepo.save(user);
        return ResponseEntity.ok("CHANGE SUCCESSFULLY!!");
    }

    @PostMapping("/addAccount")
    public ResponseEntity<?> addAccount(@RequestParam String type, @RequestBody SignUpDTO signUpDTO) {
        String typeRole = "";
        if (type.equals("admin")) {
            typeRole = "ROLE_ADMIN";
        } else if (type.equals("dealer")) {
            typeRole = "ROLE_DEALER";
        } else if (type.equals("factory")) {
            typeRole = "ROLE_FACTORY";
        } else if (type.equals("service")) {
            typeRole = "ROLE_SERVICE";
        } else {
            return ResponseEntity.ok("NOT_SUPPORT");
        }

        if (userRepo.existsByUsername(signUpDTO.getUsername())) {
            return new ResponseEntity<>("ALREADY_TAKEN", HttpStatus.BAD_REQUEST);
        }

        User user = new User();
        user.setName(signUpDTO.getName());
        user.setUsername(signUpDTO.getUsername());
        user.setPassword(passwordEncoder.encode(signUpDTO.getPassword()));

        Role roles = roleRepo.findByName(typeRole).get(0);
        user.setRoles(Collections.singleton(roles));

        userRepo.save(user);

        return ResponseEntity.ok("SUCCESS");
    }

    @GetMapping("/listUser")
    @ResponseBody
    public List<User> getListUser() {
        List<User> users = new ArrayList<>();
        Iterable<User> allUser = userRepo.findAll();
        for (User user : allUser) {
            //user.setPassword("hided");
            users.add(user);
        }
        return users;
    }

    @PostMapping("/changePass")
    public ResponseEntity<?> changePass(@RequestParam String username, @RequestBody String newPass) {
        User user = userRepo.findByUsername(username).get();
        if (user == null) {
            return new ResponseEntity<>("USER_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }

        user.setPassword(passwordEncoder.encode(newPass));
        userRepo.save(user);
        return ResponseEntity.ok("SUCCESS");
    }

    @GetMapping("/getListLocation")
    @ResponseBody
    public List<Stored> getList() {
        List<Stored> storeds = new ArrayList<>();
        Iterable<Stored> allLocation = locationRepo.findAll();
        for (Stored location : allLocation) {
            //user.setPassword("hided");
            if (location.getLocationName().equals("all")) {
                continue;
            }
            storeds.add(location);

        }
        return storeds;
    }

    @PostMapping("/addLocation")
    public ResponseEntity<?> addLocation(@RequestBody LocationDTO locationDTO) {
        try {
            Stored newStored = new Stored();
            newStored.setName(locationDTO.getName());
            newStored.setLocationName(locationDTO.getLocationName());
            newStored.setPhone(locationDTO.getPhone());
            newStored.setAddress(locationDTO.getAddress());
            newStored.setLocationType(locationDTO.getLocationType());

            locationRepo.save(newStored);
            return ResponseEntity.ok().body("SUCCESS");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/sellByYear")
    @ResponseBody
    public ChartResponse getSellByYear() {
        ChartResponse res = new ChartResponse();
        List<String> label = new ArrayList<>();

        Iterable<Batch> sellBatch = batchRepo.findByStatus("SELL");
        Set<Long> listPrd = new HashSet<>();

        for (Batch batch : sellBatch) {
            listPrd.add(batch.getProductId());
        }

        List<ComponentResponse> componentResponses = new ArrayList<>();

        Date now = new Date(System.currentTimeMillis());
        int year = now.getYear();

        for (Long pid : listPrd) {
            ComponentResponse temp = new ComponentResponse();
            temp.setLabel(productRepo.findByProductId(pid).getProductSku() + " - " + productRepo.findByProductId(pid).getOption().getOptionName());
            label = new ArrayList<>();
            List<Long> data = new ArrayList<>();
            for (int y = year - 1; y <= year; y++) {
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

    @GetMapping("/makeByYear")
    @ResponseBody
    public ChartResponse getMakeByYear() {
        ChartResponse res = new ChartResponse();
        List<String> label = new ArrayList<>();

        Iterable<Batch> sellBatch = batchRepo.findByStatus("MAKE");
        Set<Long> listPrd = new HashSet<>();

        for (Batch batch : sellBatch) {
            listPrd.add(batch.getProductId());
        }

        List<ComponentResponse> componentResponses = new ArrayList<>();

        Date now = new Date(System.currentTimeMillis());
        int year = now.getYear();

        for (Long pid : listPrd) {
            ComponentResponse temp = new ComponentResponse();
            temp.setLabel(productRepo.findByProductId(pid).getProductSku() + " - " + productRepo.findByProductId(pid).getOption().getOptionName());
            label = new ArrayList<>();
            List<Long> data = new ArrayList<>();
            for (int y = year - 2; y <= year; y++) {
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

    @GetMapping("/faultByYear")
    @ResponseBody
    public PieChartResponse faultRete() {
        PieChartResponse res = new PieChartResponse();
        List<String> label = new ArrayList<>();

        Iterable<Fault> faults = faultRepo.findAll();
        Set<Long> listPrd = new HashSet<>();

        for (Fault fault : faults) {
            listPrd.add(fault.getProductId());
        }

        List<Long> datasets = new ArrayList<>();

        Date now = new Date(System.currentTimeMillis());
        int year = now.getYear();

        for (Long pid : listPrd) {
            long data = 0;
            label.add(productRepo.findByProductId(pid).getProductSku() + " - " + productRepo.findByProductId(pid).getOption().getOptionName());
            for (Fault fault : faults) {
                if (fault.getReceiveDate().getYear() == year && fault.getProductId() == pid) {
                    data += fault.getQuantity();
                }
            }
            datasets.add(data);
        }
        res.setDatasets(datasets);
        res.setLabels(label);

        return res;
    }

}
