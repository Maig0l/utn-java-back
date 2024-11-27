package gg.wellplayed.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.yaml.snakeyaml.error.MissingEnvironmentVariableException;

@SpringBootApplication
@RestController
public class WellplayedApiApplication {

	public static void main(String[] args) {
		checkEnvironment();
		SpringApplication.run(WellplayedApiApplication.class, args);
	}
	
	@GetMapping("/")
	public String index() {
		return "Welcome.";
	}

	private static void checkEnvironment() throws MissingEnvironmentVariableException {
		String[] vars = {
				"DB_CONN_STRING",
				"DB_PASS",
				"DB_USER",
				"API_SECRET"};

		for (String var : vars) {
			if (System.getenv(var) == null) {
				throw new MissingEnvironmentVariableException("Falta la variable de entorno " + var);
			}
		}
	}
}
