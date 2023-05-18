class ItemGrid {
  constructor(style, itemClick){
    this.rows = style.rows || 3;
    this.cols = style.cols || 3;
    this.iw = style.iw || 100;
    this.ih = style.ih || this.iw;
    this.cw = style.cw || 120;
    this.ch = style.ch || this.cw + 30;
    this.fontSize = style.fontSize || 15;
    this.style = style;
    this.itemClick = itemClick;
    this.padding = style.padding;
    this.margin = style.margin;
    this.div = $("<div class='item-grid'></div>");
  }

  addItemCell (item) {
    const cell = {};
    cell.div = $("<div class='itemCell'></div>").css({padding:this.padding,margin:this.margin,fontSize:this.fontSize});
    if(this.itemClick) cell.div.click(()=>this.itemClick(item.id));
    cell.div.css({width:this.cw, height:this.ch});
    this.drawCell(cell,item);
    this.div.append(cell.div);
  }
  drawCell(cell,item) {
    cell.image = $(`<img class='itemImg' src='/resources/images${item.imagePath}'/>`);
    cell.image.attr("alt",item.name);
    cell.image.css({width:this.iw, height:this.ih});
    cell.div.append(cell.image,`<br>${item.name}<br>`,
                    item.discount > 0 ? `<red>-${item.discount}%</red>`:"",` ￦${item.dcPrice.toLocaleString()}`);
  }
  setItems = (items) => {
    this.div.empty();
    items.forEach(i => this.addItemCell(i));
 }
}

class ItemGrid2 {
  constructor(itemClick){
    this.itemClick = itemClick;
    this.div = $("<div class='item-grid'></div>");
  }

  addItemCell (item) {
    const cell = {};
    cell.div = $("<div class='itemCell2'></div>");
    if(this.itemClick) cell.div.click(()=>this.itemClick(item.id));
    this.drawCell(cell,item);
    this.div.append(cell.div);
  }
  drawCell(cell,item) {
    cell.image = $(`<img class='itemImg2' src='/resources/images${item.imagePath}'/>`);
    cell.image.attr("alt",item.name);
    cell.div.append(cell.image,`<br>${item.name}<br>`,
                    item.discount > 0 ? `<red>-${item.discount}%</red>`:"",` ￦${item.dcPrice.toLocaleString()}`);
  }
  setItems = (items) => {
    this.div.empty();
    items.forEach(i => this.addItemCell(i));
 }
}