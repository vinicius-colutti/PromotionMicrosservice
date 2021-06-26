package com.colutti.tour.service

import com.colutti.tour.model.Promocao
import javax.lang.model.element.NestingKind

interface PromocaoService {
    fun getById(id: Long) : Promocao?
    fun delete(id: Long)
    fun create(promocao:Promocao)
    fun update(id: Long, promocao:Promocao)
    fun searchByLocal(local: String): List<Promocao>
    fun getAll(start: Int, size:Int): List<Promocao>
    fun count(): Long
    fun getAllSortedByLocal(): List<Promocao>
    fun getAllByPrecoMenorQue(preco: Double): List<Promocao>
}