const 
EQUALS = "EQUALS", 
NOT_EQUALS="NOT_EQUALS", 
GREATER_THAN = "GREATER_THAN",
LESS_THAN = "LESS_THAN", 
CONTAINS="CONTAINS", 
NOT_CONTAINS="NOT_CONTAINS",
STARTS_WITH="STARTS_WITH",
ENDS_WITH="ENDS_WITH",
BETWEEN="BETWEEN";

function createPaging(pd, updateFunc) {
  let pagingBar = $("<table id='pagingBar'></table>");
  let $tr = $("<tr>");
  pagingBar.append($tr).css("width","100%").css("height","40px");
  let $td1 = $("<td>").css({textAlign:"center",height:35,width:"100%",});
  let $td2 = $("<td>");
  $tr.append($td1,$td2);
  let startPage = pd.startPage;
  let endPage = pd.endPage;
  const drc = pd.defaultRowCount || null;
  if(drc !== null) {
    let select = $("<select id='rowCountSelect'>").css({width:50,"margin-right":5,height:25});
    for(i=drc; i<=drc*5; i+=drc){
      $td2.append(select);
      let option = $(`<option value='${i}'>${i}</option>`); 
      select.append(option);
    }
    select.val(pd.rowCount).attr("selected","selected");
    select.change(()=>{
      pd.rowCount=select.val()
      updateFunc();
    });
  }

  function createLink (page, text) {
    let linkButton = $("<a class='pagingLink'>" + (text ? text : page) + "</a>");
    linkButton.css({"margin":"3px 4px 3px 4px","border-radius": "8px", "font-weight":"bold"
     ,background:"rgba(110,123,232,0.3)",padding:"4px 4px 5px 4px",fontSize:15,cursor:"pointer"});
    if(typeof updateFunc === 'function') {
      linkButton.click(()=>{
        pd.nowPage = page;
        updateFunc()
      });
    }
    return linkButton;
  }

  function addHover(target) {
    target.on("mouseover",()=>target.css("background","rgba(210,123,132,0.3)"))
    target.on("mouseout",()=>target.css("background","rgba(110,123,232,0.3)"))
    return target;
  }

  if (startPage > 1) $td1.append(addHover(createLink(startPage - 1, "◀")));
  for (let p = startPage; p <= endPage; p++) {
    let pageLink = createLink(p);
    if (p == pd.nowPage) {
        pageLink.css("cursor","default").css("color","blue");
        pageLink.off("click");
    } else {
      addHover(pageLink);
    }
    $td1.append(pageLink);
  }
  if (endPage < pd.totalPages) {
    $td1.append(addHover(createLink(endPage + 1, "▶")));
  }
  if(startPage === 0) {
    pagingBar = $("<h4 id='pagingBar' style='text-align:center'>비어있음</h4>");
  }
  return pagingBar;
}


class PagingData {
  constructor() {
    this.defaultRowCount = 10;
    this.rowCount = 10;
    this.maxPage = 10;
    this.pagingType = 1;
    this.nowPage = 1;
  }

  setPagingData(pagingData){
    for(let key in pagingData){
      this[key] = pagingData[key];
    }
  }

  setPath(path) {
    this.addOption("path",STARTS_WITH,path);
    this.path = path;
  }

  setSearch(search) {
    this.addOption("name",CONTAINS,search);
  }

  setOrderBy(orderBy) {
    if(orderBy) this.orderBy = orderBy;
    else this.orderBy = "";
  }

  setStyle(style) {
    this.style = style;
    this.defaultRowCount = style.rows * style.cols || this.defaultRowCount;
    this.rowCount = this.defaultRowCount;
    this.maxPage = style.maxPage || this.maxPage;
    this.pagingType = style.pagingType || this.pagingType;
  }

  addOption(key, condition, value1="", value2=""){
    const fo = {key:key, condition:condition, value1:value1, value2:value2};
    if(!this.option) this.option = [];
    if(value1.length === 0 && value2.length === 0) {
      this.removeOption(key,condition);
      return;
    }
    const idx = this.option.findIndex(efo => efo.key === key && efo.condition === condition);
    if(idx == -1){
      this.option.push(fo);
    } else {
      this.option[idx] = fo;
    }
  }

  removeOption(key, condition){
    this.option = this.option.filter(efo => !(efo.key === key && efo.condition === condition));
  }

  toParam(){
    const keys = ["nowPage","rowCount","maxPage","pagingType","orderBy","option"];
    const result = keys.reduce((obj, key) => {
      obj[key] = (key === "option") ? JSON.stringify(this[key]) : this[key];
      return obj;
    }, {});
    return result;
  }
}