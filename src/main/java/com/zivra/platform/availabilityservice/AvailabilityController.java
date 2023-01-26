package com.zivra.platform.availabilityservice;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;
import org.springframework.core.env.Environment;
import javax.crypto.Cipher;
import java.util.concurrent.atomic.AtomicLong;
import java.util.List;
import java.net.*;

@Controller
public class AvailabilityController {

    @Autowired
    private Environment env;

    private static final String template = "Hello %s!";
    private final AtomicLong counter = new AtomicLong();

    @GetMapping("/hello-world")
    @ResponseBody
    public Greeting sayHello(@RequestParam(name="name", required = false, defaultValue = "world") String name) {
        return new Greeting(counter.incrementAndGet(), String.format(template, name));
    }

    @GetMapping("/rest-hello")
    @ResponseBody
    public Greeting restHello(@RequestParam(name="name", required = false, defaultValue = "world") String name){

        // trying some bad things to see if sonarqube will catch them
        final String ip = "192.168.12.42"; // Noncompliant

        try{
            final URL url = new URL("https://google.com");

            // BAD: DES is a weak algorithm
            final Cipher des = Cipher.getInstance("DES");
            // des.init(Cipher.ENCRYPT_MODE, null);

            final byte[] encrypted = des.doFinal("hello".getBytes("UTF-8"));

            // ...

            // GOOD: AES is a strong algorithm
            final Cipher aes = Cipher.getInstance("AES");

        }
        catch(Exception e){
        }

        ////////////////////////////////////////////////////////////////////
        
        final RestTemplate restTemplate = new RestTemplate();

        return restTemplate.getForObject("http://localhost:" + env.getProperty("server.port") + "/hello-world?name=" + name, Greeting.class);
    }


//     @GetMapping("/engineers")
//     @ResponseBody
//     public List<Engineer> getEngineers(){

//         return repo.findAll();
//     }

//     @PostMapping("/engineer")
//     @ResponseBody
//     public Engineer addEngineer(@RequestBody Engineer engineer){

// //        return repo.save(new Engineer(firstName, lastName));
//         System.out.println(engineer.firstName);
//         return repo.save(engineer);

//     }
}
