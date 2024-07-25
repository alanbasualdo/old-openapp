package com.opencars.netgo;

import com.opencars.netgo.Notifications.service.NotificationsService;
import com.opencars.netgo.auth.service.SesionesService;
import com.opencars.netgo.dms.quoter.controller.InfoautoController;
import com.opencars.netgo.dms.quoter.dto.UpdateDatetime;
import com.opencars.netgo.dms.quoter.entity.BrandInfoAuto;
import com.opencars.netgo.dms.quoter.service.BrandInfoAutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

@SpringBootApplication
@ComponentScan
@EnableScheduling
public class NetGoApplication {
	private final long SEGUNDO = 1000;
	private final long MINUTO = SEGUNDO * 60;

	@Autowired
	SesionesService sesionesService;

	@Autowired
	NotificationsService notificationsService;

	@Autowired
	InfoautoController infoautoController;

	@Autowired
	BrandInfoAutoService brandInfoAutoService;

	public static void main(String[] args){
		TimeZone.setDefault(TimeZone.getTimeZone("GMT-3:00"));
		SpringApplication.run(NetGoApplication.class, args);

	}

	@Scheduled(fixedDelay = MINUTO)
	public void changeDropSessionsToInactive() {

		sesionesService.changeDropSessionsToInactive();

	}

	@Scheduled(fixedDelay = 86400000)
	public void deleteNotifications() {

		notificationsService.deleteNotifications();

	}

	@Scheduled(fixedDelay = 86400000)
	public void downloadBrandsInfoAuto() {

		UpdateDatetime lastUpdate = infoautoController.updateDatetimeInternal();
		if(infoautoController.brandsCompleteInternal().size() == 0){
			List<BrandInfoAuto> list = infoautoController.getBrands();
			brandInfoAutoService.saveAll(list);
		}else{
			if(Objects.equals(lastUpdate.getUpdated_at().toLocalDateTime().toLocalDate(), LocalDate.now())){
				brandInfoAutoService.deleteAll();
				List<BrandInfoAuto> list = infoautoController.getBrands();
				brandInfoAutoService.saveAll(list);
			}
		}
	}

	@Scheduled(cron = "0 0 1 1 * *")
	public void downloadModelsInfoAuto() {
		// Actualizo la lista de modelos el día 1 de cada mes a la 1AM
		infoautoController.getModelsOfInfoAutoAndSave();
	}

	@Scheduled(fixedDelay = 86400000)
	public void verifyModelsInfoAuto() {
		// Verifico si los modelos existen en la tabla de Modelos Info Auto, si no existen los cargo
		// Esto se hace porque la tarea programada se ejecuta el 1 de cada mes, y si compilamos el código
		// por primera vez, habría que esperar a ese día para obtener los modelos.
		infoautoController.getModelsOfInfoAutoAndSave();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/**").allowedOrigins("*").allowedMethods("GET", "POST","PUT", "DELETE");
			}
		};
	}
}
