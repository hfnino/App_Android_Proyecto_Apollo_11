package co.com.personal.hnino.proyectoapolo.domain

import co.com.personal.hnino.proyectoapolo.data.ImgsApolloRepository
import co.com.personal.hnino.proyectoapolo.domain.model.ItemDatosImage
import javax.inject.Inject

class SaveItemImgApoloUseCase @Inject constructor(private val repository: ImgsApolloRepository) {

    suspend operator fun invoke(itemDatosImage: ItemDatosImage){
        repository.insertItemDatosImageEntity(itemDatosImage)
    }
}