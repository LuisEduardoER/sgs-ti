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
		TipoFalha t1 = new TipoFalha("Hardware");
		TipoFalha t2 = new TipoFalha("Software");
		TipoFalha t3 = new TipoFalha("Duvida");
		
		listFalha.add(t1);
		listFalha.add(t2);
		listFalha.add(t3);		
		
		return listFalha;
	}

	@Override
	public List<StatusChamado> procurarStatus() throws RemoteException {
		StatusChamado s1 = new StatusChamado("Aberto");
		StatusChamado s2 = new StatusChamado("Pendente");
		StatusChamado s3 = new StatusChamado("Aguardando Cliente");
		StatusChamado s4 = new StatusChamado("Agendado");
		StatusChamado s5 = new StatusChamado("Fechado");
		
		listStatus.add(s1);
		listStatus.add(s2);
		listStatus.add(s3);
		listStatus.add(s4);
		listStatus.add(s5);
		
		return listStatus;
	}
}
