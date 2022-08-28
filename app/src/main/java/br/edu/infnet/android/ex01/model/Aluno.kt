package br.edu.infnet.android.ex01.model

import kotlinx.serialization.Serializable

@Serializable
data class Aluno(val nome: String, val notaUm: Double, val notaDois: Double)


