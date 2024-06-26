package tghtechnology.tiendavirtual.Controllers.Api;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import tghtechnology.tiendavirtual.Enums.Mes;
import tghtechnology.tiendavirtual.Security.Interfaces.Gerente;
import tghtechnology.tiendavirtual.Services.ReporteService;
import tghtechnology.tiendavirtual.dto.Reporte.ReporteDTOForList;

@RequestMapping("/api/reporte")
@RestController
public class ReporteController {

	@Autowired
    private ReporteService repService;
	
	@Gerente
	@GetMapping
    public ResponseEntity<Map<Mes, ReporteDTOForList>> listar(@RequestParam(name = "year") @Valid 
    															@Min(value = 2000, message = "Año no permitido")
    															@Max(value = 3000, message = "Año no permitido")
    															Integer anio){
		Map<Mes, ReporteDTOForList> reportes = repService.listarReportes(anio);
        return ResponseEntity.status(HttpStatus.OK).body(reportes);
    }
	
	@Gerente
	@GetMapping("/xlsx")
	public ResponseEntity<byte[]> obtenerExcel(@RequestParam(name = "year") @Valid 
												@Min(value = 2000, message = "Año no permitido")
												@Max(value = 3000, message = "Año no permitido")
												Integer anio) throws IOException{
		
		Map<Mes, ReporteDTOForList> reportes = repService.listarReportes(anio);
		byte[] bitearray = repService.generarReporteExcel(reportes, anio);
        return ResponseEntity.status(HttpStatus.OK).body(bitearray);
		
	}
	
	@Gerente
	@GetMapping("/monthly")
    public ResponseEntity<ReporteDTOForList> listarPorMes(@RequestParam(name = "year") @Valid 
    															@Min(value = 2000, message = "Año no permitido")
    															@Max(value = 3000, message = "Año no permitido")
    															Integer anio,
    															@RequestParam(name = "month") Mes mes){
		ReporteDTOForList reportes = repService.listarReporte(anio, mes);
        return ResponseEntity.status(HttpStatus.OK).body(reportes);
    }

}