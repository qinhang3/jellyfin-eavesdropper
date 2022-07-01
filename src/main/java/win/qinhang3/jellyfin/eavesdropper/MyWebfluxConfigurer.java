package win.qinhang3.jellyfin.eavesdropper;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.codec.ServerCodecConfigurer;
import org.springframework.web.reactive.config.WebFluxConfigurer;

/**
 * @description:
 * @author: hang
 * @create: 2022/7/1
 **/
@Configuration
public class MyWebfluxConfigurer implements WebFluxConfigurer {
    @Override
    public void configureHttpMessageCodecs(ServerCodecConfigurer configurer) {
        configurer.defaultCodecs().maxInMemorySize(20 * 1024 * 1024);
    }
}
