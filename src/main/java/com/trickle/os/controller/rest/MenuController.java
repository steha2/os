package com.trickle.os.controller.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.trickle.os.dao.MenuDao;
import com.trickle.os.vo.MenuVo;
import com.trickle.os.vo.RootVo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MenuController {
	private final MenuDao menuDao;
	
	@Value("${config.defaultStyle}")
	private String defaultStyle;
	
	@GetMapping("/addMenu")
	public String addMenu(String path, String name) {
		String[] sp = path.split("/");
		int result = 0;
        if(sp.length == 1 && name.split(",").length == 2) {
        	RootVo root = new RootVo();
        	root.setName(name.split(",")[0].trim());
        	root.setType(name.split(",")[1].trim());
        	root.setStyle(defaultStyle);
    		result = menuDao.addRoot(root);
        	createIndexPage(root);
        }else{
        	MenuVo menu = new MenuVo();
        	menu.setParentId(Integer.parseInt(sp[sp.length-1]));
    		menu.setName(name);
        	result = sp.length == 2 ? menuDao.addDepth1(menu) : menuDao.addDepth2(menu);
        }
		return result == 1 ? "메뉴 등록 성공" : "메뉴 등록 실패";
	}
	
	public void createIndexPage(RootVo root) {
		PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    	Resource res = resolver.getResource("file:src/main/resources/templates");
    	File resDir = null;
		try {
			resDir = new File(res.getFile().getAbsolutePath());
		} catch (IOException e) {
			e.printStackTrace();
		}
		File pageDir = new File(resDir, "page");
		
		File typeDir = new File(pageDir, root.getType());
		if(!typeDir.exists()) typeDir.mkdir();
		
		File rootIdDir = new File(typeDir, String.valueOf(root.getId()));
		if(!rootIdDir.exists()) rootIdDir.mkdir();
		
		File typeTemplateDir = new File(pageDir, root.getType()+"-template");
		if(typeTemplateDir.exists()) {
			File[] templateFiles = typeTemplateDir.listFiles();
			if (templateFiles != null) {
			    for (File templateFile : templateFiles) {
			        String newFileName = templateFile.getName().replaceAll("-\\d{1,}.html", "-" + root.getId() + ".html");
			        File newFile = new File(rootIdDir, newFileName);
			        
			        try {
			            Files.copy(templateFile.toPath(), newFile.toPath());
			            System.out.println("File copied: " + templateFile.getName() + " -> " + newFileName);
			        } catch (IOException e) {
			            System.out.println("Failed to copy file: " + templateFile.getName());
			            e.printStackTrace();
			        }
			    }
			}
		}
	}
	
	@GetMapping("/updateMenuName")
	public String updateMenuName(String path, String name) {
		MenuVo menu = new MenuVo();
		String[] sp = path.split("/");
		menu.setId(Integer.parseInt(sp[sp.length-1]));
		menu.setName(name);
		int result = 0;
        if(sp.length == 2) {
        	result = menuDao.updateRootName(menu);
        }else if(sp.length == 3) {
        	result = menuDao.updateDepth1Name(menu);
        } else {
        	result = menuDao.updateDepth2Name(menu);
        }
		return result == 1 ? "성공" : "실패";
	}
	
	@GetMapping("/getMenus")
	public List<RootVo> getMenus(){
		List<RootVo> roots = menuDao.getRoots();
		roots.forEach(root->menuDao.addChilds(root));
		return roots;
	}
	
	@GetMapping("/os/getMenus2")
	public List<RootVo> getMenus22(){
		List<RootVo> roots = menuDao.getRoots();
		roots.forEach(root->menuDao.addChilds(root));
		return roots;
	}
	
	@RequestMapping(value="/os/getMenus", produces = "text/plain; charset=utf8")
	public String getMenus2() {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(getMenus());
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@GetMapping("/getRootMenu/{rootId}")
	public RootVo getRootMenu(@PathVariable long rootId){
		return menuDao.addChilds(menuDao.getRootById(rootId));
	}
	
	@GetMapping("/getPathName")
	public String getPathName(String path) {
		return menuDao.getPathName(path);
	}
}
