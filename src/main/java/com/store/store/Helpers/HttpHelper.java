package com.store.store.Helpers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.store.store.Entities.ItemEntity;

public class HttpHelper {
    
    public ResponseEntity<?> getAllItemsResponse(List<? extends ItemEntity> items) {
        if(items == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar items");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(items);
        }
    }

    public ResponseEntity<?> getItemByIdResponse(ItemEntity item, Long id) {
        if(item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el item con el id: "+id);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(item);
        }
    }

    public ResponseEntity<?> getPostResponse(ItemEntity item) {
        if(item == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo añadir el nuevo item");
        }
        else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Item añadido correctamente");
        }
        
    }

    public ResponseEntity<?> getPutResponse(ItemEntity item, Long id) {

        if(item == null) {
           return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El item en el id: "+id+" no se pudo actualizar");
        }
        else if(id == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El Id no existe");
        }
        else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("El item se actualizó correctamente");
        }
    
    }

    public ResponseEntity<?> getDeleteResponse(ItemEntity item, Long id) {

        if(item == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo eliminar el item en el id: "+id);
        }
        else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("El item con el id: "+id+" se eliminó correctamente");
        }
    }

    public ResponseEntity<?> getItemsByCategoryResponse(List<? extends ItemEntity> items, String category) {

        if(items == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo encontrar items con la categoria: "+category);
        }
        else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(items);
        }
    }

    public ResponseEntity<?> getItemsByPriceResponse(List<? extends ItemEntity> items, Double price) {

        if(items == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo encontrar items con el precio: "+price);
        }
        else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body(items);
        }

    }

}
