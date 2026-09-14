package br.com.clinicaqr.infraestrutura.persistencia;
import jakarta.persistence.*;
import java.util.*;
@Entity @Table(name="perfis_clinicos")
public class EntidadePerfilClinico {
 @Id public UUID id;
 @Column(name="usuario_id",nullable=false,unique=true) public UUID usuarioId;
 @Column(name="id_publico",nullable=false,unique=true) public UUID idPublico;
 @Column(nullable=false) public String nome;
 @Column(nullable=false) public String sobrenome;
 public String sexo;
 @Column(name="contato_emergencia",nullable=false) public String contatoEmergencia;
 @Column(name="telefone_contato_emergencia",nullable=false) public String telefoneContatoEmergencia;
 @Column(name="tipo_sanguineo",nullable=false) public String tipoSanguineo;
 @Column(name="senha_publica_hash",nullable=false) public String senhaPublicaHash;
 @ElementCollection(fetch=FetchType.EAGER) @CollectionTable(name="perfil_alergias",joinColumns=@JoinColumn(name="perfil_id")) @Column(name="valor") public List<String> alergias=new ArrayList<>();
 @ElementCollection(fetch=FetchType.EAGER) @CollectionTable(name="perfil_medicamentos",joinColumns=@JoinColumn(name="perfil_id")) @Column(name="valor") public List<String> medicamentos=new ArrayList<>();
 @ElementCollection(fetch=FetchType.EAGER) @CollectionTable(name="perfil_doencas",joinColumns=@JoinColumn(name="perfil_id")) @Column(name="valor") public List<String> doencas=new ArrayList<>();
 @ElementCollection(fetch=FetchType.EAGER) @CollectionTable(name="perfil_cirurgias",joinColumns=@JoinColumn(name="perfil_id")) @Column(name="valor") public List<String> cirurgias=new ArrayList<>();
 protected EntidadePerfilClinico(){}
}