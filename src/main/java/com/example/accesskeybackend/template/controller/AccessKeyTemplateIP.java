package com.example.accesskeybackend.template.controller;


import com.example.accesskeybackend.exception.BaseException;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import org.apache.hc.core5.net.InetAddressUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.xbill.DNS.Address;

import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;

@RestController
@RequestMapping("/api/web")
@AllArgsConstructor
public class AccessKeyTemplateIP {


    @SneakyThrows
    @GetMapping("/checkIpv6Support")
    public ResponseEntity<?> checkIP6ByHost(@RequestParam String siteUrl){

        String host;
        if(siteUrl.startsWith("http")){
            host = new URL(siteUrl).getHost();
        }else {
            host = siteUrl;
        }

        InetAddress[] inetAddresses;
        try {
            inetAddresses = Address.getAllByName(host);
        }catch (UnknownHostException e){
            return new ResponseEntity(new BaseException("Введено некорректное имя домена"), HttpStatus.BAD_REQUEST);
        }

        for (InetAddress inetAddress : inetAddresses) {
            if(InetAddressUtils.isIPv6Address(inetAddress.getHostAddress())){
                return ResponseEntity.ok(true);
            }
        }
        return ResponseEntity.ok(false);
    }


}
