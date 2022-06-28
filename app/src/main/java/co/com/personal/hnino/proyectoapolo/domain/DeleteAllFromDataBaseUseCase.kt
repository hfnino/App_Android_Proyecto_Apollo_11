package co.com.personal.hnino.proyectoapolo.domain

import co.com.personal.hnino.proyectoapolo.data.ImgsApolloRepository
import javax.inject.Inject

class DeleteAllFromDataBaseUseCase @Inject constructor(private val repository: ImgsApolloRepository) {

    suspend operator fun invoke(){
        repository.deleteAllFromDataBase()
    }
}