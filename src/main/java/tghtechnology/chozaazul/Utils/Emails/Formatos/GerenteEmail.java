package tghtechnology.chozaazul.Utils.Emails.Formatos;

import org.springframework.core.io.ByteArrayResource;

import tghtechnology.chozaazul.Models.Pedido;
import tghtechnology.chozaazul.Utils.TghUtils;
import tghtechnology.chozaazul.Utils.Emails.BaseEmail;


public class GerenteEmail extends BaseEmail{
	
	/**
	 * Constructor para un Email que se envía al gerente del negocio
	 * @Param emailGerente La dirección de correo del gerente
	 * @param pedido El pedido sobre el cual se basará el correo
	 * @param archivo_pdf El PDF de la boleta/factura de compra
	 */
	public GerenteEmail(String emailGerente, Pedido pedido, byte[] archivo_pdf) {
		super();
		
		destinatario = emailGerente;
		asunto = "Choza Azul - REPORTE DE VENTA";
		addAdjunto("Boleta-" + pedido.getId_pedido() + "_" + pedido.getFecha_pedido().format(TghUtils.shortFormatter) + ".pdf", 
					new ByteArrayResource(archivo_pdf));
		
		addPlaceholder("nombre-cliente", pedido.getCliente().getNombre());
		addPlaceholder("tipo-doc", pedido.getCliente().getTipo_documento());
		addPlaceholder("num-doc", pedido.getCliente().getNumero_documento());
		addPlaceholder("precio-total", pedido.getPrecio_total());

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
				+ "Nuevo pedido Ingresado\r\n"
				+ "</p>\r\n"
				+ "</td>\r\n"
				+ "</tr>\r\n"
				+ "<tr>\r\n"
				+ "                <td style=\" color: black; font-size: 1.5em; text-align: center; margin-top: 10px;\">\r\n"
				+ "                    <p>\r\n"
				+ "                        Pedido de <strong> ${nombre-cliente}</strong> (${tipo-doc}: ${num-doc})\r\n"
				+ "                    </p>\r\n"
				+ "                </td>\r\n"
				+ "            </tr>\r\n"
				+ "            <tr>\r\n"
				+ "                <td>\r\n"
				+ "                    <div class=\"body-message\" style=\"margin: 20px;\">                        \r\n"
				+ "							${CONTENIDO-EMAIL}"		
				+ "                    </div>\r\n"
				+ "                </td>\r\n"
				+ "            </tr>\r\n"
				+ "        </tbody>\r\n"
				+ "    </table>\r\n"
				+ "</body>\r\n"
				+ "\r\n"
				+ "</html>"
				;
		
		contenido =
				  "                        <table style=\"border-collapse: collapse; width: 100%;\">\r\n"
				+ "                            <tbody>\r\n"
				+ "                                    <tr style=\"margin-top: 10px;\">  \r\n"
				+ "                                        <td style=\"font-weight: bold; padding: 10px; margin-right: 20px; margin-top: 40px; font-size: 20px;\">Precio Total del pedido</td>\r\n"
				+ "                                        <td style=\"padding: 8px; margin-right: 20px; margin-top: 10px; font-size: 20px; font-weight: 600;\">S/. ${precio-total}</td>\r\n"
				+ "                                    </tr>\r\n"
				+ "                            </tbody>\r\n"
				+ "                        </table>\r\n";
		
	}

}
