package common.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import common.entity.Chamado;
import common.entity.PessoaJuridica;
import common.entity.Usuario;
import common.exception.BusinessException;

public interface ObserverUsuario extends Remote{
	
	public void notificarTempoExcedido() throws RemoteException;
	
	public void encerrarClient() throws RemoteException;
	
	public boolean autenticar(Usuario usuario) throws RemoteException, BusinessException;
	
	public void atualizarCliente() throws RemoteException;
	
	public void encerrarSessao() throws RemoteException;
	
	public void mostrarMensagem(String mensagem) throws RemoteException;
	
	public boolean checkServerStatus() throws RemoteException;
	
	public void criarChamado(Chamado chamado) throws RemoteException;
	
	public List<PessoaJuridica> pesquisarPJ(String descricao) throws RemoteException;
	
}
