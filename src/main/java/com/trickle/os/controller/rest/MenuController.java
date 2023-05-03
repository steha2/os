package com.trickle.os.controller.rest;

import java.io.*;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
		File templateFile = new File(pageDir, "index-template.html");
		
		File typeDir = new File(pageDir, root.getType());
		if(!typeDir.exists()) typeDir.mkdir();
		
		File indexFile = new File(typeDir,"index-"+root.getId()+".html");
//		Debug.log("templateFile:" + templateFile.getAbsolutePath());
//		Debug.log("indexFile:" + indexFile.getAbsolutePath());
		try (BufferedReader reader = new BufferedReader(new FileReader(templateFile));
		    BufferedWriter writer = new BufferedWriter(new FileWriter(indexFile))) {
			
		    String line;
		    while ((line = reader.readLine()) != null) {
		        String modifiedLine = line.replace("#pageName", root.getName());
		        writer.write(modifiedLine);
		        writer.newLine();
		    }
		} catch (IOException e) {
		    e.printStackTrace();
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
	
	@GetMapping("/getRootMenu")
	public RootVo getRootMenu(String type){
		return menuDao.addChilds(menuDao.getRootByType(type));
	}
	
	@GetMapping("/getPathName")
	public String getPathName(String path) {
		return menuDao.getPathName(path);
	}
}
