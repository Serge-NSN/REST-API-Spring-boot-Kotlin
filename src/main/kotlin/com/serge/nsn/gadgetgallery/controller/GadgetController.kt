package com.serge.nsn.gadgetgallery.controller

import com.serge.nsn.gadgetgallery.model.GadgetModel
import com.serge.nsn.gadgetgallery.repository.GadgetRepository
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.util.ObjectUtils
import org.springframework.web.bind.annotation.*
import org.springframework.web.util.UriComponentsBuilder

@RestController
@CrossOrigin
@RequestMapping("/api")
class GadgetController (private val gadgetRepository: GadgetRepository){

    @GetMapping("/")
    fun display(): String = "Spring Boot CRUD operation with Kotlin H2 Database"

    // GET all gadgets from database
    @GetMapping("/gadgets")
    fun fetchGadgets(): ResponseEntity<List<GadgetModel.Gadget>> {
        val gadgets = gadgetRepository.findAll()
        if (gadgets.isEmpty()) {
            return ResponseEntity<List<GadgetModel.Gadget>>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<List<GadgetModel.Gadget>>(HttpStatus.NOT_FOUND)
    }

    //GET by gadgetId
    @GetMapping("/gadgets/{id}")
    fun fetchGadgetById(@PathVariable("id") gadgetId: Long): ResponseEntity<GadgetModel.Gadget> {
        val gadget = gadgetRepository.findById(gadgetId)
        if (gadget.isPresent) {
            return ResponseEntity<GadgetModel.Gadget>(gadget.get(), HttpStatus.OK)
        }
        return ResponseEntity<GadgetModel.Gadget>(HttpStatus.NOT_FOUND)
    }

    //POST a new Gadget
    @PostMapping("/gadgets")
    fun addNewGadget(@RequestBody gadget: GadgetModel.Gadget, uri: UriComponentsBuilder): ResponseEntity<GadgetModel.Gadget> {
        val persistedGadget = gadgetRepository.save(gadget)
        if (ObjectUtils.isEmpty(persistedGadget)) {
            return ResponseEntity<GadgetModel.Gadget>(HttpStatus.BAD_REQUEST)
        }
        val headers = HttpHeaders()
        headers.location = uri.path("/gadget/{gadgetId}").buildAndExpand(gadget.gadgetId).toUri()
        return ResponseEntity(headers, HttpStatus.CREATED)
    }


    //PUT - Update Gadget details by gadgetId
    @PutMapping("/gadgets/{id}")
    fun updateGadgetById(@PathVariable("id") gadgetId: Long, @RequestBody gadget: GadgetModel.Gadget): ResponseEntity<GadgetModel.Gadget> {
        return gadgetRepository.findById(gadgetId).map {
                gadgetDetails ->
            val updatedGadget: GadgetModel.Gadget = gadgetDetails.copy(
                gadgetCategory = gadget.gadgetCategory,
                gadgetName = gadget.gadgetName,
                gadgetPrice = gadget.gadgetPrice,
                gadgetAvailability = gadget.gadgetAvailability
            )
            ResponseEntity(gadgetRepository.save(updatedGadget), HttpStatus.OK)
        }.orElse(ResponseEntity<GadgetModel.Gadget>(HttpStatus.INTERNAL_SERVER_ERROR))
    }

    // Delete a gadget by gadgetId

    @DeleteMapping("/gadgets/{id}")
    fun removeGadgetById(@PathVariable("id") gadgetId: Long): ResponseEntity<Void> {
        val gadget = gadgetRepository.findById(gadgetId)
        if (gadget.isPresent) {
            gadgetRepository.deleteById(gadgetId)
            return ResponseEntity<Void>(HttpStatus.NO_CONTENT)
        }
        return ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR)
    }

    // Delete all gadgets
    @DeleteMapping("/gadgets")
    fun removeGadgets(): ResponseEntity<Void> {
        gadgetRepository.deleteAll()
        return ResponseEntity<Void>(HttpStatus.OK)
    }
}