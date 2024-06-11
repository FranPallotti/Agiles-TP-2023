package isi.agiles.logica;

import isi.agiles.dao.UsuarioDAO;
import isi.agiles.dto.UsuarioDTO;
import isi.agiles.entidad.Usuario;
import isi.agiles.excepcion.*;

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
    
    public void altaUsuario(UsuarioDTO dto)throws UsernameNoUnicoException{
        
        if(!this.usernameEsUnico(dto)){
            throw new UsernameNoUnicoException();
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


}
