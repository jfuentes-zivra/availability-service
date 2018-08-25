package com.zivra.platform.AvailabilityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackage;platformng/availability-service.git

import java.util.concurrent.atomic.AtomicLong;
import java.util.List;


@Controller
public class AvailabilityController {

    @Autowired
    // private EngineerRepository repo;

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

        RestTemplate restTemplate = new RestTemplate();

        Greeting grt = restTemplate.getForObject("http://localhost:9000/hello-world?name=" + name, Greeting.class );

        return grt;
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
