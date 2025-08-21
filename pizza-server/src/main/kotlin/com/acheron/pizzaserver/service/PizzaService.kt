package com.acheron.pizzaserver.service

import com.acheron.pizzaserver.entity.Pizza
import com.acheron.pizzaserver.repo.PizzaRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class PizzaService(
    val pizzaRepository: PizzaRepository,
    val imageService: ImageService
) {
    fun findAll(pageable: Pageable): Page<Pizza> {
        return pizzaRepository.findAll(pageable)
    }

    fun save(pizza: Pizza,images: List<MultipartFile>): Pizza {
        val savedImages: MutableList<String> = ArrayList();
        images.stream().forEach{  savedImages.add(imageService.uploadImage("images",it))};
        pizza.images = savedImages;
        return pizzaRepository.save(pizza)
    }

    fun findByNameIgnoreCase(name: String): Pizza? {
        return pizzaRepository.findByNameIgnoreCase(name);
    }
}