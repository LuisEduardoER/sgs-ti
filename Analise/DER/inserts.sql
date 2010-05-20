insert into STATUS values(statusSeq.nextVal,'ABERTO');
insert into STATUS values(statusSeq.nextVal,'PENDENTE');
insert into STATUS values(statusSeq.nextVal,'AGUARDANDO CLIENTE');
insert into STATUS values(statusSeq.nextVal,'AGENDADO');
insert into STATUS values(statusSeq.nextVal,'FINALIZADO');

insert into PORTE values(porteSeq.nextVal,'PESSOA FISICA');
insert into PORTE values(porteSeq.nextVal,'PEQUENA');
insert into PORTE values(porteSeq.nextVal,'MEDIA');
insert into PORTE values(porteSeq.nextVal,'GRANDE');

insert into TIPO_CHAMADO values(tipoChamadoSeq.nextVal,'PREVENTIVO',1);
insert into TIPO_CHAMADO values(tipoChamadoSeq.nextVal,'NORMAL',2);
insert into TIPO_CHAMADO values(tipoChamadoSeq.nextVal,'URGENTE',3);

insert into TIPO_FALHA values(tipoFalhaSeq.nextVal,'HARDWARE');
insert into TIPO_FALHA values(tipoFalhaSeq.nextVal,'SOFTWARE');
insert into TIPO_FALHA values(tipoFalhaSeq.nextVal,'INFORMATIVO');
insert into TIPO_FALHA values(tipoFalhaSeq.nextVal,'DUVIDA');

insert into GRUPO_USUARIO values(grupoUsuarioSeq.nextVal,'ADMINISTRADOR');

insert into USUARIO values(usuarioSeq.nextVal,'Andre','andrens','698dc19d489c4e4db73e28a713eab07b',1);
insert into USUARIO values(usuarioSeq.nextVal,'Vanessa','vanessa','698dc19d489c4e4db73e28a713eab07b',1);
insert into USUARIO values(usuarioSeq.nextVal,'Denis','denis','698dc19d489c4e4db73e28a713eab07b',1);

insert into PESSOA_FISICA values(pessoaFisicaSeq.nextVal,'Vanessa Iwamoto',to_date('19890802','yyyy-mm-dd'),123456789,'FEMININO','Uma rua do Fazendinha',1);
insert into PESSOA_FISICA values(pessoaFisicaSeq.nextVal,'Denis F. Gurski',to_date('19870918','yyyy-mm-dd'),123456789,'MASCULINO','Uma rua do Xaxim',1);
insert into PESSOA_FISICA values(pessoaFisicaSeq.nextVal,'André N. Schuster',to_date('19860525','yyyy-mm-dd'),123456789,'MASCULINO','Uma rua do Bacacheri',1);

insert into PESSOA_JURIDICA values(pessoaJuridicaSeq.nextVal,'Empresa S.A.','iBusiness',123456789,'Rua XV de Novembro, 12',2);
insert into PESSOA_JURIDICA values(pessoaJuridicaSeq.nextVal,'Gurski IT','Gurski IT',123456789,'Rua XV de Novembro, 16',4);
insert into PESSOA_JURIDICA values(pessoaJuridicaSeq.nextVal,'Japonese Food LTDA','Ching Ling Food',123456789,'Rua XV de Novembro, 30',3);
insert into PESSOA_JURIDICA values(pessoaJuridicaSeq.nextVal,'PUCPR','PUCPR',123456789,'Rua Imaculada Conceiçao, 2222',4);

insert into CHAMADO values(chamadoSeq.nextVal,to_date('20100519','yyyy-mm-dd'),null,'wi-fi não funciona','Marcio Fuckner','4133332121',null,2,3,1,2,null,4);

