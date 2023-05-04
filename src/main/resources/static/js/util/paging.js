function createPaging(paging, link, selectChange) {
  let pagingBar = $("<table id='pagingBar'></table>");
  let $tr = $("<tr>");
  pagingBar.append($tr).css("width","100%");
  let $td1 = $("<td>").css({textAlign:"center",height:35,width:"100%"});
  let $td2 = $("<td>");
  $tr.append($td1,$td2);
  let startPage = paging.startPage;
  let endPage = paging.endPage;
  const defaultRowCount = paging.defaultRowCount || null;
  if(defaultRowCount !== null) {
    let select = $("<select id='rowCountSelect'>").css({width:50,"margin-right":5,height:25});
    for(i=defaultRowCount; i<=defaultRowCount*5; i+=defaultRowCount){
      $td2.append(select);
      let option = $(`<option value='${i}'>${i}</option>`); 
      select.append(option);
    }
    select.val(paging.rowCount).attr("selected","selected");
    if(selectChange) select.change(()=>{
      selectChange(select.val());
    });
  }
  // select.val(rowCount).attr("selected","selected");

  let createLink = (page, text) => {
    let linkButton = $("<a class='pagingLink'>" + (text ? text : page) + "</a>");
    linkButton.css({"margin":"3px 4px 3px 4px","border-radius": "8px", "font-weight":"bold"
     ,background:"rgba(110,123,232,0.3)",padding:"4px",fontSize:18,cursor:"pointer"});
    if(typeof link === 'function') {
      linkButton.click(()=>link(page));
    } else {
      linkButton.attr("href",link+page);  
    }
    return linkButton;
  }
  if (startPage > 1) $td1.append(createLink(startPage - 1, "◀"));
  for (let p = startPage; p <= endPage; p++) {
    let pageLink = createLink(p);
    if (p == paging.nowPage) {
        pageLink.attr("id", "activePage");
        pageLink.css("cursor","default").css("color","blue");
        pageLink.removeAttr("href");
    }
    $td1.append(pageLink);
  }
  if (endPage < paging.totalPages) {
    $td1.append(createLink(endPage + 1, "▶"));
  }
return pagingBar;
}
