package org.changsol.apps.members.dtos;

import lombok.Getter;
import lombok.Setter;

public class PrincipalDto {

	@Getter
	@Setter
	public static class Response {

		private Long id;

		private String phone;

		private String name;
	}
}
