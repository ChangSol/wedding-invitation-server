package org.changsol.filters;

import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;

@Slf4j
public class RequestWrapper extends HttpServletRequestWrapper {

	public RequestWrapper(HttpServletRequest request) {
		super(request);
	}

	@Override
	public String getRemoteAddr() {
		String ip = getClientIP(this);

		if (StringUtils.isEmpty(ip)) {
			ip = super.getRemoteAddr();
		}

		return ip;
	}

	@Override
	public String getRemoteHost() {
		try {
			return InetAddress.getByName(getRemoteAddr()).getHostName();
		} catch (UnknownHostException e) {
			return getRemoteAddr();
		}
	}

	public static String getClientIP(HttpServletRequest request) {
		String ip = request.getHeader("X-Real-IP");

		if ("127.0.0.1".equals(ip) || "::1".equals(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = null;
		}

		if (StringUtils.isEmpty(ip)) {
			ip = request.getHeader("X-Forwarded-For");

			if (StringUtils.contains(ip, ",")) {
				ip = StringUtils.split(ip, ",")[0];
			}

			if ("127.0.0.1".equals(ip) || "::1".equals(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = null;
			}
		}

		if (StringUtils.isEmpty(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
			if ("127.0.0.1".equals(ip) || "::1".equals(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = null;
			}
		}

		if (StringUtils.isEmpty(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
			if ("127.0.0.1".equals(ip) || "::1".equals(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = null;
			}
		}
		if (StringUtils.isEmpty(ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
			if ("127.0.0.1".equals(ip) || "::1".equals(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = null;
			}
		}
		if (StringUtils.isEmpty(ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
			if ("127.0.0.1".equals(ip) || "::1".equals(ip) || "unknown".equalsIgnoreCase(ip)) {
				ip = null;
			}
		}
		return ip;
	}
//	public static String getClientIP(HttpServletRequest request) {
//		String ip = request.getHeader("X-Real-IP");
//
//		log.info("=====================RealIPRequestWrapper START=====================");
//		log.info("=====================X-Real-IP : " + ip);
//		if ("127.0.0.1".equals(ip) || "::1".equals(ip) || "unknown".equalsIgnoreCase(ip)) {
//			ip = null;
//		}
//
//		if (StringUtils.isEmpty(ip)) {
//			ip = request.getHeader("X-Forwarded-For");
//
//			if (StringUtils.contains(ip, ",")) {
//				ip = StringUtils.split(ip, ",")[0];
//			}
//
//			log.info("=====================X-Forwarded-For : " + ip);
//			if ("127.0.0.1".equals(ip) || "::1".equals(ip) || "unknown".equalsIgnoreCase(ip)) {
//				ip = null;
//			}
//		}
//
//		if (StringUtils.isEmpty(ip)) {
//			ip = request.getHeader("Proxy-Client-IP");
//			log.info("=====================Proxy-Client-IP : " + ip);
//			if ("127.0.0.1".equals(ip) || "::1".equals(ip) || "unknown".equalsIgnoreCase(ip)) {
//				ip = null;
//			}
//		}
//
//		if (StringUtils.isEmpty(ip)) {
//			ip = request.getHeader("WL-Proxy-Client-IP"); // 웹로직
//			log.info("=====================WL-Proxy-Client-IP : " + ip);
//			if ("127.0.0.1".equals(ip) || "::1".equals(ip) || "unknown".equalsIgnoreCase(ip)) {
//				ip = null;
//			}
//		}
//		if (StringUtils.isEmpty(ip)) {
//			ip = request.getHeader("HTTP_CLIENT_IP");
//			log.info("=====================HTTP_CLIENT_IP : " + ip);
//			if ("127.0.0.1".equals(ip) || "::1".equals(ip) || "unknown".equalsIgnoreCase(ip)) {
//				ip = null;
//			}
//		}
//		if (StringUtils.isEmpty(ip)) {
//			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
//			log.info("=====================HTTP_X_FORWARDED_FOR : " + ip);
//			if ("127.0.0.1".equals(ip) || "::1".equals(ip) || "unknown".equalsIgnoreCase(ip)) {
//				ip = null;
//			}
//		}
//
//		log.info("=====================RealIPRequestWrapper END=====================");
//
//		return ip;
//	}

}
