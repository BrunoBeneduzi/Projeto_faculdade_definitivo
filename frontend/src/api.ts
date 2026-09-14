import axios from 'axios';
export const api=axios.create({baseURL:import.meta.env.VITE_API_URL ?? 'http://localhost:8080/api'});
api.interceptors.request.use(config=>{const token=localStorage.getItem('token');if(token)config.headers.Authorization=`Bearer ${token}`;return config;});
api.interceptors.response.use(r=>r,e=>Promise.reject(new Error(e.response?.data?.message ?? 'Não foi possível concluir a operação')));
export type Profile={publicId:string;publicUrl:string;firstName:string;lastName:string;sex:string;emergencyContact:string;bloodType:string;allergies:string[];medications:string[];diseases:string[];surgeries:string[]};
export type ProfileInput=Omit<Profile,'publicId'|'publicUrl'> & {publicPassword?:string};
export const authApi={
 register:(body:{firstName:string;lastName:string;email:string;password:string})=>api.post('/auth/register',body).then(r=>r.data),
 login:(body:{email:string;password:string})=>api.post('/auth/login',body).then(r=>r.data)
};
export const profileApi={
 get:()=>api.get<Profile>('/profile').then(r=>r.data),
 save:(body:ProfileInput)=>api.put<Profile>('/profile',body).then(r=>r.data),
 remove:()=>api.delete('/profile'),
 publicAccess:(id:string,password:string)=>api.post<Omit<Profile,'publicId'|'publicUrl'>>(`/public/${id}`,{password}).then(r=>r.data)
};