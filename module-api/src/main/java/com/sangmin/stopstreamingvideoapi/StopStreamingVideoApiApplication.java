package com.sangmin.stopstreamingvideoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.sangmin.stopstreamingvideo"})
public class StopStreamingVideoApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(StopStreamingVideoApiApplication.class, args);
    }

}
