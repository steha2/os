function createMenu(menuClick){
  $.getJSON("/getRootMenu",{type:"shop"}).done((root) => {
    const menuUl = $("<ul>");
    $("#menudiv").empty().append(menuUl);
    root.childs.forEach(d1 => {
      const li1 = $("<li>");
      li1.attr("path","/"+root.id+"/"+d1.id); 
      li1.append(`<div>${d1.name}</div>`);
      const ul2 = $("<ul>");
      if(d1.childs) d1.childs.forEach(d2=>{
        li1.append(ul2)
        const li2 = $(`<li><div>${d2.name}</div></li>`);
        li2.attr("path",li1.attr("path")+"/"+d2.id);
        console.log(d2);
        ul2.append(li2);
      });
      menuUl.append(li1);
    });
    menuUl.menu({delay:0, select: (event,ui)=>{
       menuClick($(ui.item[0]).attr("path"));
    }});
    $("#menuDiv").append(menuUl);
  }).fail((xhr, status, error) => console.error("AJAX Error: " + status + " " + error));;
}

function createTitle(title, width, height, fontSize) {
  const $title = $("<h1>").css({
    "font-weight": "bold",
    "font-family": "title",
    fontSize: fontSize,
    color: "White"
  }).text(title);
  const $wrapper = $("<div>").css({
    "display": "flex",
    "align-items": "center",
    "justify-content": "center",
    "flex-direction": "column",
    "height": height,
    "width": width,
    "background": "linear-gradient(to bottom, #3a78c3, #0047b3)"
  });
  const $reflection = $("<div>").css({
    "height": height,
    "width": width,
    "position": "absolute",
    "top": height,
    "transform": "rotateX(180deg)",
    "filter": "blur(30px)",
    "opacity": "0.5",
    "background": "linear-gradient(to top, #00F0F0, transparent)"
  });
  return $wrapper.append($title);
}