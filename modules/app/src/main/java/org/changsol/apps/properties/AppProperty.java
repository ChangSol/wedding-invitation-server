package org.changsol.apps.properties;

import javax.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@Configuration
@ConfigurationProperties("app")
public class AppProperty {

	@NotEmpty
	private String name;

	@NotEmpty
	private String host;

	@NotEmpty
	private String url;

	private String version = "0.0.1";

	@Value("${spring.profiles.active:Unknown}")
	private String activeProfile;

	private String localUrl;

	private Oauth oauth = new Oauth();

	public String getOauthUrl() {
		return (StringUtils.isEmpty(this.localUrl) ? this.url : this.localUrl) + "/oauth/token";
	}

	@Getter
	@Setter
	public static class Oauth {

		private boolean enabled;
		private String clientId;
		private String clientSecret;
		private String tokenSigningKey;
		private int tokenValiditySeconds;
		private int refreshTokenValiditySeconds;
	}

}
