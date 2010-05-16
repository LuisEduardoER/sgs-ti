package service.teste;

import java.rmi.RemoteException;
import java.util.Date;
import java.util.Random;
import common.entity.Chamado;
import common.entity.Cliente;
import common.entity.PessoaFisica;
import common.entity.Porte;
import common.entity.TipoChamado;
import common.exception.BusinessException;
import common.remote.ServiceChamado;
import common.util.Utils;

public class ProdutoraChamados extends Thread {

	private ServiceChamado serviceChamado;
	
	public ProdutoraChamados() {
		try {
			serviceChamado = Utils.obterServiceChamado();
		} catch (BusinessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		abrirChamados();
	}
	
	private void abrirChamados(){
		while(!Thread.interrupted()){
			try {
				
				Cliente cliente = new PessoaFisica("Rua x", new Porte(Porte.PF),null,"André","Macho",new Date(),new Long("0123456789"));
				Chamado chamado = new Chamado(new Date(), new TipoChamado(TipoChamado.URGENTE),cliente);
				
				serviceChamado.cadastrarChamado(chamado);
				System.out.println("Cadastrando novo chamado.");
				
				// dorme um pouco
				long time = new Random().nextInt(5000);
				Thread.sleep(time);

			} catch (InterruptedException e) {
				break;
			} catch (RemoteException e) {
				// TODO criar exception de infra
				e.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args) {
		new ProdutoraChamados().start();
	}
}
