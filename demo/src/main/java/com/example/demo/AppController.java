package com.example.demo;

import java.util.List;

import com.example.demo.security.UserInfo;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
@EnableMethodSecurity(prePostEnabled = true, proxyTargetClass = true)
public class AppController {

    @Autowired
    private ResourcesService service;

    @GetMapping("/about")
    public String about(){
        return "about_page";
    }
    
    @GetMapping("/login_page")
    public String login(){
        return "login_page";
    }

    @PostMapping("/login_page")
    public String loginSubmit(@RequestParam String username, HttpSession session){

        final String currentUser = SecurityContextHolder.getContext().getAuthentication().getName();
        session.setAttribute("username", currentUser);
        return "redirect:/";
    }

    @PostMapping("/reg")
    public String addNewUser(@ModelAttribute UserInfo userInfo, @RequestParam String name, @RequestParam String roles, HttpSession session) {

        service.addUser(userInfo);
        session.setAttribute("username", name);
        session.setAttribute("roles", roles);
        return "redirect:/";
    }

    @GetMapping("/reg")
    public String register(HttpServletRequest request, HttpServletResponse response) {

        new SecurityContextLogoutHandler().logout(request, response, SecurityContextHolder.getContext().getAuthentication());
        return "reg_page";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping("/reg_admin")
    public String addNewUserAdmin(@ModelAttribute UserInfo userInfo, @RequestParam String name, @RequestParam String roles, HttpSession session) {

        service.addUser(userInfo);
        session.setAttribute("username", name);
        session.setAttribute("roles", roles);
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @GetMapping("/reg_admin")
    public String registerAdmin() {
        return "reg_page_admin";
    }

    @RequestMapping("/")
    public String viewHomePage(Model model, @Param("keyword") String keyword){

        List<Resource> listRes = service.listAllRes(keyword);
        List<String> listSups = service.getResSupplier();
        model.addAttribute("listRes", listRes);
        model.addAttribute("keyword", keyword);
        model.addAttribute("listSups", listSups);
        return "index";
    }

    @RequestMapping("/sup")
    public String viewSupPage(Model model, @Param("keyword") String keyword) {

        List<Supplier> listSup = service.listAllSup(keyword);
        model.addAttribute("listSup", listSup);
        model.addAttribute("keyword", keyword);
        return "sup";
    }

    @RequestMapping("/findRes")
    public String searchCargo(Model model, @Param("keywordName") String keywordName,
                              @Param("keywordType") String keywordType,
                              @Param("keywordQuantity") String keywordQuantity,
                              @Param("keywordCost") String keywordCost,
                              @Param("keywordAcdate") String keywordAcdate,
                              @Param("keywordSupplier") String keywordSupplier){

        List<Resource> listResByCriteria = service.listByResCriteria(keywordName, keywordType,
                keywordQuantity, keywordCost, keywordAcdate, keywordSupplier);
        model.addAttribute("listRes", listResByCriteria);
        model.addAttribute(service.getResKeywordName(),service.getResKeyword());
        List<String> listSups = service.getSupplierNames(keywordSupplier);
        model.addAttribute("listSups", listSups);
        return "index";
    }

    @RequestMapping("/findSup")
    public String searchRecord(Model model, @Param("keywordName") String keywordName,
                               @Param("keywordPhone") String keywordPhone,
                               @Param("keywordEmail") String keywordEmail,
                               @Param("keywordId") String keywordId){

        List<Supplier> listSupByCriteria = service.listBySupCriteria(keywordId, keywordName, keywordPhone, keywordEmail);
        model.addAttribute("listSup", listSupByCriteria);
        model.addAttribute(service.getSupKeywordName(), service.getSupKeyword());
        return "sup";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/newRes")
    public String showNewResForm(Model model){

        Resource resource = new Resource();
        model.addAttribute("resource", resource);
        model.addAttribute("suppliers", service.listAllSups());
        return "new_res";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/newSup")
    public String showNewRecordForm(Model model){

        Supplier supplier = new Supplier();
        model.addAttribute("supplier", supplier);
        return "new_sup";
    }

    @RequestMapping(value = "/saveRes", method = RequestMethod.POST)
    public String saveRes(@ModelAttribute("resource") Resource resource){

        service.saveRes(resource);
        return "redirect:/";
    }

    @RequestMapping(value = "/saveSup", method = RequestMethod.POST)
    public String saveSup(@ModelAttribute("supplier") Supplier supplier){
        service.saveSup(supplier);
        return "redirect:/sup";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/editRes/{resid}")
    public ModelAndView showEditResForm(@PathVariable(name = "resid") Long id){

        ModelAndView mav = new ModelAndView("edit_res");
        Resource resource = service.getRes(id);
        mav.addObject("resource", resource);
        mav.addObject("suppliers", service.listAllSups());
        return mav;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/editSup/{supid}")
    public ModelAndView showEditSupForm(@PathVariable(name = "supid") Long id){

        ModelAndView mav = new ModelAndView("edit_sup");
        Supplier supplier = service.getSup(id);
        mav.addObject("supplier", supplier);
        return mav;
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/deleteRes/{resid}")
    public String deleteRes(@PathVariable(name = "resid") Long id){

        service.deleteRes(id);
        return "redirect:/";
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @RequestMapping("/deleteSup/{supid}")
    public String deleteSup(@PathVariable(name = "supid") Long id){

        service.deleteSup(id);
        return "redirect:/sup";
    }

}
