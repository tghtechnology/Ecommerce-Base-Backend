package tghtechnology.tiendavirtual.Services;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.function.Function;
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
import tghtechnology.tiendavirtual.Utils.Exceptions.DataMismatchException;
import tghtechnology.tiendavirtual.Utils.Exceptions.IdNotFoundException;
import tghtechnology.tiendavirtual.dto.Setting.SettingDTOForInsert;
import tghtechnology.tiendavirtual.dto.Setting.SettingDTOForList;

/**
 * Servicio base para obtener las configuraciones básicas de la página.
 * <p>
 * Los datos de las configuraciones se guardan en un mapa se hash local respaldado 
 * por una tabla en la base de datos.
 * <p>
 * Al iniciarse por primera vez, tanto la base de datos como el mapa local se poblan 
 * con datos por defecto. Para cambiar estos datos luego de ser asignados se utiliza 
 * {@link #alterSetting(String, Object, SettingType) alterSetting}
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
		
		addSetting("notificaciones.intervalo"	, "3"				, SettingType.INT);
		addSetting("notificaciones.email"		, "exm@admin.com"	, SettingType.STRING);
		
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
	 * Lista todos los settings de la página
	 * @return Una lista de todos los settings en formato DTOForList
	 */
	public List<SettingDTOForList> listarSettings() {
		return settings.values()
				.stream()
				.map(ss -> new SettingDTOForList().from(ss))
				.collect(Collectors.toList());
	}
	
	/**
	 * Modifica un setting según su identificador
	 * @param mSet El setting a modificar en formato ForInsert
	 */
	public void modificarSetting(SettingDTOForInsert mSet) {
		Setting set = settings.get(mSet.getIdentificador());
		if(set == null) throw new IdNotFoundException("setting");
		
		 if(!validarValor(mSet.getValor(), set.getType()))
			 throw new DataMismatchException(mSet.getIdentificador(), "Valor no permitido");
		
		set.setBaseValue(mSet.getValor());
		settings.put(set.getId(), set);
		setRepository.save(set);
	}
	
	/**
	 * Modifica una lista de settings
	 * @param mSetsUna lista que contiene todos los settings a modificar en formato DTOForList
	 */
	public void modificarSettings(List<SettingDTOForInsert> mSets) {
		mSets.forEach( mSet -> {
			modificarSetting(mSet);
		});
	}
	
	public void restablecerSettings() {
		settings.entrySet().forEach(entry -> entry.getValue().setValor(entry.getValue().getBaseValue()));
		setRepository.saveAll(settings.values());
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
		return set.getBaseValue();
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
		return set.getBaseValue();
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
		return Boolean.valueOf(set.getBaseValue());
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
		return Integer.valueOf(set.getBaseValue());
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
		return new BigDecimal(set.getValor());
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
	 * Modifica el valor de un setting y propaga los cambios tanto
	 * en el mapa interno como en la base de datos.
	 * @param key El identificador del setting a modificar.
	 * @param value El valor nuevo para el setting.
	 * @param type El tipo de setting que es.
	 * @throws IllegalArgumentException Si el tipo proporcionado no concuerda con el tipo real
	 * del setting.
	 */
	public void alterSetting(String key, Object value, SettingType type) {
		Setting set = settings.get(key);
		if(set.getType() != type)
			throw new IllegalArgumentException("El setting solicitado no es de tipo " + type + ".");
		
		String str_val;
		
		switch(type) {
		case BOOL:
			str_val = ((Boolean)value).toString();
			break;
		case DECIMAL:
			BigDecimal bd = (BigDecimal)value;
			str_val = String.format("%d,%d", bd.unscaledValue(), bd.scale());
			break;
		case INT:
			str_val = ((Integer)value).toString();
			break;
		case STRING:
			str_val = (String)value;
			break;
		default:
			str_val = value.toString();
			break;
		}
		
		set.setBaseValue(str_val);
		settings.put(set.getId(), set);
		setRepository.save(set);
	}
	
	/**
	 * Inicializa la base de datos en caso no exista y la llena con los datos por defecto.
	 * <p>
	 * Luego llena el mapa privado acorde con los datos recuperados de la base de datos.
	 */
	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {

		Map<String, Setting> s = StreamSupport.stream(setRepository.findAll().spliterator(), false).collect(Collectors.toMap(ss -> ss.getId(),Function.identity()));

		log.info("Comprobando settings...");
		settings.entrySet().forEach(entry -> {
			if(!s.containsKey(entry.getKey())) {
				log.info(String.format("Setting [%s] faltante. Llenando con valor por defecto.", entry.getKey()));
				s.put(entry.getKey(), entry.getValue());
			} else {
				// Llenando mapa con valor de la base de datos
				Setting ss = s.get(entry.getKey());
				settings.put(ss.getId(), ss);
			}
		});
		
		setRepository.saveAll(settings.values());
		log.info("Finalizada comprobación de settings.");
	}
	
	private void addSetting(String key, String val, SettingType type) {
		addSetting(key, val, type, true);
	}
	
	private void addSetting(String key, String val, SettingType type, boolean editable) {
		settings.put(key, new Setting(key, val, val, type, editable));
	}
	
	private boolean validarValor(String val, SettingType type) {
		switch(type) {
		case BOOL:
			return SettingType.boolValues.contains(val);
		case DECIMAL:
			return SettingType.decimalPattern.matcher(val).matches();
		case INT:
			return SettingType.intPattern.matcher(val).matches();
		default:
			return true;
		}
		
	}
	
}
