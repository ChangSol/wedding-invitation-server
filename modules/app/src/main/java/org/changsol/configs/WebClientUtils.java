package org.changsol.configs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.MapUtils;
import org.changsol.exceptions.BadRequestException;
import org.changsol.exceptions.ConflictException;
import org.changsol.exceptions.ForbiddenException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.BodyInserter;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

public class WebClientUtils {

	public static <T> Mono<T> retrieveGetForMono(WebClient webClient,
												 String url,
												 HttpHeaders httpHeaders,
												 MultiValueMap<String, String> paramMap,
												 Class<T> returnClass) {
		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
		}

		if (MapUtils.isEmpty(paramMap)) {
			paramMap = new LinkedMultiValueMap<>();
		}

		HttpHeaders finalHttpHeaders = httpHeaders;

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
														   .queryParams(paramMap);
		return webClient.mutate()
						.build()
						.get()
						.uri(builder.build(false).toUriString())
						.headers(header -> header.addAll(finalHttpHeaders))
						.retrieve()
						.onStatus(HttpStatus::is4xxClientError, response ->
							response.bodyToMono(String.class).map(BadRequestException::new)
						)
						.onStatus(HttpStatus::is5xxServerError, response ->
							response.bodyToMono(String.class).map(RuntimeException::new)
						)
						.bodyToMono(returnClass);
	}

	public static <T> Mono<T> retrievePostForMono(WebClient webClient,
												  String url,
												  HttpHeaders httpHeaders,
												  MultiValueMap<String, String> paramMap,
												  Map<String, Object> bodyMap,
												  Class<T> returnClass) {
		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
		}

		if (MapUtils.isEmpty(paramMap)) {
			paramMap = new LinkedMultiValueMap<>();
		}

		if (MapUtils.isEmpty(bodyMap)) {
			bodyMap = new HashMap<>();
		}

		HttpHeaders finalHttpHeaders = httpHeaders;

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
														   .queryParams(paramMap);

		BodyInserter bodyInserter = BodyInserters.fromValue(bodyMap);
		MultiValueMap<String, String> multiBodyMap;
		if (MediaType.APPLICATION_FORM_URLENCODED.equals(httpHeaders.getContentType())) {
			multiBodyMap = new LinkedMultiValueMap<>();
			multiBodyMap.setAll(bodyMap.entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> String.valueOf(e.getValue()))));
			bodyInserter = BodyInserters.fromValue(multiBodyMap);
		}

		return webClient.mutate()
						.build()
						.post()
						.uri(builder.build(false).toUriString())
						.accept(MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML)
						.headers(header -> header.addAll(finalHttpHeaders))
						.body(bodyInserter)
						.retrieve()
						.onStatus(HttpStatus::is4xxClientError, response -> {
							String errorMessage = response.headers().header("WWW-Authenticate").toString();
							if (errorMessage.contains("409_passwordNotMatch")) {
								throw new ConflictException("비밀번호가 맞지 않습니다.");
							} else if (errorMessage.contains("[403_cert]")) {
								final String[] SPLIT_VALUE = errorMessage.split("\\[403_cert]");
								throw new ForbiddenException("cert_" + SPLIT_VALUE[1]);
							} else {
								return response.bodyToMono(String.class).map(BadRequestException::new);
							}
						})
						.onStatus(HttpStatus::is5xxServerError, response -> response.bodyToMono(String.class).map(RuntimeException::new))
						.bodyToMono(returnClass);
	}

	public static <T> Mono<T> retrievePostForMonoList(WebClient webClient,
													  String url,
													  HttpHeaders httpHeaders,
													  MultiValueMap<String, String> paramMap,
													  List<Map<String, Object>> bodyMapList,
													  Class<T> returnClass) {
		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
		}

		if (MapUtils.isEmpty(paramMap)) {
			paramMap = new LinkedMultiValueMap<>();
		}

		if (CollectionUtils.isEmpty(bodyMapList)) {
			bodyMapList = new ArrayList<>();
		}

		HttpHeaders finalHttpHeaders = httpHeaders;

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
														   .queryParams(paramMap);

		BodyInserter bodyInserter = BodyInserters.fromValue(bodyMapList);

		return webClient.mutate()
						.build()
						.post()
						.uri(builder.build(false).toUriString())
						.headers(header -> header.addAll(finalHttpHeaders))
						.body(bodyInserter)
						.retrieve()
						.onStatus(HttpStatus::is4xxClientError, response ->
							response.bodyToMono(String.class).map(BadRequestException::new)
						)
						.onStatus(HttpStatus::is5xxServerError, response ->
							response.bodyToMono(String.class).map(RuntimeException::new)
						)
						.bodyToMono(returnClass);
	}

	public static <T> Mono<T> retrievePutForMono(WebClient webClient,
												 String url,
												 HttpHeaders httpHeaders,
												 MultiValueMap<String, String> paramMap,
												 Map<String, Object> bodyMap,
												 Class<T> returnClass) {
		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
		}

		if (MapUtils.isEmpty(paramMap)) {
			paramMap = new LinkedMultiValueMap<>();
		}

		if (MapUtils.isEmpty(bodyMap)) {
			bodyMap = new HashMap<>();
		}

		HttpHeaders finalHttpHeaders = httpHeaders;

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
														   .queryParams(paramMap);

		MultiValueMap<String, String> multiBodyMap = null;
		if (MediaType.APPLICATION_FORM_URLENCODED.equals(httpHeaders.getContentType())) {
			multiBodyMap = new LinkedMultiValueMap<>();
			multiBodyMap.setAll(bodyMap.entrySet().stream().collect(Collectors.toMap(Entry::getKey, e -> String.valueOf(e.getValue()))));
		}

		return webClient.mutate()
						.build()
						.put()
						.uri(builder.build(false).toUriString())
						.headers(header -> header.addAll(finalHttpHeaders))
						.body(multiBodyMap != null ?
							  BodyInserters.fromValue(multiBodyMap) :
							  BodyInserters.fromValue(bodyMap)
						)
						.retrieve()
						.onStatus(HttpStatus::is4xxClientError, response ->
							response.bodyToMono(String.class).map(BadRequestException::new)
						)
						.onStatus(HttpStatus::is5xxServerError, response ->
							response.bodyToMono(String.class).map(RuntimeException::new)
						)
						.bodyToMono(returnClass);
	}


	public static <T> Mono<T> retrieveDeleteForMono(WebClient webClient,
													String url,
													HttpHeaders httpHeaders,
													MultiValueMap<String, String> paramMap,
													Class<T> returnClass) {
		if (httpHeaders == null) {
			httpHeaders = new HttpHeaders();
		}

		if (MapUtils.isEmpty(paramMap)) {
			paramMap = new LinkedMultiValueMap<>();
		}

		HttpHeaders finalHttpHeaders = httpHeaders;

		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(url)
														   .queryParams(paramMap);
		return webClient.mutate()
						.build()
						.delete()
						.uri(builder.build(false).toUriString())
						.headers(header -> header.addAll(finalHttpHeaders))
						.retrieve()
						.onStatus(HttpStatus::is4xxClientError, response ->
							response.bodyToMono(String.class).map(BadRequestException::new)
						)
						.onStatus(HttpStatus::is5xxServerError, response ->
							response.bodyToMono(String.class).map(RuntimeException::new)
						)
						.bodyToMono(returnClass);
	}
}
