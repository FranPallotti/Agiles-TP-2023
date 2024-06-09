package isi.agiles.logica;

import isi.agiles.dao.UsuarioDAO;
import isi.agiles.dto.UsuarioDTO;
import isi.agiles.entidad.Usuario;
import isi.agiles.excepcion.*;

public class GestorUsuario {

    public static UsuarioDTO getUsuarioDTO(Usuario usuario){
        UsuarioDTO dto= new UsuarioDTO();
        dto.setApellido(usuario.getApellido());
        dto.setFechaNaciemiento(usuario.getFechaNacimiento());
        dto.setNombre(usuario.getNombre());
        dto.setMail(usuario.getMail());
        dto.setSexo(usuario.getSexo());
        dto.setTipoDoc(usuario.getTipoDoc());
        dto.setNumDoc(usuario.getNumDoc());
        dto.setNombreUsuario(usuario.getNombreUsuario());
        dto.setRol(usuario.getRol());
        return dto;

    }

    public static Usuario getUsuario(UsuarioDTO u) throws ObjetoNoEncontradoException{
        UsuarioDAO dao = new UsuarioDAO();
        Usuario usuario = dao.getByUsername(u.getNombreUsuario()).orElseThrow(()-> new ObjetoNoEncontradoException());
        return usuario;
    }

    public static Boolean usernameEsUnico(UsuarioDTO dto){
        Boolean ret =false;
        UsuarioDAO dao = new UsuarioDAO();
        if(dao.getByUsername(dto.getNombreUsuario()).isEmpty()){
            ret=true;
        }
        return ret;
        
    }
    
    public static void altaUsuario(UsuarioDTO dto)throws UsernameNoUnicoException{
        
        if(!GestorUsuario.usernameEsUnico(dto)){
            throw new UsernameNoUnicoException();
        }
        Usuario user= GestorUsuario.crearUsuario(dto);
        UsuarioDAO dao = new UsuarioDAO();
        dao.saveInstance(user);
    }

    public static Usuario crearUsuario(UsuarioDTO dto){
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


}
