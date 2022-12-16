package com.rafli.yourvideogames.helper

import com.rafli.yourvideogames.network.ClientService

open class BaseHelper {

    val ApiServiceServer by lazy { ClientService().create() }


}