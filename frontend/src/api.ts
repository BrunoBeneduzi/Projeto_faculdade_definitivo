import axios from 'axios';
export const api=axios.create({baseURL:import.meta.env.VITE_URL_API ?? 'http://localhost:8080/api'});
api.interceptors.request.use(configuracao=>{const token=localStorage.getItem('token');if(token)configuracao.headers.Authorization=`Bearer ${token}`;return configuracao;});
api.interceptors.response.use(resposta=>resposta,erro=>Promise.reject(new Error(erro.response?.data?.mensagem ?? 'Não foi possível concluir a operação')));
export type Perfil={idPublico:string;urlPublica:string;nome:string;sobrenome:string;sexo:string;contatoEmergencia:string;tipoSanguineo:string;alergias:string[];medicamentos:string[];doencas:string[];cirurgias:string[]};
export type EntradaPerfil=Omit<Perfil,'idPublico'|'urlPublica'> & {senhaPublica?:string};
export const apiAutenticacao={
 cadastrar:(corpo:{nome:string;sobrenome:string;email:string;senha:string})=>api.post('/autenticacao/cadastro',corpo).then(resposta=>resposta.data),
 entrar:(corpo:{email:string;senha:string})=>api.post('/autenticacao/entrar',corpo).then(resposta=>resposta.data)
};
export const apiPerfil={
 buscar:()=>api.get<Perfil>('/perfil').then(resposta=>resposta.data),
 salvar:(corpo:EntradaPerfil)=>api.put<Perfil>('/perfil',corpo).then(resposta=>resposta.data),
 excluir:()=>api.delete('/perfil'),
 acessarPublico:(id:string,senha:string)=>api.post<Omit<Perfil,'idPublico'|'urlPublica'>>(`/publico/${id}`,{senha}).then(resposta=>resposta.data)
};