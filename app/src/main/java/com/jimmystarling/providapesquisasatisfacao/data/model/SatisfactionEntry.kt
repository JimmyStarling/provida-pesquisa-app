package com.jimmystarling.providapesquisasatisfacao.data.model

data class SatisfactionEntry(
    var satisfacao_atendimento_enfermagem:String,
    var satisfacao_atendimento_farmacia:String,
    var satisfacao_atendimento_marcacao:String,
    var satisfacao_atendimento_recepcao:String,
    var satisfacao_atendimento_adm:String,
    var satisfacao_atendimento_vacinacao:String,
    var satisfacao_atendimento_lab:String,
    var satisfacao_atendimento_medica:String,
    var satisfacao_agilidade_enfermagem:String,
    var satisfacao_agilidade_farmacia:String,
    var satisfacao_agilidade_marcacao:String,
    var satisfacao_agilidade_recepcao:String,
    var satisfacao_agilidade_adm:String,
    var satisfacao_agilidade_vacinacao:String,
    var satisfacao_agilidade_lab:String,
    var satisfacao_agilidade_medica:String,
    var satisfacao_estrutura_ponde:String,
    var satisfacao_limpeza_ponde:String,
    var atendido_pelo_callcenter:String,
    var satisfacao_callcenter:String,
    var atendido_por_profissionais_adicionais:String,
    var profissionais_adicionais_especialidade:String,
    var satisfacao_atendimento_profissionais_adicionais:String,
    var satisfacao_atendimento_geral:String,
    var campo_registro_reclamacao:String,
    var campo_registro_sugestao:String,
)