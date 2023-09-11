package org.changsol.configs;

import io.netty.channel.ChannelOption;
import io.netty.handler.timeout.ReadTimeoutHandler;
import io.netty.handler.timeout.WriteTimeoutHandler;
import java.time.Duration;
import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.LoggingCodecSupport;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.netty.http.client.HttpClient;

@Configuration
@Slf4j
public class WebClientConfig {

	@Bean
	public WebClient webClient() {

		// 메모리 버퍼 설정
		ExchangeStrategies exchangeStrategies = ExchangeStrategies.builder()
																  .codecs(configurer -> configurer.defaultCodecs().maxInMemorySize(1024 * 1024 * 50))
																  .build();
		//로깅 설정
		exchangeStrategies
			.messageWriters().stream()
			.filter(LoggingCodecSupport.class::isInstance)
			.forEach(writer -> ((LoggingCodecSupport) writer).setEnableLoggingRequestDetails(true));

		HttpClient httpClient = HttpClient.create()
										  .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000 * 10) // 클라이언트와 서버 간 커넥션 소요 시간
										  .responseTimeout(Duration.ofMillis(1000 * 10)) // 응답 대기 시간
										  .doOnConnected(conn ->
															 conn.addHandlerLast(new ReadTimeoutHandler(60, TimeUnit.SECONDS)) //데이터를 읽기 위해 기다리는 시간
																 .addHandlerLast(new WriteTimeoutHandler(60, TimeUnit.SECONDS))); //데이터를 쓰기 위해 기다리는 시간

		return WebClient.builder()
						.clientConnector(new ReactorClientHttpConnector(httpClient))
						.exchangeStrategies(exchangeStrategies)
//						.filter(ExchangeFilterFunction.ofRequestProcessor(
//							clientRequest -> {
//								log.debug("Request: {} {}", clientRequest.method(), clientRequest.url());
//								clientRequest.headers().forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
//								return Mono.just(clientRequest);
//							}
//						))
//						.filter(ExchangeFilterFunction.ofResponseProcessor(
//							clientResponse -> {
//								clientResponse.headers().asHttpHeaders().forEach((name, values) -> values.forEach(value -> log.debug("{} : {}", name, value)));
//								return Mono.just(clientResponse);
//							}
//						))
//						.defaultHeader("user-agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_14_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/78.0.3904.87 Safari/537.3")
						.build();
	}
}
