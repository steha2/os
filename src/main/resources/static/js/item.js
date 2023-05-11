class ItemGrid {
  constructor(style, itemClick){
    this.rows = style.rows || 3;
    this.cols = style.cols || 3;
    this.iw = style.iw || 80;
    this.ih = style.ih || this.iw;
    this.cw = style.cw || 100;
    this.ch = style.ch || this.cw + 30;
    this.fontSize = style.fontSize || 18;
    this.style = style;
    this.itemClick = itemClick;
    this.padding = style.padding || "10px 5px 5px 5px";
    this.margin = style.margin || "10px";
    this.div = $("<div class='item-grid'></div>");
  }

  addItemCell = (item) => {
    const cell = {};
    cell.div = $("<div class='itemCell'></div>").css({padding:this.padding,margin:this.margin,fontSize:this.fontSize});
    if(this.itemClick) cell.div.click(()=>this.itemClick(item.id));
    cell.div.css({width:this.cw, height:this.ch});
    cell.image = $(`<img class='itemImg' src='/resources/images${item.imagePath}'/>`);
    cell.image.attr("alt",sliceText(item.name,20));
    cell.image.css({width:this.iw, height:this.ih});
    cell.div.append(cell.image,`<br>${sliceText(item.name,this.style.sliceText)}<br>`,
                    item.discount > 0 ? `<red>-${item.discount}%</red>`:"",` ￦${item.dcPrice.toLocaleString()}`);
    this.div.append(cell.div);
  }

  setLocation = (left, top) => {
    this.div.css({position:"absolute", left: left, top: top});
  }

  setVisible = (isVisible) => {
    this.isVisible = isVisible;
    this.div.css("visibility",isVisible ? "visible" : "hidden");
  }
  
  setItems = (items) => {
     this.div.empty();
     items.forEach(i => this.addItemCell(i));
  }
}