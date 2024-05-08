package tghtechnology.tiendavirtual.Utils.izipay.Objects.Response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class IzipayResponse<T> {

	private String webService;
	private String version;
	private String applicationVersion;
	private String status;
	private T answer;
	private String ticket;
	private String serverDate;
	private String applicationProvider;
	private Object metadata;
	private String mode;
	private String serverUrl;
	private String _type;
	
}
