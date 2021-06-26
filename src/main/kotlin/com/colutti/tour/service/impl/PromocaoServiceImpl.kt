package com.colutti.tour.service.impl

import com.colutti.tour.model.Promocao
import com.colutti.tour.repository.PromocaoRepository
import com.colutti.tour.service.PromocaoService
import org.springframework.cache.annotation.CacheEvict
import org.springframework.cache.annotation.Cacheable
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.domain.Sort
import org.springframework.stereotype.Service


@Service
class PromocaoServiceImpl(val promocaoRepository: PromocaoRepository) : PromocaoService{

    override fun getById(id: Long): Promocao? =
        this.promocaoRepository.findById(id).orElseGet(null)

    @CacheEvict("promocoes", allEntries = true)
    override fun delete(id: Long) {
        this.promocaoRepository.deleteById(id)
    }

    @CacheEvict("promocoes", allEntries = true)
    override fun create(promocao: Promocao) {
        this.promocaoRepository.save(promocao)
    }

    @CacheEvict("promocoes", allEntries = true)
    override fun update(id: Long, promocao: Promocao) {
        create(promocao)
    }

    override fun searchByLocal(local: String): List<Promocao> =
        listOf()

    @Cacheable("promocoes")
    override fun getAll(start: Int, size:Int): List<Promocao> {
        val pages:Pageable = PageRequest.of(start,size, Sort.by("local").ascending())
        return this.promocaoRepository.findAll(pages).toList()
    }

    override fun count(): Long =
        this.promocaoRepository.count()

    override fun getAllSortedByLocal(): List<Promocao> =
        this.promocaoRepository.findAll(Sort.by("local").descending()).toList()

    override fun getAllByPrecoMenorQue(preco: Double): List<Promocao> {
        return this.promocaoRepository.findByPrecoMenorQue(preco)
    }

}