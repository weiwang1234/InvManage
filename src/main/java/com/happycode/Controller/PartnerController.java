package com.happycode.Controller;

import com.happycode.model.Partner;
import com.happycode.model.UserInfo;
import com.happycode.service.PartnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/partners")
public class PartnerController {

    @Autowired
    private PartnerService partnerService;



    @PostMapping("/getAll")
    public List<Partner> getAllPartners() {
        return partnerService.getAllPartners();
    }
    // 获取所有合作方（POST 请求）
    @PostMapping("/getAllOrderPartners")
    public List<Partner> getAllOrderPartners() {
        return partnerService.getAllOrderPartners();
    }
    @PostMapping("/getpurchaseAll")
    public List<Partner> getpurchasePartners() {
        return partnerService.getAllpurchasePartners();
    }


    // 根据 id 获取合作方（POST 请求）
    @PostMapping("/getById")
    public ResponseEntity<Partner> getPartnerById(@RequestParam Long partnerId) {
        Optional<Partner> partner = partnerService.getPartnerById(partnerId);
        return partner.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
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
    @PostMapping("/delete")
    public ResponseEntity<String> deletePartner(@RequestBody Partner partner) {
        try {
            partnerService.deletePartner(partner.getPartnerid()); // 调用 service 层的软删除方法
            return ResponseEntity.ok("合作方已成功删除");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("未找到合作方，无法删除");
        }
    }

    @PostMapping("/takeeffectPartner")
    public ResponseEntity<String> takeeffectPartner(@RequestBody Partner partner) {
        try {
            partnerService.TakeeffectPartner(partner.getPartnerid()); // 调用 service 层的软删除方法
            return ResponseEntity.ok("合作方已成功删除");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("未找到合作方，无法删除");
        }
    }

    // 搜索合作方（POST 请求）
    @PostMapping("/search")
    public List<Partner> searchPartners(@RequestBody String query) {
        return partnerService.searchPartners(query);
    }
}
