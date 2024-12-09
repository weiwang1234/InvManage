package com.happycode.service;

import com.happycode.model.Partner;
import com.happycode.repository.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PartnerService {

    @Autowired
    private PartnerRepository partnerRepository;

    // 获取所有合作方
    public List<Partner> getAllPartners() {
        return partnerRepository.findByPartnerstatus("1");  // 查询 partnerstatus 为 '1' 的数据
    }

    // 根据 id 查找合作方
    public Optional<Partner> getPartnerById(Long id) {
        return partnerRepository.findById(id); // 如果没有找到，返回 null
    }

    // 新增合作方
    public Partner addPartner(Partner partner) {
        return partnerRepository.save(partner);
    }

    // 更新合作方
    public Partner updatePartner(Long id, Partner partnerDetails) {
        Optional<Partner> optionalPartner = partnerRepository.findById(id);
        if (optionalPartner.isPresent()) {
            Partner partner = optionalPartner.get();
            partner.setPartnername(partnerDetails.getPartnername());
            partner.setPartneraddress(partnerDetails.getPartneraddress());
            partner.setPartnerphone(partnerDetails.getPartnerphone());
            partner.setPartnertype(partnerDetails.getPartnertype());
            partner.setPartnerstatus(partnerDetails.getPartnerstatus());
            return partnerRepository.save(partner);
        }
        return null;
    }

    // 删除合作方
    public void deletePartner(Long id) {
        Optional<Partner> partnerOpt = getPartnerById(id);

        // 如果找到了合作方
        if (partnerOpt.isPresent()) {
            Partner foundPartner = partnerOpt.get();

            // 修改 status 字段为 "2"（表示软删除）
            foundPartner.setPartnerstatus("2");

            // 保存修改后的合作方对象
            partnerRepository.save(foundPartner);
        } else {
            // 如果未找到合作方，做一些处理（如抛出异常或返回提示）
            System.out.println("未找到合作方，无法删除");
        }
    }

    // 根据名称和电话进行搜索
    public List<Partner> searchPartners(String query) {
        return partnerRepository.findAll().stream()
                .filter(partner -> partner.getPartnername().contains(query) || partner.getPartnerphone().contains(query))
                .collect(Collectors.toList());
    }
}
