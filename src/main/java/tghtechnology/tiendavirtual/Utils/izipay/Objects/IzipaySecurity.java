package tghtechnology.tiendavirtual.Utils.izipay.Objects;

import java.util.HashMap;
import java.util.Map;

import lombok.Getter;
import lombok.Setter;
import tghtechnology.tiendavirtual.Utils.ApisPeru.Enums.TipoDocIdentidad;
import tghtechnology.tiendavirtual.Utils.izipay.Objects.Enum.Banco;


@Getter
@Setter
public class IzipaySecurity {

	private String email;
	private String telefono;
	private TipoDocIdentidad tipo_doc;
	private String numero_doc;
	private Boolean enviado;
	private String cod_local;
	private String nombre_negocio;
	private Boolean delivery;
	private Banco banco_negocio;
	
	/**
	 * Constructor de objeto de seguridad de IziPay. Ningún campo es obligatorio,
	 *  pero se recomienda llenarlos todos para reducir el riesgo de fraude.
	 * @param email Dirección de Email del cliente
	 * @param telefono Número telefónico del cliente
	 * @param tipo_doc Tipo de documento por el que se identifica el cliente
	 * @param numero_doc Número de documento de identificación del cliente.
	 * @param enviado Si el producto/servicio ya ha sido enviado al momento de la compra o no.
	 * @param cod_local El código de local de la empresa.
	 * @param nombre_negocio La razon social / nombre del negocio.
	 * @param delivery Si la compra incluye delivery o no.
	 * @param banco_negocio El banco al cual se llegará a transferir el dinero de la compra.
	 */
	public IzipaySecurity(String email,
						  String telefono,
						  TipoDocIdentidad tipo_doc,
						  String numero_doc,
						  Boolean enviado,
						  String cod_local,
						  String nombre_negocio,
						  Boolean delivery,
						  Banco banco_negocio) {
		
		this.email = email;
		this.telefono = telefono;
		this.tipo_doc = tipo_doc;
		this.numero_doc = numero_doc;
		this.enviado = enviado;
		this.cod_local = cod_local;
		this.nombre_negocio = nombre_negocio;
		this.delivery = delivery;
		this.banco_negocio = banco_negocio;
	}
	
	public Map<String, Object> toMap(){
		Map<String, Object> map = new HashMap<>();
		
		if(email != null) map.put("cybersource_mdd_12", email);
		if(telefono != null) map.put("cybersource_mdd_13", telefono);
		if(tipo_doc != null && numero_doc != null) {
			String d = tipo_doc == TipoDocIdentidad.DNI ? "01" : (numero_doc.substring(0, 2));
			map.put("cybersource_mdd_14", String.format("%s-%s", d, numero_doc));
		}
		
		if(enviado != null) map.put("cybersource_mdd_30", enviado ? "Enviado" : "Pendiente");
		if(cod_local != null) map.put("cybersource_mdd_35", cod_local);
		if(nombre_negocio != null) map.put("cybersource_mdd_36", nombre_negocio);
		if(delivery != null) map.put("cybersource_mdd_37", delivery ? "DELIVERY" : "RECOJO EN TIENDA");
		if(banco_negocio != null) map.put("cybersource_mdd_39", banco_negocio.getValue().toString());
		
		return map;
	}
	
}
