package service.teste;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import common.entity.Chamado;
import common.entity.PessoaJuridica;
import common.entity.Porte;
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
				
				PessoaJuridica pj = new PessoaJuridica("rua xv, 123", new Porte("MEDIA"), null, "Gurski IT", "Gurski IT", 123456789);
				pj.setCodigo(2);
				Usuario usuario = new Usuario("andrens","teste");
				usuario.setNome("Andre");
				usuario.setCodigo(1);
				
				//Tipo de Chamado
				StatusChamado status = new StatusChamado(StatusChamado.ABERTO);
				status.setCodigo(1);
				
				Random random = new Random();
				
				//Tipo de Chamado
				List<TipoChamado> vTipoChamado = new ArrayList<TipoChamado>();
				vTipoChamado.add(new TipoChamado(TipoChamado.PREVENTIVO));
				vTipoChamado.add(new TipoChamado(TipoChamado.NORMAL));
				vTipoChamado.add(new TipoChamado(TipoChamado.URGENTE));

				int indiceTipoChamado = random.nextInt(3);
				TipoChamado tempTipoChamado = vTipoChamado.get(indiceTipoChamado);
				tempTipoChamado.setCodigo(indiceTipoChamado+1);

				//Tipo de Falha
				List<TipoFalha> vTipoFalha = new ArrayList<TipoFalha>();
				vTipoFalha.add(new TipoFalha(TipoFalha.HARDWARE));
				vTipoFalha.add(new TipoFalha(TipoFalha.SOFTWARE));
				vTipoFalha.add(new TipoFalha(TipoFalha.INFORMATIVO));
				vTipoFalha.add(new TipoFalha(TipoFalha.DUVIDA));
				
				int indiceTipoFalha = random.nextInt(4);
				TipoFalha tempTipoFalha = vTipoFalha.get(indiceTipoFalha);
				tempTipoFalha.setCodigo(indiceTipoFalha+1);
				
				//Descrição
				String descricao = null;
				switch (indiceTipoFalha) {
				case 0:			
					descricao = "Programa não abre";
					break;
				case 1:
					descricao = "Computador não liga";
					break;
				case 2:
					descricao = "Não sei ligar o computador";
					break;
				default:
					break;
				}
				
				Chamado c = new Chamado(new Date(), null, descricao, usuario, null, tempTipoChamado, tempTipoFalha, pj, status);
				c.setResponsavel("Vanessa Japa");
				c.setContato("4133332222");
				c.pritChamado();
				serviceChamado.cadastrarChamado(c);
				Utils.printMsg(this.getClass().getName(), "Cadastrando novo chamado.");
				
				// dorme um pouco
				long time = new Random().nextInt(5000);
				Thread.sleep(time);

			} catch (InterruptedException e) {
				break;
			} catch (BusinessException e) {
				// TODO Auto-generated catch block
				Utils.printMsg(this.getClass().getName(),e.getMessage());
			}
		}
	}
	
	public static void main(String[] args) {
		new ProdutoraChamados().start();
	}
}
