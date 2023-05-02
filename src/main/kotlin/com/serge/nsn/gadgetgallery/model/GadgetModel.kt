package com.serge.nsn.gadgetgallery.model

import jakarta.persistence.*


class GadgetModel {
    @Entity
    @Table(name = "GADGET")
    data class Gadget(

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        val  gadgetId: Long,
        val gadgetName: String,
        val gadgetCategory: String?,
        val gadgetAvailability: Boolean = true,
        val gadgetPrice: Double

        )
}