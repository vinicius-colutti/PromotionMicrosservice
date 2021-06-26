package com.colutti.tour.repository

import com.colutti.tour.model.Promocao
import org.springframework.data.jpa.repository.Modifying
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.PagingAndSortingRepository
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.function.DoubleToLongFunction
import javax.transaction.Transactional

@Repository
interface PromocaoRepository: PagingAndSortingRepository<Promocao, Long> {

    @Query(value="select p from Promocao p where p.preco <= :preco")
    fun findByPrecoMenorQue(@Param("preco") preco: Double): List<Promocao>

    @Query(value="select p from Promocao p where p.local IN :locais")
    fun findByLocalInList(@Param("locais") locais: List<String>) : List<Promocao>

    @Query(value="UPDATE Promocao p set p.preco = :preco WHERE p.local = :local")
    @Transactional @Modifying
    fun updateByLocal(@Param("preco") preco: Double, @Param("local") local: String)

}