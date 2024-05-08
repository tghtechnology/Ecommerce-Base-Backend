package tghtechnology.tiendavirtual.Enums;

public enum EstadoPedido {
	
	PAGO_PENDIENTE("Pago pendiente"),
	FACTURADO("Pedido facturado"),
	PENDIENTE("Pedido pendiente"),
	ATENDIDO("Pedido atendido"),
	EN_TIENDA("Esperando entrega en tienda"),
	ENTREGA("Por entregar"),
	COMPLETADO("Pedido completado"),
	CANCELADO("Pedido cancelado")
	;

	
	private final String label;
	
	private EstadoPedido(String label) {
		this.label = label;
	}
	
	public String getLabel() {
		return this.label;
	}
	
	public EstadoPedido fromString(String s) {
		for(EstadoPedido ep : values()) {
			if(ep.label.equals(s))
				return ep;
		}
		throw new IllegalArgumentException("El string proporcionado no se corresponde con ningun estado de pedido");
	}
	
}
