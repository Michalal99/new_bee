package com.bee;

import com.bee.models.ERole;
import com.bee.models.Role;
import com.bee.repository.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class  StartUpApplication {

//    @Autowired
//    private RoleRepository roleRepository;

    public static void main(String[] args) {
        SpringApplication.run(StartUpApplication.class, args);
    }

}

@Component
class DemoCommandLineRunner implements CommandLineRunner {

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public void run(String... args) throws Exception {

          roleRepository.deleteAll();
        var x1= new Role();
        x1.setName(ERole.ROLE_ADMIN);
        roleRepository.save(x1);

        var x2= new Role();
        x2.setName(ERole.ROLE_USER);
        roleRepository.save(x2);

        var x3= new Role();
        x3.setName(ERole.ROLE_MODERATOR);
        roleRepository.save(x3);
    }
}
