package service.teste;

import java.rmi.RemoteException;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import common.entity.Chamado;
import common.entity.Cliente;
import common.entity.PessoaFisica;
import common.entity.Porte;
import common.entity.Prioridade;
import common.entity.StatusChamado;
import common.entity.TipoChamado;
import common.entity.TipoFalha;
import common.entity.Usuario;

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
				
				Cliente cliente = new PessoaFisica("Rua x", new Porte(Porte.PF), null, "André", "Macho",
						new Date(), new Long("0123456789"), "4121212121");
				Usuario usuario = new Usuario("Vane Iwa", "senha");
				
				//Chamado chamado = new Chamado(new Date(), new TipoChamado(TipoChamado.URGENTE),cliente);
				//Numero do Chamado
				int indiceChamado = (int) ((int) 1 + (Math.random() * 100));
				
				//Tipo de Chamado
				List<StatusChamado> vStatusChamado = new ArrayList<StatusChamado>();
				vStatusChamado.add(new StatusChamado(StatusChamado.ABERTO));
				vStatusChamado.add(new StatusChamado(StatusChamado.AGUARDANDO_CLIENTE));
				vStatusChamado.add(new StatusChamado(StatusChamado.AGENDADO));
				vStatusChamado.add(new StatusChamado(StatusChamado.PENDENTE));
				int indiceStatusChamado = (int)(Math.random() * vStatusChamado.size());
				StatusChamado tempStatusChamado = vStatusChamado.get(indiceStatusChamado);
				
				//Tipo de Chamado
				List<TipoChamado> vTipoChamado = new ArrayList<TipoChamado>();
				vTipoChamado.add(new TipoChamado(TipoChamado.INFORMATIVO));
				vTipoChamado.add(new TipoChamado(TipoChamado.NORMAL));
				vTipoChamado.add(new TipoChamado(TipoChamado.PROGRAMADO));
				vTipoChamado.add(new TipoChamado(TipoChamado.URGENTE));
				int indiceTipoChamado = (int)(Math.random() * vTipoChamado.size());
				TipoChamado tempTipoChamado = vTipoChamado.get(indiceTipoChamado);
				
				//Tipo de Falha
				List<TipoFalha> vTipoFalha = new ArrayList<TipoFalha>();
				vTipoFalha.add(new TipoFalha(TipoFalha.SOFTWARE));
				vTipoFalha.add(new TipoFalha(TipoFalha.HARDWARE));
				vTipoFalha.add(new TipoFalha(TipoFalha.DUVIDA));
				int indiceTipoFalha = (int)(Math.random() * vTipoFalha.size());
				TipoFalha tempTipoFalha = vTipoFalha.get(indiceTipoFalha);
				
				//Descrição
				String descricao = null;
				switch (indiceTipoFalha) {
				case 0:			
					descricao = "Programa não abre";
					break;
				case 1:
					descricao = "Computador não ligae";
					break;
				case 2:
					descricao = "Não sei ligar o computador";
					break;
				default:
					break;
				}
				
				Chamado chamado = new Chamado(indiceChamado, new Date(), null, descricao, tempStatusChamado, 
						new Prioridade(2, 1, new Date()), tempTipoChamado, cliente, tempTipoFalha,
						"Nome do Responsavel", usuario, null, "4121212121");
								
				serviceChamado.cadastrarChamado(chamado);
				Utils.printMsg(this.getClass().getName(), "Cadastrando novo chamado.");
				
				// dorme um pouco
				long time = new Random().nextInt(5000);
				Thread.sleep(time);

			} catch (InterruptedException e) {
				break;
			} catch (RemoteException e) {
				// TODO criar exception de infra
				Utils.printMsg(this.getClass().getName(), "Erro no objeto remoto, " + e);
			}
		}
	}
	
	public static void main(String[] args) {
		new ProdutoraChamados().start();
	}
}
