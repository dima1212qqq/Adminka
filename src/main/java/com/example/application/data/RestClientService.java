package com.example.application.data;

import com.example.application.model.CliDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.Serializable;
import java.util.List;

@Service
public class RestClientService implements Serializable {

    private final RestClient jsonplaceholderClient = RestClient.create("http://localhost:8080/v2");
    private final RestClient localClient;
    private String csrfToken = "eyJEb2wiOiLQoNCQ0JfQoNCQ0JHQntCi0KfQmNCaIE9GRklDRSBTViIsIkxvZyI6ImRpbWEyMzYiLCJQb3MiOjAsImFsZyI6IkhTMjU2In0.eyJqdGkiOiJkNmMwYWFiYzAyMjE0M2FkYmEyNTY5MTFiMDU4MzgwMSIsImlhdCI6MTczMzMzNjU4OCwibmJmIjoxNzMzMzM2NTg4LCJleHAiOjIwOTMzOTU3Mjd9.kAtTdHLcbaLIGq6Ty4i3Bi84soDvlwRJy4Qt3HbFAt4";

    public RestClientService(@Value("${server.port}") String serverPort) {
        localClient = RestClient.create("http://localhost:" + serverPort);
    }

    public List<CliDto> getAllCli() {
        System.out.println("getAllCli");

        List<CliDto> cli = jsonplaceholderClient.get()
                .uri("/cli")
                .headers(headers -> headers.add("x-csrf-token", csrfToken))
                .retrieve()
                .body(new ParameterizedTypeReference<List<CliDto>>() {
                });
        return cli;
    }
}
