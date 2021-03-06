package co.id.mii.serverside;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class Mcc63ServersideApplication {

	public static void main(String[] args) {
		SpringApplication.run(Mcc63ServersideApplication.class, args);
                System.out.println("Serverside is Running");
	}
        
        @Bean
        public ModelMapper modelMapper(){
            return new ModelMapper();
        }
}
