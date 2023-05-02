package com.trickle.os.controller.rest;

import java.io.*;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trickle.os.dao.MenuDao;
import com.trickle.os.vo.MenuVo;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class MenuController {
	private final MenuDao menuDao;
	
	@GetMapping("/addMenu")
	public String addMenu(String path, String name) {
		MenuVo menu = new MenuVo();
		String[] sp = path.split("/");
		menu.setParentId(Integer.parseInt(sp[sp.length-1].isEmpty() ? "0" : sp[sp.length-1]));
		menu.setName(name);
		int result = 0;

        if(sp.length == 1 && name.split(",").length == 2) {
        	menu.setName(name.split(",")[0].trim());
        	menu.setType(name.split(",")[1].trim());
        	result = menuDao.addRoot(menu);
        	File resDir = new File("src/main/resources");
    		File pageDir = new File(resDir, "page");
    		File templateFile = new File(pageDir, "index-template.html");
    		File typeDir = new File(pageDir, menu.getType());
    		if(!typeDir.exists()) typeDir.mkdir();
    		File indexFile = new File("/index-"+menu.getId()+".html");
    		try (BufferedReader reader = new BufferedReader(new FileReader(templateFile));
			    BufferedWriter writer = new BufferedWriter(new FileWriter(indexFile))) {
    			
			    String line;
			    while ((line = reader.readLine()) != null) {
			        String modifiedLine = line.replace("#pageName", menu.getName());
			        writer.write(modifiedLine);
			        writer.newLine();
			    }
			} catch (IOException e) {
			    e.printStackTrace();
			}
        }else if(sp.length == 2) {
        	result = menuDao.addDepth1(menu);
        } else {
        	result = menuDao.addDepth2(menu);
        }
        
		return result == 1 ? "성공" : "실패";
	}
	
	@GetMapping("/updateMenuName")
	public String updateMenuName(String path, String name) {
		MenuVo menu = new MenuVo();
		String[] sp = path.split("/");
		System.out.println(path);
		menu.setId(Integer.parseInt(sp[sp.length-1]));
		menu.setName(name);
		System.out.println(menu);
		System.out.println(sp.length);
		int result = 0;
        if(sp.length == 2) {
        	result = menuDao.updateRoot(menu);
        }else if(sp.length == 3) {
        	result = menuDao.updateDepth1(menu);
        } else {
        	result = menuDao.updateDepth2(menu);
        }
		return result == 1 ? "성공" : "실패";
	}
	
	@GetMapping("/getMenus")
	public List<MenuVo> getMenus(){
		List<MenuVo> roots = menuDao.getRoots();
		roots.forEach(root->menuDao.addChilds(root));
		return roots;
	}
	
	@GetMapping("/getRootMenu")
	public MenuVo getRootMenu(String type){
		return menuDao.addChilds(menuDao.getRootByType(type));
	}
	
	@GetMapping("/getPathName")
	public String getPathName(String path) {
		return menuDao.getPathName(path);
	}
}
