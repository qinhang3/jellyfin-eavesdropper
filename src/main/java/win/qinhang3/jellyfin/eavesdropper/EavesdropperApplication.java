package win.qinhang3.jellyfin.eavesdropper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import reactor.core.publisher.Mono;

import javax.annotation.Resource;

@SpringBootApplication
public class EavesdropperApplication {

	@Value("${jellyfin.host}")
	public String host;

	public static void main(String[] args) {
		SpringApplication.run(EavesdropperApplication.class, args);
	}

	@Bean
	public RouteLocator routes(RouteLocatorBuilder builder, ModifyPlayUrlService modifyPlayUrlService) {
		return builder.routes()
				.route("setPlayUrl",
					r -> r.path("/dlna/{UUID}/contentdirectory/control")
					.filters(f -> f.modifyResponseBody(String.class, String.class, (exchange, s) -> Mono.just(modifyPlayUrlService.apply(s))))
					.uri(host))
				.route("default", r -> r.alwaysTrue().uri(host))
				.build();
	}

}
