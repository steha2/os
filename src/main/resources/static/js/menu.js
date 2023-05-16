function createMenu(menuClick, rootId){
  $.getJSON("/getRootMenu/"+rootId).done((root) => {
    const menuUl = $("<ul>");
    $("#menudiv").empty().append(menuUl);
    console.log(root);
    if(root.childs) root.childs.forEach(d1 => {
      const li1 = $("<li>");
      li1.attr("path",d1.path); 
      li1.attr("pathName",d1.pathName); 
      li1.append(`<div>${d1.name}</div>`);
      const ul2 = $("<ul>");
      if(d1.childs) d1.childs.forEach(d2=>{
        li1.append(ul2)
        const li2 = $(`<li><div>${d2.name}</div></li>`);
        li2.attr("path",d2.path);
        li2.attr("pathName",d2.pathName);
        ul2.append(li2);
      });
      menuUl.append(li1);
    });
    menuUl.menu({delay:0, select: (event,ui)=>{
      menuClick($(ui.item[0]).attr("path"),$(ui.item[0]).attr("pathName"));
    }});
    $("#menuDiv").append(menuUl);
  }).fail((xhr, status, error) => console.error("AJAX Error: " + status + " " + error));
}

function createTitle(title, width, height, fontSize, link, background) {
  const $title = $("<h1>").css({
    "font-weight": "bold",
    "font-family": "title",
    fontSize: fontSize,
    color: "White"
  }).html(title);
  const $wrapper = $("<div>").css({
    cursor:"pointer",
    "display": "flex",
    "align-items": "center",
    "justify-content": "center",
    "flex-direction": "column",
    "height": height,
    "width": width,
    "background": background
  }).click(()=>location.href=link);
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

function createSelectMenu(rootId, element, selectedPath) {
  $.getJSON("/getRootMenu/" + rootId).done((root) => {
      const rootName = root.name;
      console.log(root)
      // 첫 번째 <select> 요소 생성
      const select1 = $("<select>");
      root.childs.forEach((menu) => {
        const option = $("<option>").attr("value",menu.id).text(menu.name);
        select1.append(option);
      });

      // 두 번째 <select> 요소 생성
      const select2 = $("<select>");
      const updateSelect2 = () => {
        const selectedValue = select1.val();
        const selectedChild = root.childs.find((menu) => menu.id == selectedValue);
        select2.empty();
        if (selectedChild && selectedChild.childs) {
          selectedChild.childs.forEach((menu) => {
            const option = $("<option>").attr("value",menu.id).text(menu.name);
            select2.append(option);
          });
        }
      };

      // 첫 번째 <select> 변경 시 두 번째 <select> 업데이트
      select1.on("change", updateSelect2);
      // selectedPath에 해당하는 메뉴 선택
      updateSelect2();
      if (selectedPath) {
        const pathParts = selectedPath.split("/");
        if (pathParts.length >= 3) {
          const selectedValue1 = pathParts[2];
          const selectedChild1 = root.childs.find((menu) => menu.id == selectedValue1);
          if (selectedChild1) {
            select1.val(selectedValue1);
            if (pathParts.length >= 4 && selectedChild1.childs) {
              updateSelect2();
              const selectedValue2 = pathParts[3];
              const selectedChild2 = selectedChild1.childs.find((menu) => menu.id == selectedValue2);
              if (selectedChild2) {
                select2.val(selectedValue2);
              }
            }
          }
        }
      }

      // 최종 결과물 조립
      const resultContainer = $("<div>").append(select1," / ",select2);
      element.append(resultContainer);
    }).fail((xhr, status, error) => console.error("AJAX Error: " + status + " " + error));
}