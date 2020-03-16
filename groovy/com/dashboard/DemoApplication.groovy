package com.dashboard

import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer
import org.springframework.context.annotation.Configuration

@Configuration
@SpringBootApplication(exclude = [DataSourceAutoConfiguration.class])
@Slf4j
class DemoApplication extends SpringBootServletInitializer implements CommandLineRunner {

    @Value('${spring.application.name}')
    String applicationName

    static void main(String[] args) {
        SpringApplication.run(DemoApplication, args)
    }

    @Override
    void run(String... strings) throws Exception {
        println("************************************")
        println("************************************")
        println("************************************")
        log.info("{} Started Successfully", applicationName)
        println("************************************")
        println("************************************")
        println("************************************")
    }

}
