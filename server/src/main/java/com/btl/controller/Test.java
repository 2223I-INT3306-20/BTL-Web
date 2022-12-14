package com.btl.controller;

import com.btl.dto.LoginDTO;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;
import java.io.IOException;
import java.io.InputStream;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/test")
public class Test {

    @GetMapping("/getTest")
    public ResponseEntity<?> getTest() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Access-Control-Allow-Origin", "Value-ResponseEntityBuilderWithHttpHeaders");
        return ResponseEntity.ok()
                .headers(headers)
                .body("Response with header using ResponseEntity");
    }

    @GetMapping("/getImg")
    public @ResponseBody byte[] getImage() throws IOException {
        InputStream imgPath = getClass()
                .getResourceAsStream("/image/sid.jpg");
        // open image
        //File imgPath = new File("/com/btl/image/sid.jpg");
        assert imgPath != null;
        BufferedImage bufferedImage = ImageIO.read(imgPath);

        // get DataBufferBytes from Raster
        WritableRaster raster = bufferedImage .getRaster();
        DataBufferByte data   = (DataBufferByte) raster.getDataBuffer();

        return ( data.getData() );
    }

    @RequestMapping(value = "/sid", method = RequestMethod.GET,
            produces = MediaType.IMAGE_JPEG_VALUE)

    public void getImage(HttpServletResponse response) throws IOException {

        ClassPathResource imgFile = new ClassPathResource("image/sid.jpg");

        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(imgFile.getInputStream(), response.getOutputStream());
    }

}
