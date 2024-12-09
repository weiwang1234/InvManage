package com.happycode.Controller;

import com.happycode.model.Partner;
import com.happycode.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/partners")
public class PartnerController {

    @Autowired
    private PartnerService partnerService;

    // 获取所有合作方（POST 请求）
    @PostMapping("/getAll")
    public List<Partner> getAllPartners() {
        return partnerService.getAllPartners();
    }

    // 根据 id 获取合作方（POST 请求）
    @PostMapping("/getById")
    public ResponseEntity<Partner> getPartnerById(@RequestBody Long id) {
        Optional<Partner> partner = partnerService.getPartnerById(id);
        return partner.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 新增合作方（POST 请求）
    @PostMapping("/add")
    public Partner addPartner(@RequestBody Partner partner) {
        return partnerService.addPartner(partner);
    }

    // 更新合作方（POST 请求）
    @PostMapping("/update/{id}")
    public ResponseEntity<Partner> updatePartner(@PathVariable Long id, @RequestBody Partner partnerDetails) {
        Partner updatedPartner = partnerService.updatePartner(id, partnerDetails);
        return updatedPartner != null ? ResponseEntity.ok(updatedPartner) : ResponseEntity.notFound().build();
    }

    // 删除合作方（POST 请求）
    @PostMapping("/delete/{id}")
    public ResponseEntity<Void> deletePartner(@PathVariable Long id) {
        partnerService.deletePartner(id);
        return ResponseEntity.noContent().build();
    }

    // 搜索合作方（POST 请求）
    @PostMapping("/search")
    public List<Partner> searchPartners(@RequestBody String query) {
        return partnerService.searchPartners(query);
    }
}