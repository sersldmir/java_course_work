package com.example.demo;

import java.util.List;

import com.example.demo.security.UserInfo;
import com.example.demo.security.UserInfoRepository;
import io.netty.util.internal.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class ResourcesService {
    
    @Autowired
    private ResourceRepository repoRes;

    @Autowired
    private SupplierRepository repoSup;

    @Autowired
    private UserInfoRepository userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Resource> listAllRes(String keyword) {
        if (keyword != null) {
            return repoRes.search(keyword);
        }
        return repoRes.findAll();
    }

    public List<Supplier> listAllSup(String keyword) {
        if (keyword != null) {
            return repoSup.search(keyword);
        }
        return repoSup.findAll();
    }

    private String resKeywordName;
    private String supKeywordName;

    private String resKeyword;
    private String supKeyword;

    public String getResKeywordName() {
        return resKeywordName;
    }

    public String getResKeyword() {
        return resKeyword;
    }

    public String getSupKeywordName() {
        return supKeywordName;
    }

    public String getSupKeyword() {
        return supKeyword;
    }

    public List<Resource> listByResCriteria(
            String keywordName,
            String keywordType,
            String keywordQuantity,
            String keywordCost,
            String keywordAcdate,
            String keywordSupplier){
                
        if (!StringUtil.isNullOrEmpty(keywordName)){
            this.resKeywordName = "keywordName";
            this.resKeyword = keywordName;
            return repoRes.searchByName(keywordName);
        }
        else if (!StringUtil.isNullOrEmpty(keywordType)){
            this.resKeywordName = "keywordContent";
            this.resKeyword = keywordType;
            return repoRes.searchByType(keywordType);
        }
        else if (!StringUtil.isNullOrEmpty(keywordQuantity)){
            this.resKeywordName = "keywordShipmentCity";
            this.resKeyword = keywordQuantity;
            return repoRes.searchByQuantity(keywordQuantity);
        }
        else if (!StringUtil.isNullOrEmpty(keywordCost)){
            this.resKeywordName = "keywordShipmentDate";
            this.resKeyword = keywordCost;
            return repoRes.searchByCost(keywordCost);
        }
        else if (!StringUtil.isNullOrEmpty(keywordAcdate)){
            this.resKeywordName = "keywordArrivalCity";
            this.resKeyword = keywordAcdate;
            return repoRes.searchByAcdate(keywordAcdate);
        }
        else if (!StringUtil.isNullOrEmpty(keywordSupplier)){
            this.resKeywordName = "keywordArrivalDate";
            this.resKeyword = keywordSupplier;
            return repoRes.searchBySupplier(keywordSupplier);
        }
        else return repoRes.findAll();
    }

    public List<Supplier> listBySupCriteria(
            String keywordName,
            String keywordPhone,
            String keywordEmail){
        if (!StringUtil.isNullOrEmpty(keywordName)){
            this.supKeywordName = "keywordName";
            this.supKeyword = keywordName;
            return repoSup.searchByName(keywordName);
        }
        else if (!StringUtil.isNullOrEmpty(keywordPhone)){
            this.supKeywordName = "keywordDate";
            this.supKeyword = keywordPhone;
            return repoSup.searchByPhone(keywordPhone);
        }
        else if (!StringUtil.isNullOrEmpty(keywordEmail)){
            this.supKeywordName = "keywordAuthor";
            this.supKeyword = keywordEmail;
            return repoSup.searchByEmail(keywordEmail);
        }
        else return repoSup.findAll();
    }
    public String addUser(UserInfo userInfo){
        userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
        userRepo.save(userInfo);
        return "user added to system";
    }

    public void saveRes(Resource cargo) {
        repoRes.save(cargo);
    }

    public void saveSup(Supplier blog) {
        repoSup.save(blog);
    }

    public Resource getRes(Long id) {
        return repoRes.findById(id).get();
    }

    public Supplier getSup(Long id) {
        return repoSup.findById(id).get();
    }

    public void deleteRes(Long id) {
        repoRes.deleteById(id);
    }

    public void deleteSup(Long id) {
        repoSup.deleteById(id);
    }


}
