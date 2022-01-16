package com.jimmystarling.providapesquisasatisfacao.data.interfaces

import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface GoogleSpreadsheetService {
    @POST("1FAIpQLScFKGmmYPAZZS41JtqhI2urDhmYdv5NpEDKSsb4OnscTwe2bA/viewform")//?usp=pp_url&
    @FormUrlEncoded
    fun completeFormPesquisaDeSatisfacao(
        @Field("entry.1565867496") satisfacao_atendimento_enfermagem: String,
        @Field("entry.1813610430") satisfacao_atendimento_farmacia: String,
        @Field("entry.1225570595") satisfacao_atendimento_marcacao: String,
        @Field("entry.843702939") satisfacao_atendimento_recepcao: String,
        @Field("entry.731300413") satisfacao_atendimento_adm: String,
        @Field("entry.567051792") satisfacao_atendimento_vacinacao: String,
        @Field("entry.1391146651") satisfacao_atendimento_lab: String,
        @Field("entry.255917679") satisfacao_atendimento_medica: String,
        @Field("entry.1941905166") satisfacao_agilidade_enfermagem: String,
        @Field("entry.1895862388") satisfacao_agilidade_farmacia: String,
        @Field("entry.78559090") satisfacao_agilidade_marcacao: String,
        @Field("entry.627425289") satisfacao_agilidade_recepcao: String,
        @Field("entry.1976677376") satisfacao_agilidade_adm: String,
        @Field("entry.1473799466") satisfacao_agilidade_vacinacao: String,
        @Field("entry.126903910") satisfacao_agilidade_lab: String,
        @Field("entry.918688160") satisfacao_agilidade_medica: String,
        @Field("entry.698495710") satisfacao_estrutura_ponde: String,
        @Field("entry.1135385060") satisfacao_limpeza_ponde: String,
        @Field("entry.1595410109") atendido_pelo_callcenter: String,
        @Field("entry.2014467095") satisfacao_callcenter: String,
        @Field("entry.1851047393") atendido_por_profissionais_adicionais: String,
        @Field("entry.1744271615") profissionais_adicionais_especialidade: String,
        @Field("entry.2099027296") satisfacao_atendimento_profissionais_adicionais: String,
        @Field("entry.654910633") satisfacao_atendimento_geral: String,
        @Field("entry.1504355617") campo_registro_reclamacao: String,
        @Field("entry.263948175") campo_registro_sugestao: String,
    ): Call<Void?>?
}