package tghtechnology.tiendavirtual.Services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import tghtechnology.tiendavirtual.Enums.SettingType;
import tghtechnology.tiendavirtual.Models.Setting;
import tghtechnology.tiendavirtual.Repository.SettingsRepository;

/**
 * Servicio base para obtener las configuraciones básicas de la página.
 * <p>
 * Los datos de las configuraciones se guardan en un mapa se hash local respaldado 
 * por una tabla en la base de datos.
 * <p>
 * Al iniciarse por primera vez, tanto la base de datos como el mapa local se poblan 
 * con datos por defecto. Para cambiar estos datos luego de ser asignados se utiliza 
 * {@link}
 */
@Slf4j
@Service
public class SettingsService implements ApplicationListener<ApplicationReadyEvent>{

	@Autowired
	private SettingsRepository setRepository;
	
	private final Map<String, Setting> settings = new HashMap<>();

	public SettingsService() {		
		// Inicializar mapa de settings con valores iniciales
		addSetting("facturacion.igv"			, "18"			, SettingType.INT);
		addSetting("facturacion.antes_de_igv"	, "true"		, SettingType.BOOL);
		addSetting("facturacion.serie_boleta"	, "1"			, SettingType.INT, false);
		addSetting("facturacion.serie_factura"	, "1"			, SettingType.INT, false);
		
		addSetting("notificaciones.intervalo"	, "3"			, SettingType.INT);
		addSetting("notificaciones.email"		, ""			, SettingType.STRING);
		
		addSetting("seguridad.lockout_user"		, "30"			, SettingType.INT);
		addSetting("seguridad.attempts_user"	, "5"			, SettingType.INT);
		addSetting("seguridad.lockout_ip"		, "1440"		, SettingType.INT);
		addSetting("seguridad.attempts_ip"		, "16"			, SettingType.INT);
		addSetting("seguridad.token_duration"	, "24"			, SettingType.INT);
		
		addSetting("company.ruc"				, "20000000000"	, SettingType.STRING);
		addSetting("company.razon_social"		, "COMPAÑIA"	, SettingType.STRING);
		addSetting("company.nombre_comercial"	, "NOMBRE"		, SettingType.STRING);
		addSetting("company.email"				, "exm@comp.com", SettingType.STRING);
		addSetting("company.telefono"			, "+51999999999", SettingType.STRING);
		
		addSetting("company.ubigeo"				, ""			, SettingType.STRING);
		addSetting("company.departamento"		, "LIMA"		, SettingType.STRING);
		addSetting("company.provincia"			, "LIMA"		, SettingType.STRING);
		addSetting("company.distrito"			, "LIMA"		, SettingType.STRING);
		addSetting("company.direccion"			, "MI DIRECCION", SettingType.STRING);
		
		addSetting("apisperu.url"				, "https://facturacion.apisperu.com/api/v1"	, SettingType.STRING);
		addSetting("apisperu.token"				, "mi token"								, SettingType.STRING);
		
		addSetting("cloudinary.url"				, "cloudinary://"	, SettingType.STRING);
		addSetting("cloudinary.directory"		, "mi_directorio/"	, SettingType.STRING);
		
	}
	
	/**
	 * Obtiene un Setting sin importar su tipo.
	 * @param key El nombre del setting.
	 * @return El setting encontrado.
	 * @throws NoSuchElementException Si el nombre no se corresponde 
	 * con ningún setting.
	 */
	public String get(String key) {
		Setting set = getSetting(key, SettingType.OTHER);
		return set.getValue();
	}
	
	/**
	 * Obtiene un setting de tipo {@link java.lang.String}.
	 * @param key El nombre del setting.
	 * @return El setting encontrado.
	 * @throws NoSuchElementException Si el nombre no se corresponde 
	 * con ningún setting.
	 * @throws IllegalArgumentException Si el setting no es de tipo String.
	 */
	public String getString(String key) {
		Setting set = getSetting(key, SettingType.STRING);
		return set.getValue();
	}
	
	/**
	 * Obtiene un setting de tipo {@link java.lang.Boolean}.
	 * @param key El nombre del setting.
	 * @return El setting encontrado.
	 * @throws NoSuchElementException Si el nombre no se corresponde 
	 * con ningún setting.
	 * @throws IllegalArgumentException Si el setting no es de tipo Boolean.
	 */
	public Boolean getBoolean(String key) {
		Setting set = getSetting(key, SettingType.BOOL);
		return Boolean.valueOf(set.getValue());
	}
	
	/**
	 * Obtiene un setting de tipo {@link java.lang.Integer}.
	 * @param key El nombre del setting.
	 * @return El setting encontrado.
	 * @throws NoSuchElementException Si el nombre no se corresponde 
	 * con ningún setting.
	 * @throws IllegalArgumentException Si el setting no es de tipo Integer.
	 */
	public Integer getInt(String key) {
		Setting set = getSetting(key, SettingType.INT);
		return Integer.valueOf(set.getValue());
	}
	
	/**
	 * Obtiene un setting de tipo {@link java.math.BigDecimal}.
	 * @param key El nombre del setting.
	 * @return El setting encontrado.
	 * @throws NoSuchElementException Si el nombre no se corresponde 
	 * con ningún setting.
	 * @throws IllegalArgumentException Si el setting no es de tipo BigDecimal.
	 */
	public BigDecimal getDecimal(String key) {
		Setting set = getSetting(key, SettingType.DECIMAL);
		
		String[] decimalBuilder = set.getValue().split(",");
		Long unscaled = Long.valueOf(decimalBuilder[0]);
		Integer scale = Integer.valueOf(decimalBuilder[1]);
		
		return BigDecimal.valueOf(unscaled, scale);
	}
	
	/**
	 * Obtiene un setting de un tipo específico.
	 * @param key El nombre del setting.
	 * @param type El tipo de setting que se requiere.
	 * @return El setting encontrado.
	 * @throws NoSuchElementException Si el nombre no se corresponde 
	 * con ningún setting.
	 * @throws IllegalArgumentException Si el setting no es del tipo proporcionado.
	 */
	public Setting getSetting(String key, SettingType type) {
		Setting set = settings.get(key);
		
		if(set == null)
			throw new NoSuchElementException("No existe un setting con el nombre [" + key + "].");
		if(set.getType() != type && type != SettingType.OTHER)
			throw new IllegalArgumentException("El setting solicitado no es de tipo " + type + ".");
		
		return set;
	}
	
	
	/**
	 * Inicializa la base de datos en caso no exista y la llena con los datos por defecto.
	 * <p>
	 * Luego llena el mapa privado acorde con los datos recuperados de la base de datos.
	 */
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		List<Setting> s = StreamSupport.stream(setRepository.findAll().spliterator(), false).collect(Collectors.toList());

		if(s.isEmpty()) {
			// Rellenar base de datos si está vacía
			
		}
		
		// Rellenar el mapa privado con los settings
		s.forEach(ss -> settings.put(ss.getKey(), new Setting(ss.getKey(), ss.getValue(), ss.getValue(), ss.getType(), ss.isEditable())));
	}
	
	private void addSetting(String key, String val, SettingType type) {
		addSetting(key, val, type, true);
	}
	
	private void addSetting(String key, String val, SettingType type, boolean editable) {
		settings.put(key, new Setting(key, val, val, type, editable));
	}
	
	
}
