package br.edu.infnet.android.ex01.model
import kotlinx.serialization.Serializable

@Serializable
data class Turma(var alunos: MutableList<Aluno> = mutableListOf<Aluno>())
