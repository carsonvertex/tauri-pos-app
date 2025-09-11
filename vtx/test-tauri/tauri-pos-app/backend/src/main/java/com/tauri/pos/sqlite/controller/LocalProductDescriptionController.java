package com.tauri.pos.sqlite.controller;

import com.tauri.pos.sqlite.persistance.dao.LocalProductDescriptionDao;
import com.tauri.pos.sqlite.persistance.eo.LocalProductDescriptionEntity;
import com.tauri.pos.sqlite.persistance.eo.LocalProductDescriptionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/local-product-descriptions")
@CrossOrigin(origins = "*")
public class LocalProductDescriptionController {

    @Autowired
    private LocalProductDescriptionDao localProductDescriptionDao;

    // Get all local product descriptions
    @GetMapping
    public ResponseEntity<List<LocalProductDescriptionEntity>> getAllLocalProductDescriptions() {
        List<LocalProductDescriptionEntity> descriptions = localProductDescriptionDao.findAll();
        return ResponseEntity.ok(descriptions);
    }

    // Get local product description by product ID, site ID, and language ID
    @GetMapping("/{productId}/{siteId}/{languageId}")
    public ResponseEntity<LocalProductDescriptionEntity> getLocalProductDescriptionById(
            @PathVariable Integer productId, 
            @PathVariable Integer siteId, 
            @PathVariable Integer languageId) {
        LocalProductDescriptionId id = new LocalProductDescriptionId(productId, siteId, languageId);
        return localProductDescriptionDao.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create new local product description
    @PostMapping
    public ResponseEntity<LocalProductDescriptionEntity> createLocalProductDescription(@RequestBody LocalProductDescriptionEntity description) {
        LocalProductDescriptionEntity savedDescription = localProductDescriptionDao.save(description);
        return ResponseEntity.ok(savedDescription);
    }

    // Update local product description
    @PutMapping("/{productId}/{siteId}/{languageId}")
    public ResponseEntity<LocalProductDescriptionEntity> updateLocalProductDescription(
            @PathVariable Integer productId, 
            @PathVariable Integer siteId, 
            @PathVariable Integer languageId, 
            @RequestBody LocalProductDescriptionEntity descriptionEntity) {
        LocalProductDescriptionId id = new LocalProductDescriptionId(productId, siteId, languageId);
        if (!localProductDescriptionDao.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        descriptionEntity.setProductId(productId);
        descriptionEntity.setSiteId(siteId);
        descriptionEntity.setLanguageId(languageId);
        LocalProductDescriptionEntity updatedDescription = localProductDescriptionDao.save(descriptionEntity);
        return ResponseEntity.ok(updatedDescription);
    }

    // Delete local product description
    @DeleteMapping("/{productId}/{siteId}/{languageId}")
    public ResponseEntity<Void> deleteLocalProductDescription(
            @PathVariable Integer productId, 
            @PathVariable Integer siteId, 
            @PathVariable Integer languageId) {
        LocalProductDescriptionId id = new LocalProductDescriptionId(productId, siteId, languageId);
        if (!localProductDescriptionDao.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        localProductDescriptionDao.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    // Get local product descriptions by product ID
    @GetMapping("/product/{productId}")
    public ResponseEntity<List<LocalProductDescriptionEntity>> getLocalProductDescriptionsByProductId(@PathVariable Integer productId) {
        List<LocalProductDescriptionEntity> descriptions = localProductDescriptionDao.findByProductId(productId);
        return ResponseEntity.ok(descriptions);
    }

    // Get local product descriptions by site ID
    @GetMapping("/site/{siteId}")
    public ResponseEntity<List<LocalProductDescriptionEntity>> getLocalProductDescriptionsBySiteId(@PathVariable Integer siteId) {
        List<LocalProductDescriptionEntity> descriptions = localProductDescriptionDao.findBySiteId(siteId);
        return ResponseEntity.ok(descriptions);
    }

    // Get local product descriptions by language ID
    @GetMapping("/language/{languageId}")
    public ResponseEntity<List<LocalProductDescriptionEntity>> getLocalProductDescriptionsByLanguageId(@PathVariable Integer languageId) {
        List<LocalProductDescriptionEntity> descriptions = localProductDescriptionDao.findByLanguageId(languageId);
        return ResponseEntity.ok(descriptions);
    }

    // Get total count of local product descriptions
    @GetMapping("/count")
    public ResponseEntity<Map<String, Long>> getLocalProductDescriptionsCount() {
        Long count = localProductDescriptionDao.countAllDescriptions();
        Map<String, Long> response = new HashMap<>();
        response.put("count", count);
        return ResponseEntity.ok(response);
    }
}
