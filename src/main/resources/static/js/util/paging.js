function createPaging(paging, link) {
  let pagingBar = $("<div id='pagingBar'></div>");
  pagingBar.css({textAlign:"center"});
  let startPage = paging.startPage;
  let endPage = paging.endPage;
  
  let createLink = (page, text) => {
    let linkButton = $("<a class='pagingLink'>" + (text ? text : page) + "</a>");
    linkButton.css("margin","1px 3px 3px 1px");
    linkButton.attr("href","#p"+page);
    return linkButton;
  }
  if (startPage > 1) pagingBar.append(createLink(startPage - 1, "◀ 이전"));
  for (let p = startPage; p <= endPage; p++) {
    let pageLink = createLink(p);
    if (p == paging.currentPage) {
        pageLink.attr("id", "activePage");
        pageLink.removeAttr("href");
    }
    pagingBar.append(pageLink);
  }
  if (endPage < paging.totalPages) {
    pagingBar.append(createLink(endPage + 1, "다음 ▶"));
  }
return pagingBar;
}