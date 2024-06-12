package isi.agiles.logica;

import java.time.LocalDate;

import isi.agiles.dao.UsuarioDAO;
import isi.agiles.dto.UsuarioDTO;
import isi.agiles.entidad.TipoDoc;
import isi.agiles.entidad.Usuario;
import isi.agiles.excepcion.*;
import isi.agiles.util.DatosInvalidosException;

public class GestorUsuario {

    private UsuarioDAO usuarioDao = new UsuarioDAO();

    public UsuarioDTO getUsuarioDTO(Usuario usuario){
        UsuarioDTO dto= new UsuarioDTO();
        dto.setApellido(usuario.getApellido());
        dto.setFechaNacimiento(usuario.getFechaNacimiento());
        dto.setNombre(usuario.getNombre());
        dto.setMail(usuario.getMail());
        dto.setSexo(usuario.getSexo());
        dto.setTipoDoc(usuario.getTipoDoc());
        dto.setNumDoc(usuario.getNumDoc());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setRol(usuario.getRol());
        return dto;
    }

    public Usuario getUsuario(UsuarioDTO u) throws ObjetoNoEncontradoException{
        Usuario usuario = usuarioDao.getByUsername(u.getNombreUsuario()).orElseThrow(()-> new ObjetoNoEncontradoException());
        return usuario;
    }

    public Boolean usernameEsUnico(UsuarioDTO dto){
        Boolean ret =false;
        UsuarioDAO dao = new UsuarioDAO();
        if(dao.getByUsername(dto.getNombreUsuario()).isEmpty()){
            ret=true;
        }
        return ret;
        
    }
    
    public void altaUsuario(UsuarioDTO dto)throws UsernameNoUnicoException,DatosInvalidosException{
        
        if(!this.usernameEsUnico(dto)){
            throw new UsernameNoUnicoException();
        }
        else if(this.datosInvalidos(dto)){
            throw new DatosInvalidosException();
        }
        Usuario user= this.crearUsuario(dto);
        UsuarioDAO dao = new UsuarioDAO();
        dao.saveInstance(user);
    }

    public Usuario crearUsuario(UsuarioDTO dto){
        Usuario user= new Usuario();
        user.setApellido(dto.getApellido());
        user.setFechaNacimiento(dto.getFechaNaciemiento());
        user.setMail(dto.getMail());
        user.setNombre(dto.getNombre());
        user.setNombreUsuario(dto.getNombreUsuario());
        user.setNumDoc(dto.getNumDoc());
        user.setRol(dto.getRol());
        user.setSexo(dto.getSexo());
        user.setTipoDoc(dto.getTipoDoc());
        return user;
    }

    public  Boolean dniInvalido(UsuarioDTO u){
        Boolean invalido = false;

        if(u.getTipoDoc() != null && u.getNumDoc() != null){
            switch (u.getTipoDoc()) {
                case DNI:
                    if(!u.getNumDoc().matches("^\\d{8}$") || u.getNumDoc().isBlank() || u.getNumDoc().isEmpty()){
                        invalido=true;
                    }
                                        
                break;
                case PASAPORTE:
                    if(!u.getNumDoc().matches("^[a-zA-Z]{3}\\d{6}$") || u.getNumDoc().isBlank() || u.getNumDoc().isEmpty() ){
                        invalido=true;
                    }
                break;
                default:
                    invalido=true;
            }
        }
        return invalido;
    }
    public Boolean datosInvalidos(UsuarioDTO u){
        Boolean invalidos = false;
        invalidos |=this.nombreInvalido(u);
        invalidos |=this.apellidoInvalido(u);
        invalidos |=this.fechaNacimientoInvalido(u);
        invalidos |=this.mailInvalido(u);
        invalidos |=this.sexoInvalido(u);
        invalidos |=this.dniInvalido(u);
        invalidos |=this.nombreUsuarioInvalido(u);
        return invalidos;
    }

    public Boolean nombreInvalido(UsuarioDTO u){
        Boolean invalido = false;
        if(u.getNombre() == null || u.getNombre().matches("^\\s+$") || u.getNombre().isBlank() || u.getNombre().isEmpty()|| u.getNombre().length()>32){
            invalido = true;
        }
        
        
        return invalido;
    }
    public Boolean apellidoInvalido(UsuarioDTO u){
        Boolean invalido =  false;
        if(u.getApellido() == null|| u.getApellido().matches("^\\s+$") || u.getApellido().isEmpty() || u.getApellido().isBlank() || u.getApellido().length()>32){

            invalido = true;
        }
        return invalido;
    }

    public Boolean fechaNacimientoInvalido(UsuarioDTO u){
        Boolean invalido =false;
        if(u.getFechaNaciemiento() == null || u.getFechaNaciemiento().isAfter(LocalDate.now().minusYears(18))){
            invalido = true;
        }
        return invalido;
    }

    public Boolean mailInvalido(UsuarioDTO u){
        Boolean invalido = false;
        if (u.getMail() == null || u.getMail().isEmpty() || u.getMail().isBlank() || !u.getMail().matches("^[\\w.-]+@(gmail|hotmail)\\.com$")){
            invalido = true;
            
        }
       return invalido;
    }

    public Boolean sexoInvalido(UsuarioDTO u){
        Boolean invalido = false;
        if(u.getSexo()== null){
            invalido=true;
        }
        return invalido;
    }

    public Boolean nombreUsuarioInvalido(UsuarioDTO u){
        Boolean invalido =  false;
        if (u.getNombreUsuario() == null || u.getNombreUsuario().matches("^\\s+$") || u.getNombreUsuario().isEmpty()|| u.getNombreUsuario().length()>16 || u.getNombreUsuario().matches("^(\\s+[a-zA-Z0-9]+|[a-zA-Z0-9]+\\s+[a-zA-Z0-9]+|[a-zA-Z0-9]+\\s+)$")){
            
            invalido=true;
            
        } 
        return invalido;
    }





}
