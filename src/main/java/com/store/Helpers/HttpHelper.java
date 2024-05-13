package com.store.Helpers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.store.store.Entities.Item;

public class HttpHelper {
    
    public ResponseEntity<?> getAllItemsResponse(List<? extends Item> items) {
        if(items == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("no se pudo encontrar items");
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

}
