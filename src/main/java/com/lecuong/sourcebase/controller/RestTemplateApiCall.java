package com.lecuong.sourcebase.controller;

import com.lecuong.sourcebase.modal.request.user.UserSaveRequest;
import com.lecuong.sourcebase.modal.response.user.UserResponse;
import com.lecuong.sourcebase.util.RestApiUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

/**
 * @author CuongLM
 * @created 13/08/2023 - 1:24 PM
 * @project source-base
 */
@RestController
@RequestMapping("/call")
public class RestTemplateApiCall {

    public static final String URL = "http://localhost:8080/test";

    @Autowired
    private RestApiUtils restApiUtils;

    /**
     * Call api using RestTemplate with parameters and HttpMethod GET
     */
    @GetMapping("/call-with-params")
    public ResponseEntity<?> getDataFromApi(@RequestParam String name,
                                            @RequestParam int age) {

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);
        builder
                .queryParam("name", name)
                .queryParam("age", age);

//        String response = restApiUtils.callRestApi(builder.toUriString(), HttpMethod.GET, String.class);
        String response = restApiUtils.callRestApi(builder.toUriString(), HttpMethod.GET, String.class);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/call-with-list-params")
    public ResponseEntity<?> getDataFromApiWithListParams() {

        List<String> list = Arrays.asList("1", "2", "3");

        UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(URL);
        builder
                .queryParam("age", String.join(",", list));

        String response = restApiUtils.callRestApi(builder.toUriString(), HttpMethod.GET, null, null, String.class);

        return ResponseEntity.ok(response);
    }

    /**
     * Call api using RestTemplate no parameters and HttpMethod GET
     */
    @GetMapping("/call-no-params")
    public ResponseEntity<?> getDataFromApi() {

        String response = restApiUtils.callRestApi(URL, HttpMethod.GET, String.class);

        return ResponseEntity.ok(response);
    }

    /**
     * Call api using RestTemplate with body and HttpMethod POST
     */
    @PostMapping("/call-with-body")
    public ResponseEntity<?> getDataFromApi(@RequestBody UserSaveRequest request) {

        UserResponse response = restApiUtils.callRestApi(URL, HttpMethod.POST, request, UserResponse.class);
//        restApiUtils.callRestApi(URL, HttpMethod.POST, request, Void.class);

        return ResponseEntity.ok(response);
    }
}
