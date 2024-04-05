package tghtechnology.tiendavirtual.Services;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.AllArgsConstructor;
import tghtechnology.tiendavirtual.Enums.Mes;
import tghtechnology.tiendavirtual.Models.ReporteMensual;
import tghtechnology.tiendavirtual.Repository.ReporteRepository;
import tghtechnology.tiendavirtual.dto.Reporte.ReporteDTOForList;

@Service
@AllArgsConstructor
public class ReporteService {

    private ReporteRepository repRepository;

    /**
     * Lista todos los reportes de un año en específico
     * 
     * @return Una lista de los reportes en formato DTOForList
     */
    public Map<Mes, ReporteDTOForList> listarReportes (Integer anio){
        Map<Mes, ReporteDTOForList> repMap = new TreeMap<>();
        List<ReporteMensual> reps = repRepository.listarPorAnio(anio);
        
        // Obtener los registros que ya hayan en la base de datos
        reps.forEach( x -> {
            repMap.put(x.getId().getMonth(), new ReporteDTOForList().from(x));
        });
        // Generar los registros que no existan ya
        for(Mes mes : Mes.values()) {
        	if(!repMap.containsKey(mes)) {
        		repMap.put(mes, new ReporteDTOForList().from(generarReporte(mes.ordinal(), anio)));
        	}
        }
        
        return repMap;
    }
    
    @Transactional(rollbackFor = {})
    public ReporteMensual generarReporte(Integer mes,Integer anio) {
    	
    }
    
}