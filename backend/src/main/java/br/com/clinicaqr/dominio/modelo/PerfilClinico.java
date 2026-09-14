package br.com.clinicaqr.dominio.modelo;
import java.util.List;
import java.util.UUID;
public record PerfilClinico(UUID id, UUID usuarioId, UUID idPublico, String nome, String sobrenome, String sexo,
 String contatoEmergencia, String telefoneContatoEmergencia, String tipoSanguineo, List<String> alergias, List<String> medicamentos,
 List<String> doencas, List<String> cirurgias, String senhaPublicaHash) {
    public PerfilClinico {
        alergias=alergias==null?List.of():List.copyOf(alergias);
        medicamentos=medicamentos==null?List.of():List.copyOf(medicamentos);
        doencas=doencas==null?List.of():List.copyOf(doencas);
        cirurgias=cirurgias==null?List.of():List.copyOf(cirurgias);
    }
}