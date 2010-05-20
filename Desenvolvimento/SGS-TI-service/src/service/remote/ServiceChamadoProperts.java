package service.remote;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import common.entity.StatusChamado;
import common.entity.TipoFalha;
import common.remote.ServiceChamadoItens;

public class ServiceChamadoProperts implements ServiceChamadoItens 
{
	private List<TipoFalha> listFalha;
	private List<StatusChamado> listStatus;
	

	public ServiceChamadoProperts()
	{
		listFalha = new ArrayList<TipoFalha>();
		listStatus = new ArrayList<StatusChamado>();
	}
	
	@Override
	public List<TipoFalha> procurarFalha() throws RemoteException {
		TipoFalha t1 = new TipoFalha(TipoFalha.DUVIDA);
		TipoFalha t2 = new TipoFalha(TipoFalha.HARDWARE);
		TipoFalha t3 = new TipoFalha(TipoFalha.SOFTWARE);
		
		listFalha.add(t1);
		listFalha.add(t2);
		listFalha.add(t3);		
		
		return listFalha;
	}

	@Override
	public List<StatusChamado> procurarStatus() throws RemoteException {
		StatusChamado s1 = new StatusChamado(StatusChamado.ABERTO);
		StatusChamado s2 = new StatusChamado(StatusChamado.AGENDADO);
		StatusChamado s3 = new StatusChamado(StatusChamado.AGUARDANDO_CLIENTE);
		StatusChamado s4 = new StatusChamado(StatusChamado.FINALIZADO);
		StatusChamado s5 = new StatusChamado(StatusChamado.PENDENTE);
		
		listStatus.add(s1);
		listStatus.add(s2);
		listStatus.add(s3);
		listStatus.add(s4);
		listStatus.add(s5);
		
		return listStatus;
	}
}
