package com.colutti.tour.controller

import com.colutti.tour.exception.PromocaoNotFoundException
import com.colutti.tour.model.Promocao
import com.colutti.tour.model.ResponseJson
import com.colutti.tour.model.exceptions.ErrorMessage
import com.colutti.tour.service.PromocaoService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.util.*
import java.util.concurrent.ConcurrentHashMap

@RestController
@RequestMapping(value = ["/promocoes"])
class PromocaoController {

    @Autowired
    lateinit var service: PromocaoService

    @GetMapping("/{id}")
    fun getById(@PathVariable id:Long): ResponseEntity<Any> {
        var promocao = service.getById(id)
        return if(promocao != null)
            return ResponseEntity(promocao,HttpStatus.OK)
        else
            return ResponseEntity(ErrorMessage("Promocao não localizada", "promocao ${id} não locallizada"), HttpStatus.NOT_FOUND)
    }

    @PostMapping()
    fun create(@RequestBody promocao: Promocao): ResponseEntity<ResponseJson> {
        service.create(promocao)
        val respostaJson = ResponseJson("OK", Date())
        return ResponseEntity(respostaJson, HttpStatus.CREATED)
    }

    @GetMapping()
    fun getAll(@RequestParam(required = false, defaultValue = "0") start: Int,
               @RequestParam(required = false, defaultValue = "3") size: Int): ResponseEntity<List<Promocao>>{
        val list = service.getAll(start, size)
        val status = if(list.size == 0) HttpStatus.NOT_FOUND else HttpStatus.OK
        return ResponseEntity(list,status)
    }

    @GetMapping("/ordenados")
    fun ordenados() = service.getAllSortedByLocal()

    @GetMapping("/menor")
    fun getAllMenores(@RequestParam(required = false, defaultValue = "0") preco: Double) =
            service.getAllByPrecoMenorQue(preco)

    @GetMapping("/count")
    fun count(): ResponseEntity<Map<String, Long>> =
        ResponseEntity.ok().body(mapOf("count" to service.count()))


    @DeleteMapping("/{id}")
    fun delete(@PathVariable id:Long): ResponseEntity<Unit> {
        var status = HttpStatus.NOT_FOUND
        if(service.getById(id) != null){
            status = HttpStatus.NO_CONTENT
            service.delete(id)
        }
        return ResponseEntity(Unit, status)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody promocao: Promocao): ResponseEntity<ResponseJson> {
        var status = HttpStatus.NOT_FOUND
        var respostaJson = ResponseJson("Error", Date())
        if(service.getById(id) != null){
            status = HttpStatus.OK
            service.update(id, promocao)
            respostaJson = ResponseJson("OK", Date())
        }
        return ResponseEntity(respostaJson, status)
    }

}