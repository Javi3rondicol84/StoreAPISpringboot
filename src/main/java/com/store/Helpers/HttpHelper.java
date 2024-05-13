package com.store.Helpers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.store.store.Entities.Item;

public class HttpHelper {
    
    public ResponseEntity<?> getAllItemsResponse(List<? extends Item> items) {
        if(items == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se pudo encontrar items");
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(items);
        }
    }

    public ResponseEntity<?> getItemByIdResponse(Item item, Long id) {
        if(item == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontró el item con el id: "+id);
        }
        else {
            return ResponseEntity.status(HttpStatus.OK).body(item);
        }
    }

    public ResponseEntity<?> getPostResponse(Item item) {
        if(item == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo añadir el nuevo item");
        }
        else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("Item añadido correctamente");
        }
        
    }

    public ResponseEntity<?> getPutResponse(Item item, Long id) {

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

    public ResponseEntity<?> getDeleteResponse(Item item, Long id) {

        if(item == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("No se pudo eliminar el item en el id: "+id);
        }
        else {
            return ResponseEntity.status(HttpStatus.ACCEPTED).body("El item con el id: "+id+" se eliminó correctamente");
        }
    }

}
