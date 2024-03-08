package tghtechnology.tiendavirtual.Utils.Emails.Formatos;

import org.springframework.core.io.ByteArrayResource;

import tghtechnology.tiendavirtual.Models.Pedido;
import tghtechnology.tiendavirtual.Models.Enums.TipoDelivery;
import tghtechnology.tiendavirtual.Utils.TghUtils;
import tghtechnology.tiendavirtual.Utils.Emails.BaseEmail;

public class ClienteEmail extends BaseEmail{

	
	/**
	 * Constructor para un Email que se envía al cliente
	 * @param destinatario La dirección de correo a la cual se debe enviar el email
	 * @param pedido El pedido sobre el cual se basará el correo
	 * @param archivo_pdf El PDF de la boleta/factura de compra
	 */
	public ClienteEmail(String destinatario, Pedido pedido, byte[] archivo_pdf) {
		super(destinatario, "Choza Azul - RECIBO DE COMPRA");
		
		addPlaceholder("nombre-cliente", pedido.getCliente().getNombre());
		addPlaceholder("precio-total", pedido.getPrecio_total());
		addPlaceholder("precio-delivery", pedido.getCosto_delivery());
		addAdjunto("Boleta-" + pedido.getId_pedido() + "_" + pedido.getFecha_pedido().format(TghUtils.shortFormatter) + ".pdf", 
				new ByteArrayResource(archivo_pdf));
		
		//TODO: set formato y contenido
		
		formato =
				  "<html lang=\"en\">\r\n"
				+ "\r\n"
				+ "<head>\r\n"
				+ "    <meta charset=\"UTF-8\">\r\n"
				+ "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\r\n"
				+ "    <title>Document</title>\r\n"
				+ "</head>\r\n"
				+ "\r\n"
				+ "<body>\r\n"
				+ "<table style=\"width: 600px; height: 250px; border-radius: 10px; font-family: Arial, Helvetica, sans-serif; border: 1px solid rgb(173, 169, 169);\">\r\n"
				+ "<tbody>\r\n"
				+ "<tr>\r\n"
				+ "<td style=\"background-color:#00B140; height: 80px; text-align: center; font-size: 1em; border-radius: 10px 10px 0px 0px; padding: 10px;\">\r\n"
				+ "<p style=\"color: white; font-size: 2em; padding: 0; margin: 0;\">\r\n"
				+ "¡Gracias por su compra!\r\n"
				+ "	</p>\r\n"
				+ "		</td>\r\n"
				+ "			</tr>\r\n"
				+ "				<tr>\n"
				+ "                <td style=\" color: black; font-weight: 700; font-size: 1.5em; text-align: center; margin-top: 10px;\">\r\n"
				+ "                    <p>\r\n"
				+ "                        Gracias por comprar en Choza Azul, ${nombre-cliente}\r\n"
				+ "                    </p>\r\n"
				+ "                </td>\r\n"
				+ "            </tr>\r\n"
				+ "            <tr>\r\n"
				+ "                <td>\r\n"
				+ "                    <div class=\"body-message\" style=\"margin: 20px;\">\r\n"
				+ "							${CONTENIDO-EMAIL}\r\n"
				+ "                    </div>\r\n"
				+ "                </td>\r\n"
				+ "            </tr>\r\n"
				+ "        </tbody>\r\n"
				+ "    </table>\r\n"
				+ "</body>\r\n"
				+ "\r\n"
				+ "</html>";
		
		final StringBuilder builder = new StringBuilder();
//		builder.append(" Gracias por comprar en Choza Azul! <br/>");
//		builder.append("Tu compra consiste en:<br/>");
		
		builder.append(
					  "<p class=\"title-body\" style=\"font-weight: bold; margin: 10px 0px;\">Detalle de la compra</p>\r\n"
					+ "\r\n"
					+ "<table style=\"border-collapse: collapse; width: 100%;\">\r\n"
					+ "	<tbody>\r\n");


		
		pedido.getDetallePedido().forEach(det -> {
			builder.append(
					  "		<tr>\r\n"
					+ "			<td style=\"padding: 8px; margin-right: 20px; margin-top: 10px;\">\r\n"
					+ "				" + det.getCantidad() + "\r\n"
					+ "			</td>\r\n"
					+ "			<td style=\"padding: 8px; margin-right: 20px; margin-top: 10px;\">"+ det.getPlato().getNombre_plato() +"</td>\r\n"
					+ "			<td style=\"padding: 8px; margin-right: 20px; margin-top: 10px;\">S/ " + det.getSub_total() + "</td>\r\n");
		});
		
		if(pedido.getTipo_delivery() != TipoDelivery.EN_TIENDA) {
			builder.append(
					  "		<tr>\r\n"
					+ "			<td style=\"padding: 8px; margin-right: 20px; margin-top: 10px;\"> </td>\r\n"
					+ "			<td style=\"padding: 8px; margin-right: 20px; margin-top: 10px;\"> Delivery: </td>\r\n"
					+ "			<td style=\"padding: 8px; margin-right: 20px; margin-top: 10px;\">S/ ${precio-delivery}</td>\r\n");
		}

		builder.append(
				  "		</tr>\r\n"
				+ "		<tr style=\"margin-top: 10px;\">\r\n"
				+ "			<td style=\"font-weight: bold; padding: 10px; margin-right: 20px; margin-top: 40px; font-size: 20px;\">Precio Total</td>\r\n"
				+ "			<td style=\"padding: 8px; margin-right: 20px; margin-top: 10px; font-size: 20px; font-weight: 600;\">S/. ${precio-total}</td>\r\n"
				+ "		</tr>\r\n" 
				+ "	</tbody>\r\n"
				+ "</table>"
			);
		
		
		contenido = builder.toString();
		
	}

}
