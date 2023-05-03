class ItemGrid {
  constructor(rootId, style){
    this.rootId = rootId;
    this.rows = style.rows || 3;
    this.cols = style.cols || 3;
    this.iw = style.iw || 200;
    this.ih = style.ih || this.iw;
    this.cw = style.cw || 250;
    this.ch = style.ch || this.cw;
    this.style = style;
    this.create();
  }
  
  create = ()=>{
    this.div = $("<div id='item-grid'></div>");
    this.detailDiv = $("<div id='detail-div'></div>");
    this.detailDiv.css({
      position:"absolute",
      width: this.cw * this.rows / 2,
      height: this.ch * this.cols / 2,
      border:"1px solid",
      visibility:"hidden",
      background:"rgba(100,250,160,0.8)",
      zIndex:40001,
      textAlign:"center"
    });
    $(this.div).append(this.detailDiv);
  }

  addItemCell = (item) => {
    this.items.push(item);
    const cell = {};
    cell.div = $("<div class='itemCell'></div>");
    cell.div.css({
      width:this.cw, height:this.ch,
      "padding": "10px 5px 5px 5px",
      "margin": "10px",
      "outline": "1px solid",
      "display": "inline-block",
      "text-align": "center",
      "box-sizing": "border-box",
    });
    cell.item = item;
    cell.image = $(`<img class='itemImg' src='/resources/images/${item.imagePath}'/>`);
    cell.image.css({width:this.iw, height:this.ih});
    
    cell.div.append(cell.image).append(`<br>${sliceText(item.name,this.style.sliceText)}<br>￦ ${item.price.toLocaleString()} `);
    
    this.div.append(cell.div);

    cell.div.click(()=>{
      if(this.isOpenDetail) this.openDetail(cell);
    });
    // if(this.items.length % this.cols === 0) this.div.append("<br>");
  }

  setLocation = (left, top) => {
    this.div.css({position:"absolute", left: left, top: top});
  }

  setVisible = (isVisible) => {
    this.isVisible = isVisible;
    this.div.css("visibility",isVisible ? "visible" : "hidden");
  }

  openDetail = (item) => {
    this.detailDiv.empty();
    const pre = $(`<pre>Item Id: ${item.id}\nItem Name: ${item.name}\nAmount: ${item.price}\nUseable: ${item.regDate}\nDescription: ${item.content}\n</pre>`);
    pre.css({textAlign:"left",padding:10, margin:0, flex:1});
    this.detailDiv.append(pre);
    const buttonDiv = $("<div></div>");
    this.detailDiv.append(buttonDiv)
    if(item.useable) {
      const useButton = $("<button class='detail-button'>사용</button>");
      useButton.click(()=>this.useItem(item));
      this.detailDiv.append(useButton);
      buttonDiv.append(useButton);
    }
    const closeButton = $("<button class='detail-button'>닫기</button>");
    buttonDiv.append(closeButton);
    closeButton.click(()=>this.setDetailVisible(false));
    this.detailDiv.css({
      left: this.div.offset().left + this.div.width()/4,
      top: this.div.offset().top + this.div.height()/4,
    });
    this.setDetailVisible(true);
  }

  useItem = (item) => {
    if(this.useItemAction) this.useItemAction(item);
    this.setDetailVisible(false);
    return item;
  }

  setDetailVisible = (visible) => {
    this.div.css('filter', visible ? 'blur(2px)' : 'none');
    this.detailDiv.css("visibility", visible ? "visible": "hidden");
  }
  
  setItems = (items) => {
     this.items = [];
     this.div.empty();
     items.forEach(i => this.addItemCell(i));
  }
}
