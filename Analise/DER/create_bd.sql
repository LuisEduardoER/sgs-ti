/*
 * Criação da tabela de TIPO_CHAMADO 
 */
create table TIPO_CHAMADO
(
	codigo integer not null constraint PK_CODIGO_TIPO_CHAMADO PRIMARY KEY,
	nome varchar(100), 
	valor_prioridade integer not null	

);

create sequence tipoChamadoSeq start with 1 increment by 1;

/*
 * Criação da tabela de TIPO_FALHA
 */
create table TIPO_FALHA
(
	codigo integer not null constraint PK_CODIGO_TIPO_FALHA PRIMARY KEY,
	nome varchar(100)

);

create sequence tipoFalhaSeq start with 1 increment by 1;

/*
 * Criação da tabela de STATUS
 */
create table STATUS
(
	codigo integer not null constraint PK_CODIGO_STATUS PRIMARY KEY,
	nome varchar(100)
);

create sequence statusSeq start with 1 increment by 1;

/*
 * Criação da tabela de GRUPO_USUARIO
 */
create table GRUPO_USUARIO
(
	codigo integer not null constraint PK_CODIGO_GRUPO_USUARIO PRIMARY KEY,
	nome varchar(100)

);

create sequence grupoUsuarioSeq start with 1 increment by 1;

/*
 * Criação da tabela de USUARIO
 */
create table USUARIO
(
	codigo integer not null constraint PK_CODIGO_USUARIO PRIMARY KEY,
	nome varchar(100),
	username varchar(20),
	password varchar(100),
	codigo_grupo_usuario int null constraint FK_CODIGO_GRUPO_USUARIO references GRUPO_USUARIO (codigo)
);

create sequence usuarioSeq start with 1 increment by 1;

/*
 * Criação da tabela de PORTE
 */
create table PORTE
(
	codigo integer not null constraint PK_CODIGO_PORTE PRIMARY KEY,
	nome varchar(100)
);

create sequence porteSeq start with 1 increment by 1;

/*
 * Criação da tabela de PESSOA_FISICA
 */
create table PESSOA_FISICA
(
	codigo integer not null constraint PK_PESSOA_FISICA PRIMARY KEY,
	nome varchar(100),
	data_nascimento Date,
	cpf LONG,
        sexo varchar(20),
        endereco varchar(140),
	codigo_porte int null constraint FK_CODIGO_PORTE_PF references PORTE (codigo)
);

create sequence pessoaFisicaSeq start with 1 increment by 1;

/*
 * Criação da tabela de PESSOA_JURIDICA
 */
create table PESSOA_JURIDICA
(
	codigo integer not null constraint PK_PESSOA_JURIDICA PRIMARY KEY,
	razao_social varchar(140),
	nome_fantasia varchar(140),
	cnpj LONG,
        endereco varchar(140),
	codigo_porte int null constraint FK_CODIGO_PORTE_PJ references PORTE (codigo)
);

create sequence pessoaJuridicaSeq start with 1 increment by 1;


/*
 * Criação da tabela de CHAMADO
 */
create table CHAMADO
(
   codigo integer not null constraint PK_CODIGO_CHAMADO PRIMARY KEY,
   data_abertura Date,
   data_fechamento Date,
   detalhes varchar(255),
   responsavel varchar(140),
   contato char(10),
   data_agendamento Date,
   codigo_status integer null constraint FK_CODIGO_STATUS references STATUS (codigo),
   codigo_tipo_chamado integer not null constraint FK_CODIGO_TIPO_CHAMADO references TIPO_CHAMADO (codigo),
   codigo_tipo_falha integer not null constraint FK_CODIGO_TIPO_FALHA references TIPO_FALHA (codigo),
   codigo_usu_registro integer not null constraint FK_CODIGO_USO_REGISTRO references USUARIO (codigo),
   codigo_pf integer null constraint FK_CODIGO_PESSOA_FISICA references PESSOA_FISICA (codigo),
   codigo_pj integer null constraint FK_CODIGO_PESSOA_JURIDICA references PESSOA_JURIDICA (codigo)
);

create sequence chamadoSeq start with 1 increment by 1;

/*
 * Criação da tabela de HISTORICO_CHAMADO
 */
create table HISTORICO_CHAMADO
(
   codigo integer not null constraint PK_CODIGO_HISTORICO_CHAMADO PRIMARY KEY,
   data_atualizacao Date,
   detalhes varchar(255),
   data_agendamento Date,
   codigo_status integer null constraint FK_CODIGO_STATUS_HIST references STATUS (codigo),
   codigo_usu_registro integer not null constraint FK_CODIGO_USO_REGISTRO_HIST references USUARIO (codigo),
   codigo_chamado integer null constraint FK_CODIGO_CHAMADO_HIST references CHAMADO (codigo)
);

create sequence historicoChamadoSeq start with 1 increment by 1;

/*
 * Criação da tabela de CLIENTE_USUARIO
 */
create table CLIENTE_USUARIO
(
    codigo_usuario integer not null constraint FK_CODIGO_USUARIO_2 references USUARIO (codigo),
    codigo_pj integer null constraint FK_CODIGO_PJ_2 references PESSOA_JURIDICA (codigo),
    codigo_pf integer null constraint FK_CODIGO_PF_2 references PESSOA_FISICA (codigo)
);

