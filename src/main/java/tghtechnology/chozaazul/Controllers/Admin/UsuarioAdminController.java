package tghtechnology.chozaazul.Controllers.Admin;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import tghtechnology.chozaazul.Models.Enums.TipoCargo;
import tghtechnology.chozaazul.Services.UsuarioService;
import tghtechnology.chozaazul.Utils.Security.Interfaces.Empleado;
import tghtechnology.chozaazul.Utils.Security.Interfaces.Gerente;
import tghtechnology.chozaazul.dto.Usuario.UsuarioDTOForInsert;
import tghtechnology.chozaazul.dto.Usuario.UsuarioDTOForList;
import tghtechnology.chozaazul.dto.Usuario.UsuarioDTOForModify;

@RestController
@RequestMapping("/admin/usuario")
public class UsuarioAdminController {

    @Autowired
    private UsuarioService userService;

    TipoCargo tipo = TipoCargo.EMPLEADO;
    
    @Empleado
    @GetMapping
    public ResponseEntity<List<UsuarioDTOForList>> listaUsuarios(){
            List<UsuarioDTOForList> userList = userService.listarUsuarios();
            return ResponseEntity.status(HttpStatus.OK).body(userList);
    }
    
    @Gerente
    @PostMapping
    public ResponseEntity<UsuarioDTOForList> crearUsuario(@Valid @RequestBody UsuarioDTOForInsert usuario){

            UsuarioDTOForList u = userService.crearUsuario(usuario);
            return ResponseEntity.status(HttpStatus.CREATED).body(u);
    }

    @Gerente
    @PutMapping("/{id}")
    public ResponseEntity<Void> actualizarUsuario(@PathVariable Integer id, @Valid @RequestBody UsuarioDTOForModify body){
        if(body.getUsername() == null || body.getUsername().strip().equals("")){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        userService.actualizarUsuario(id, body);
        return ResponseEntity.status(HttpStatus.OK).build();    
    }    

    @Gerente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer id){
            userService.eliminarUsuario(id);
            return ResponseEntity.status(HttpStatus.OK).build();
    }
}
