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
        return partnerRepository.findById(id);
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
        partnerRepository.deleteById(id);
    }

    // 根据名称和电话进行搜索
    public List<Partner> searchPartners(String query) {
        return partnerRepository.findAll().stream()
                .filter(partner -> partner.getPartnername().contains(query) || partner.getPartnerphone().contains(query))
                .collect(Collectors.toList());
    }
}
