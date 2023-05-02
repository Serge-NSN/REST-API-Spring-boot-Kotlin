package com.serge.nsn.gadgetgallery.repository

import com.serge.nsn.gadgetgallery.model.GadgetModel
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface GadgetRepository : JpaRepository<GadgetModel.Gadget, Long>