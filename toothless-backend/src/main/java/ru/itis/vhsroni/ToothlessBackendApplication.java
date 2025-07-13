package ru.itis.vhsroni;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import ru.itis.vhsroni.config.property.*;
import ru.itis.vhsroni.error.message.ErrorMessageProperties;

@EnableConfigurationProperties({
		AppConfigProperties.class,
		AuthConfigProperties.class,
		CalendarificConfigProperties.class,
		EncryptionConfigProperties.class,
		LoggingConfigProperties.class,
		MailConfigProperties.class,
		MinioConfigProperties.class,
		ErrorMessageProperties.class
})
@SpringBootApplication
public class ToothlessBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToothlessBackendApplication.class, args);
	}

}
